package pk.org.rayn.zindagi.services;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.MoreFiles;

import pk.org.rayn.zindagi.daos.TopicsDao;
import pk.org.rayn.zindagi.utils.JsonUtils;

@Service
public class FileHandlingService {
	private static final Logger LOG = LoggerFactory.getLogger(FileHandlingService.class);

	@Autowired
	TopicsDao topicsDao;

	public void topicVerses(UUID scriptureId, MultipartFile file) throws IOException {
		String language = MoreFiles.getNameWithoutExtension(Paths.get(file.getOriginalFilename()));
		List<CSVTopicVerseRow> csvRows = parseCsvFile(file.getInputStream(), CSVTopicVerseRow.class);

		// sort by topic/area
		Map<Pair<String, String>, List<CSVTopicVerseRow>> filteredRows = csvRows.stream().collect(
				Collectors.groupingBy(r -> Pair.of(StringUtils.trimToNull(r.topicArea()), StringUtils.trimToNull(r.topic()))));

		// sort by groupdID
		Map<String, List<CSVTopicVerseRow>> filteredVerseGroupRows = csvRows.stream()
				.collect(Collectors.groupingBy(r -> StringUtils.trimToNull(r.primaryGroupId())));

		// clean verse groups
		topicsDao.cleanVerseGroupsFromTopics(scriptureId);

		// clean topics
		topicsDao.cleanDanglingTopics();

		// create topics
		Map<Pair<String, String>, UUID> topicIds = filteredRows.keySet().stream()
				.map(k -> Pair.of(k, topicsDao.createTopic(k.getRight(), k.getLeft())))
				.collect(toMap(k -> k.getLeft(), k -> k.getRight()));

		// insert to scripture topics
		topicIds.values().forEach(e -> {
			topicsDao.createScriptureTopic(scriptureId, e);
		});
		// Create verse groups
		Map<String, UUID> groupIds = new LinkedHashMap<>();

		filteredRows.keySet().forEach(e -> {
			List<CSVTopicVerseRow> listTvr = filteredRows.get(e);
			listTvr.forEach(v -> {
				if (!groupIds.containsKey(v.primaryGroupId())) {
					// Group id key does not exist
					groupIds.put(v.primaryGroupId(), topicsDao.createVerseGroups(v.primaryGroupId()));
				}
			});
		});

		// Add topic to verse group mappings
		filteredRows.entrySet().forEach(e -> {
			UUID topicId = topicIds.get(e.getKey());
			e.getValue().forEach(v -> {
				topicsDao.associateTopicsToVerseGroups(groupIds.get(v.primaryGroupId()), topicId);
			});
		});

		// Associate verse to verse groups
		groupIds.entrySet().forEach(e -> {
			List<CSVTopicVerseRow> rows = filteredVerseGroupRows.get(e.getKey());

			List<Boolean> isPrimaryValues = rows.stream()
					.map(v -> ((v.context().equals("Primary") || v.context().equals("")) && v.context != null)).collect(toList());

			List<String> keys = new ArrayList<String>(
					JsonUtils.getValue(rows.get(0).metadata(), new TypeReference<LinkedHashMap<String, String>>() {
					}).keySet());

			List<List<Object>> values = rows.stream().map(v -> new ArrayList<Object>(
					JsonUtils.getValue(v.metadata(), new TypeReference<LinkedHashMap<String, Object>>() {
					}).values())).collect(toList());

			topicsDao.associateVersesToVerseGroups(e.getValue(), isPrimaryValues, keys, values);
		});

		// Associate fragments
		filteredRows.entrySet().forEach(e -> {
			UUID topicId = topicIds.get(e.getKey());
			e.getValue().forEach(v -> {
				List<List<Integer>> highlighted = JsonUtils.getValue("[" + v.highlighted() + "]",
						new TypeReference<List<List<Integer>>>() {
						});
				List<Triple<Integer, Integer, String>> fragments = highlighted.stream()
						.map(l -> Triple.of(l.get(0), l.get(1), "highlighted")).collect(toList());
				if (!topicsDao.associateFragments(topicId, language,
						JsonUtils.getValue(v.metadata(), new TypeReference<LinkedHashMap<String, String>>() {
						}), fragments)) {
					LOG.warn("Failed to associate fragments for topic {}, language {}, metadata {}", e.getKey(), language,
							v.metadata());
				}
			});
		});

	}

	public void relatedTopics(MultipartFile file) throws IOException {
		List<CSVRelatedTopicsRow> csvRows = parseCsvFile(file.getInputStream(), CSVRelatedTopicsRow.class);

		topicsDao.clearRelatedTopics();

		csvRows.forEach(row -> {
			String[] related = StringUtils.split(row.relatedTopics, ",");
			if (!topicsDao.associateTopics(StringUtils.trimToNull(row.topic()),
					Arrays.stream(related).map(StringUtils::trimToNull).filter(StringUtils::isNotBlank).collect(toList()))) {
				LOG.warn("Failed to associate all topics for topic {}", row.topic());
			}
		});
	}

	private static CsvSchema schema = CsvSchema.emptySchema().withHeader();

	public static <T> List<T> parseCsvFile(InputStream input, Class<T> klazz) throws IOException {
		ObjectReader oReader = new CsvMapper().enable(CsvParser.Feature.TRIM_SPACES).readerFor(klazz).with(schema);
		try (Reader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8.name()))) {
			return oReader.<T>readValues(reader).readAll();
		}
	}

	public record CSVTopicVerseRow(@JsonProperty("WebsiteCategory") String topicArea,
			@JsonProperty("PotentialTopic") String topic, @JsonProperty("GroupID") String groupId,
			@JsonProperty("ContextTag") String context, @JsonProperty("PrimaryContextHadithID") String primaryGroupId,
			@JsonProperty("Metadata") String metadata, @JsonProperty("Highlights") String highlighted) {

	}

	public record CSVRelatedTopicsRow(@JsonProperty("Topic") String topic,
			@JsonProperty("RelatedTopics") String relatedTopics) {

	}
}
