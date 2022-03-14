package pk.org.rayn.zindagi.daos;

import java.util.UUID;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import pk.org.rayn.zindagi.daos.AbstractDao;
import pk.org.rayn.zindagi.domain.TopicVerseTemporaryFragments;
import pk.org.rayn.zindagi.utils.JsonUtils;

@Repository
public class HighlighterDao extends AbstractDao {

    private static RowMapper<TopicVerseTemporaryFragments> FRAGMENTMAPPER = (rs, i) -> new TopicVerseTemporaryFragments(
            UUID.fromString(rs.getString("id")), UUID.fromString(rs.getString("language_id")),
            UUID.fromString(rs.getString("topic_id")), rs.getString("type"), rs.getInt("beginning"),
            rs.getInt("ending"), rs.getString("highlighted_by"), rs.getString("added_date"), rs.getBoolean("approved"),
            JsonUtils.getValue(rs.getString("metadata"), new TypeReference<Map<String, Object>>() {
            }));

    public int addFragmentPending(UUID languageId, UUID topicId, String type, int beginning, int ending,
    String highlightedBy, Map<String,Object> metadata) throws DataAccessException, JsonProcessingException{

        String sql = """
                INSERT INTO fragments_pending(language_id, topic_id, type, beginning, ending, highlighted_by,metadata)
                VALUES (?, ?, ?, ?, ?, ?,cast(? as jsonb));""";

                ObjectMapper objectMapper = new ObjectMapper();
        return jdbcTemplate.update(sql, languageId, topicId, type, beginning, ending, highlightedBy,objectMapper.writeValueAsString(metadata));
        
    }

    public int removeFragmentPending(Map<String,Object> metadata, UUID topicId) throws DataAccessException, JsonProcessingException{

        String sql = """
                DELETE FROM fragments_pending fp WHERE fp.topic_id=? AND fp.approved=FALSE AND fp.metadata=cast(? as jsonb)
                """;
        ObjectMapper objectMapper = new ObjectMapper();
        return jdbcTemplate.update(sql, topicId,objectMapper.writeValueAsString(metadata));
    }

    public int removeSingleFragmentPending(UUID id) {

        String sql = """
                DELETE FROM fragments_pending fp WHERE fp.id=?
                """;
        return jdbcTemplate.update(sql, id);
    }

    public List<TopicVerseTemporaryFragments> getVerseFragments(Map<String,Object> metadata, UUID topicId, UUID languageId) throws DataAccessException, JsonProcessingException{
        List<TopicVerseTemporaryFragments> res = null;
        String sql = """
                SELECT * FROM fragments_pending
                """;
        res = jdbcTemplate.query(sql,FRAGMENTMAPPER);
        return res;

    }

    public List<TopicVerseTemporaryFragments> getVerseFragmentsByTopic(UUID topicId, UUID languageId, boolean approved) {

        List<TopicVerseTemporaryFragments> res = null;
        String sql = """
                SELECT * FROM fragments_pending WHERE topic_id=? AND language_id=? AND approved=?
                """;
        res = jdbcTemplate.query(sql, FRAGMENTMAPPER, topicId, languageId, approved);

        return res;
    }

    public int approveHighlight(UUID id) {

        String sql = """
                UPDATE fragments_pending fp
                SET approved=True
                WHERE fp.id=?
                """;

        return jdbcTemplate.update(sql, id);
    }


}