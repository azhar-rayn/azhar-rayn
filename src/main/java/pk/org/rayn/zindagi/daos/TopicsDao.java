package pk.org.rayn.zindagi.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.jdbc.core.RowMapper;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableMap;

import pk.org.rayn.zindagi.domain.Topic;
import pk.org.rayn.zindagi.domain.TopicGroupedVerseContentFragments;
import pk.org.rayn.zindagi.domain.TopicVerseContentFragments;
import pk.org.rayn.zindagi.utils.JsonUtils;

@Repository
public class TopicsDao extends AbstractDao {
	private static final Logger LOG = LoggerFactory.getLogger(TopicsDao.class);

	private static RowMapper<Topic> TOPICMAPPER = (rs, i) -> new Topic(UUID.fromString(rs.getString("id")),
			rs.getString("name"), rs.getString("area"));

	public static RowMapper<TopicGroupedVerseContentFragments> GROUPEDVERSEMAPPER = (rs, i) -> {
		TopicVerseContentFragments tvcf = new TopicVerseContentFragments(UUID.fromString(rs.getString("verse_id")),
				UUID.fromString(rs.getString("scripture_id")), rs.getInt("verse_order"),
				JsonUtils.getValue(rs.getString("metadata"), new TypeReference<Map<String, String>>() {
				}), UUID.fromString(rs.getString("language_id")), rs.getString("content"), rs.getString("type"),
				rs.getObject("beginning", Integer.class), rs.getObject("ending", Integer.class));

		return new TopicGroupedVerseContentFragments(tvcf, UUID.fromString(rs.getString("verse_group_id")),
				rs.getObject("is_primary", Boolean.class),
				JsonUtils.getValue(rs.getString("group_metadata"), new TypeReference<Map<String, String>>() {
				}));
	};

	public List<Topic> listTopics(UUID scriptureId) {
		String sql = "";
		List<Topic> res = null;
		if (scriptureId != null) {
			sql = """
					SELECT * FROM  topics tp
					WHERE tp.id IN( SELECT st.topic_id FROM scripture_topics st
					WHERE st.scripture_id=?)
					""";
			res = jdbcTemplate.query(sql, TOPICMAPPER, scriptureId);
		} else {
			sql = "SELECT * FROM topics";
			res = jdbcTemplate.query(sql, TOPICMAPPER);
		}
		return res;
	}

	public List<Topic> getRelatedTopics(UUID id) {
		return jdbcTemplate.query("""
				select t.*
				from topics t, related_topics rt
				where rt.topic_id=?
				and rt.related_topic_id=t.id
				""", TOPICMAPPER, id);
	}

	public List<TopicVerseContentFragments> getVerses(UUID id, List<UUID> languages) {
		String sql = """
				with vs as (
					select v.*, vc.*, tv.topic_id
					from topic_verses tv, verses v, verse_contents vc
					where tv.topic_id=:tid
					and tv.verse_id=v.id
					and v.id=vc.verse_id
				)
				select vs.*, f.type, f.beginning, f.ending
				from vs left join fragments f
				on (f.verse_id=vs.verse_id and f.language_id=vs.language_id and f.topic_id=vs.topic_id)
				""";

		if (!languages.isEmpty()) {
			sql += " where vs.language_id in (:lids)";
		}

		sql += " order by vs.verse_order, vs.language_id";
		return namedParameterJdbcTemplate.query(sql, ImmutableMap.of("tid", id, "lids", languages),
				ScripturesDao.VERSEMAPPER);
	}

	public List<TopicGroupedVerseContentFragments> getVerseGroups(UUID id, List<UUID> languages, List<UUID> scriptures) {
		String scriptureSql = !scriptures.isEmpty() ? " and v.scripture_id in (:sids)" : "";

		String sql = """
				with vs as (
					select distinct v.id, v.*, vc.*, tvg.topic_id, tvg.verse_group_id, vgvm.is_primary, vg.metadata as group_metadata
					from topic_verse_groups tvg, verse_groups_verse_mapping vgvm, verse_groups vg, verses v, verse_contents vc
					where tvg.topic_id=:tid
					and tvg.verse_group_id=vgvm.verse_group_id
					and vg.id=tvg.verse_group_id
					and vg.id=vgvm.verse_group_id
					and vgvm.verse_id=v.id
					and v.id=vc.verse_id
					"""
				+ scriptureSql + """
						)
						select vs.*, f.type, f.beginning, f.ending
						from vs left join fragments f
						on (f.verse_id=vs.verse_id and f.language_id=vs.language_id and f.topic_id=vs.topic_id)
						""";

		if (!languages.isEmpty()) {
			sql += " where vs.language_id in (:lids)";
		}

		sql += " order by vs.scripture_id, vs.verse_order, vs.language_id";
		return namedParameterJdbcTemplate.query(sql, ImmutableMap.of("tid", id, "lids", languages, "sids", scriptures),
				GROUPEDVERSEMAPPER);
	}

	public UUID createTopic(String topicName, String topicArea) {
		String sql = """
					WITH ins AS (
						INSERT INTO topics (name, area)
						VALUES (:topicName, :topicArea)
						ON CONFLICT (name, area)
						DO NOTHING
						RETURNING id)
					SELECT id FROM ins
					UNION ALL
					SELECT id FROM topics WHERE name=:topicName AND area=:topicArea;
				""";
		return namedParameterJdbcTemplate.queryForObject(sql,
				ImmutableMap.of("topicName", topicName, "topicArea", topicArea), UUID.class);
	}

	public int createScriptureTopic(UUID scriptureId, UUID topicId) {
		String sql = """
					WITH ins AS (
						INSERT INTO scripture_topics (scripture_id, topic_id)
						VALUES (:scriptureId, :topicId)
						ON CONFLICT (scripture_id, topic_id)
						DO NOTHING
						RETURNING id)
					SELECT id FROM ins
					UNION ALL
					SELECT id FROM scripture_topics WHERE scripture_id=:scriptureId AND topic_id=:topicId;
				""";
		namedParameterJdbcTemplate.queryForObject(sql, ImmutableMap.of("scriptureId", scriptureId, "topicId", topicId),
				UUID.class);

		return 1;
	}

	public UUID createVerseGroups(String groupId) {
		String sql = """
						WITH ins as (
							INSERT INTO verse_groups (metadata)
							VALUES (jsonb_build_object('groupId', :groupId))
							RETURNING id
						)
						SELECT id FROM ins
				""";

		return namedParameterJdbcTemplate.queryForObject(sql, ImmutableMap.of("groupId", groupId), UUID.class);
	}

	public void associateTopicsToVerseGroups(UUID verseGroupId, UUID topicId) {
		String sql = """
					INSERT INTO topic_verse_groups (topic_id, verse_group_id)
					VALUES (:topicId, :topicVerseGroupId)
				""";

		namedParameterJdbcTemplate.update(sql, ImmutableMap.of("topicId", topicId, "topicVerseGroupId", verseGroupId));
	}

	public boolean cleanVerseGroupsFromTopics(UUID scriptureId) {
		String sql = """
					DELETE FROM verse_groups vg
					WHERE EXISTS
						(
							SELECT 1
							FROM verses v, verse_groups_verse_mapping vgvm
							WHERE vg.id=vgvm.verse_group_id
							AND vgvm.verse_id=v.id
							AND v.scripture_id=:sid
						)
				""";
		return namedParameterJdbcTemplate.update(sql, ImmutableMap.of("sid", scriptureId)) > 0;
	}

	public boolean cleanTopic(UUID topicId) {
		return jdbcTemplate.update("DELETE FROM topic_verses WHERE topic_id=?", topicId) > 0;
	}

	public boolean cleanDanglingTopics() {
		return jdbcTemplate.update("""
				DELETE FROM topics tp
				WHERE tp.id not IN(
				SELECT st.topic_id FROM scripture_topics st
				right join topic_verse_groups tvg on tvg.topic_id=st.topic_id)  
				""") > 0;
	}

	public boolean clearRelatedTopics() {
		return jdbcTemplate.update("TRUNCATE related_topics CASCADE") == 0;
	}

	public boolean associateTopics(String topic, List<String> relatedTopics) {
		if (relatedTopics.isEmpty()) {
			return false;
		}

		int[] inserted = namedParameterJdbcTemplate.batchUpdate("""
				WITH data(topic,related) AS (
					VALUES(
						(SELECT id FROM topics WHERE name=:topicName),
						(SELECT id FROM topics WHERE name=:relatedName)
					)
				)
				INSERT INTO related_topics
				SELECT topic, related
				FROM data
				WHERE topic IS NOT NULL AND related IS NOT NULL
				""",
				relatedTopics.stream()
						.map(rt -> ImmutableMap.<String, Object>builder().put("topicName", topic).put("relatedName", rt).build())
						.toArray(ImmutableMap[]::new));

		IntStream.range(0, inserted.length).filter(i -> inserted[i] == 0)
				.forEach(i -> LOG.warn("Did not insert related topic {} for topic {}", relatedTopics.get(i), topic));

		return IntStream.of(inserted).sum() == relatedTopics.size();
	}

	public boolean associateVersesToVerseGroups(UUID verseGroupId, List<Boolean> isPrimaryValues,
			List<String> metadataKeys, List<List<Object>> metadataVals) {
		if (metadataVals.isEmpty()) {
			return true;
		}

		List<Object[]> listOfPreparedStatements = new ArrayList<>();

		for (int i = 0; i < isPrimaryValues.size(); i++) {
			listOfPreparedStatements
					.add(Stream.concat(Stream.concat(Stream.of(verseGroupId), Stream.of(isPrimaryValues.get(i))),
							metadataVals.get(i).stream()).map(String::valueOf).toArray());
		}

		String whereClause = metadataKeys.stream().map(k -> "v.metadata->>'" + k + "'=?")
				.collect(Collectors.joining(" AND "));

		String sql = """
					INSERT INTO verse_groups_verse_mapping (verse_group_id, verse_id, is_primary)
					SELECT cast (? as uuid), v.id, cast (? as boolean)
					FROM verses v
					WHERE
				""" + whereClause;

		return IntStream.of(jdbcTemplate.batchUpdate(sql, listOfPreparedStatements)).sum() == metadataVals.size();
	}

	public boolean associateFragments(UUID topicId, String language, Map<String, String> verseMetadata,
			List<Triple<Integer, Integer, String>> fragments) {
		if (fragments.isEmpty()) {
			return true;
		}
		String whereClause = verseMetadata.keySet().stream().map(k -> "v.metadata->>'" + k + "'=:" + k)
				.collect(Collectors.joining(" AND "));
		String sql = """
					INSERT INTO fragments(verse_id, language_id, topic_id, type, beginning, ending)
					SELECT v.id, l.id, cast (:topicId as uuid), :type, :beginning, :ending
					FROM verses v, languages l
					WHERE l.name=:language
					AND
				""" + whereClause;

		return IntStream.of(namedParameterJdbcTemplate.batchUpdate(sql,
				fragments.stream()
						.map(f -> ImmutableMap.<String, Object>builder().put("topicId", topicId).put("type", f.getRight())
								.put("beginning", f.getLeft()).put("ending", f.getMiddle()).put("language", language)
								.putAll(verseMetadata).build())
						.toArray(ImmutableMap[]::new)))
				.sum() == fragments.size();
	}
}
