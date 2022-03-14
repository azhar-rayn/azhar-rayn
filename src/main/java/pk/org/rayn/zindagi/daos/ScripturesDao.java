package pk.org.rayn.zindagi.daos;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableMap;

import pk.org.rayn.zindagi.domain.ScriptureLanguageDTO;
import pk.org.rayn.zindagi.domain.TopicVerseContentFragments;

import pk.org.rayn.zindagi.utils.JsonUtils;

@Repository
public class ScripturesDao extends AbstractDao {

	private static RowMapper<ScriptureLanguageDTO> SCRIPTUREMAPPER = (rs, i) -> new ScriptureLanguageDTO(
			UUID.fromString(rs.getString("scripture_id")), rs.getString("scripture_name"), rs.getString("author"),
			rs.getTimestamp("publish_date").toInstant(), UUID.fromString(rs.getString("language_id")),
			rs.getBoolean("native_language"), rs.getString("language_name"), rs.getString("language"),
			rs.getString("translator"));

	public static RowMapper<TopicVerseContentFragments> VERSEMAPPER = (rs, i) -> new TopicVerseContentFragments(
			UUID.fromString(rs.getString("verse_id")), UUID.fromString(rs.getString("scripture_id")),
			rs.getInt("verse_order"), JsonUtils.getValue(rs.getString("metadata"), new TypeReference<Map<String, String>>() {
			}), UUID.fromString(rs.getString("language_id")), rs.getString("content"), rs.getString("type"),
			rs.getObject("beginning", Integer.class), rs.getObject("ending", Integer.class));

	public List<ScriptureLanguageDTO> listScriptures() {
		return jdbcTemplate.query(
				"""
						select sl.*, s.name as scripture_name, s.author, s.publish_date, l.name as language_name, l.language, l.translator, l.native_language
						from scriptures s, languages l, scripture_languages sl
						where sl.scripture_id=s.id
						and sl.language_id=l.id
						""",
				SCRIPTUREMAPPER);
	}

	public List<TopicVerseContentFragments> getVerses(UUID scriptureId, List<UUID> languages, Integer page,
			Integer pageLength) {
		String sql = """
				select *, null as type, null::int as beginning, null::int as ending
				from verses v, verse_contents vc
				where v.scripture_id=:sid
				and v.id = vc.verse_id
				""";

		if (!languages.isEmpty()) {
			sql += " and vc.language_id in (:lids)";
		}

		sql += " order by v.verse_order, vc.language_id";

		if (pageLength != null) {
			sql += " limit " + pageLength;
			// defining a page without defining each page's length is useless
			// thus this must be within the parent if clause
			if (page != null) {
				sql += " offset " + ((page - 1) * pageLength);
			}
		}

		return namedParameterJdbcTemplate.query(sql, ImmutableMap.of("sid", scriptureId, "lids", languages), VERSEMAPPER);
	}
}
