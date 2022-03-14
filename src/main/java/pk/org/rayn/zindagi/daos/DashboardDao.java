package pk.org.rayn.zindagi.daos;

import java.sql.Types;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pk.org.rayn.zindagi.domain.*;
import pk.org.rayn.zindagi.utils.JsonUtils;

@Repository
public class DashboardDao extends AbstractDao{
    
    private static RowMapper<PendingContent> PENDINGCONTENTMAPPER = (rs, i) -> new PendingContent(
            UUID.fromString(rs.getString("id")),
            JsonUtils.getValue(rs.getString("data"), new TypeReference<Map<String, Object>>(){}), 
            DashboardActions.valueOf(rs.getString("action")),
            DashboardTypes.valueOf(rs.getString("type")), 
            rs.getString("added_date"));
    private static RowMapper<TopicVerseFragments> FRAGMENTMAPPER = (rs, i) -> new TopicVerseFragments(
            UUID.fromString(rs.getString("id")), UUID.fromString(rs.getString("language_id")),
            UUID.fromString(rs.getString("topic_id")), rs.getString("type"),UUID.fromString(rs.getString("verse_id")), rs.getInt("beginning"),
            rs.getInt("ending"));

    public boolean isExist(Map<String, Object> data, DashboardActions action, DashboardTypes type)
    {
        String _sql = """
                """;
        switch (type)
        {
            case HIGHLIGHT :
                _sql = """
                select * from pending_table
                WHERE type='HIGHLIGHT' and data->>'verse_id' = ? and data->>'language_id' = ? and data->>'topic_id' = ? and status = 'PENDING' and action = ? and (data->>'beginning')::int = ? and (data->>'ending')::int = ?
                """;
                {
                    List<PendingContent> res = jdbcTemplate.query(_sql, PENDINGCONTENTMAPPER, data.get("verse_id"), data.get("language_id"), data.get("topic_id"), action.toString(), data.get("beginning"), data.get("ending"));
                    if (res.size() > 0) {
                        return true;
                    }
                }
                break;

            case MINDFULMINUTE:

                switch (action)
                {
                    case INSERT :
                        _sql = """
                        select * from pending_table
                        WHERE data->>'name' = ? and status = 'PENDING' and action = ? and type = 'MINDFULMINUTE'
                        """;
                        {
                            List<PendingContent> res = jdbcTemplate.query(_sql, PENDINGCONTENTMAPPER, data.get("name"),action.toString());
                            if (res.size() > 0) {
                                return true;
                            }
                        }
                        break;
                    default:

                }




                break;
            default:
                return false;
        }

        return false;
    }

    public int insertPending(Map<String, Object> data, DashboardActions action, DashboardTypes type) throws DataAccessException, JsonProcessingException{

        if(isExist(data,action,type)) return 0;

        String sql = """
        INSERT INTO pending_table(data, action, type)
        VALUES (cast(? as jsonb), ?, ?);""";

        ObjectMapper objectMapper = new ObjectMapper();
        return jdbcTemplate.update(sql,objectMapper.writeValueAsString(data), action.toString(),type.toString());
    }

    public List<PendingContent> getPendingContent(DashboardActions action, DashboardTypes type, String status){
        List<PendingContent> res = null;

        String sql = """
        SELECT * from pending_table
        WHERE type = ?
        """;
        if(action != null && status != null){
            sql+="AND action = ? AND status = ?";
            res = jdbcTemplate.query(sql,PENDINGCONTENTMAPPER,type.toString(),action.toString(),status);
        }
        else if(action != null) {
            sql+="AND action = ?";
            res = jdbcTemplate.query(sql,PENDINGCONTENTMAPPER,type.toString(),action.toString());
        }
        else if(action != null) {
            sql+="AND status = ?";
            res = jdbcTemplate.query(sql,PENDINGCONTENTMAPPER,type.toString(),status);
        }
        else{
            res = jdbcTemplate.query(sql,PENDINGCONTENTMAPPER,type.toString());
        }

        return res;
    }

    public List<PendingContent> getPendingHighlightsVerse(String verseId,String languageId,String topicId){
        List<PendingContent> res = null;

        String sql = """
        SELECT * from pending_table
        WHERE type = 'HIGHLIGHT' and data->>'verse_id' = ? and data->>'language_id' = ? and data->>'topic_id' = ? and status = 'PENDING'
        """;

        res = jdbcTemplate.query(sql,PENDINGCONTENTMAPPER,verseId,languageId,topicId);
        return res;
    }

    public List<TopicVerseFragments> getHighlightsVerse(String verseId,String languageId,String topicId){
        List<TopicVerseFragments> res = null;

        String sql = """
        SELECT * from fragments
        WHERE verse_id = (cast(? as UUID)) and language_id = (cast(? as UUID)) and topic_id = (cast(? as UUID))
        """;

        res = jdbcTemplate.query(sql,FRAGMENTMAPPER,verseId,languageId,topicId);
        return res;
    }

    public int rejectPendingContent(UUID id) {
        String sql="""
        Delete from pending_table where id = ?
        """;
        return jdbcTemplate.update(sql.toString(),id);
    }

    public int approvePendingContent(UUID id) throws DataAccessException, JsonProcessingException{
        String sql="""
        SELECT * from pending_table where id = ?
        """;
        List<PendingContent> res = null;
        res = jdbcTemplate.query(sql,PENDINGCONTENTMAPPER,id);
        int ret=0;
        switch(res.get(0).type()){
            case HIGHLIGHT:
                ret = updateHighlight(res.get(0).data(),res.get(0).action(), id);
            case TOPIC:
                ret = topics(res.get(0).data(),res.get(0).action());
            break;
            case MINDFULMINUTE:
                mindfulMinute(res.get(0).data(), res.get(0).action());
            break;
            case VERSEGROUP:
                
            break;
            
            case VERSEGROUPMAPPING:
                
            break;
            
            case TOPICVERSEGROUP:
                
            break;
            
        }
        return ret;
    }

    public int updateHighlight(Map<String,Object> data,DashboardActions action, UUID id){
        int res = 0;
        String sql="";
        switch (action){
            case INSERT:                
                sql= """
                INSERT INTO fragments (verse_id,topic_id,language_id,type,beginning,ending) 
                VALUES (cast(? as UUID),cast(? as UUID),cast(? as UUID),?,?,?);
                UPDATE pending_table SET status = 'APPROVED' WHERE id = (cast(? as UUID) )
                """;
                res = jdbcTemplate.update(sql.toString(),data.get("verse_id"),data.get("topic_id"),data.get("language_id"),
                data.get("type"),data.get("beginning"),data.get("ending"), id);
            break;
            
            case UPDATE:
                sql= """
                UPDATE fragments
                SET (type,beginning,ending) =  (:type,:beginning,:ending)
                WHERE id=cast(:id as UUID);
                UPDATE pending_table SET status = 'APPROVED' WHERE id = (cast(:p_id as UUID) )
                """;
    
                res = namedParameterJdbcTemplate.update(sql, Map.of(
                "type",data.get("type"),
                "beginning", data.get("beginning"),
                "ending",data.get("ending"),
                "id",data.get("id"),
                        "p_id",id));
            break;
            case DELETE:
                sql="""
                DELETE from fragments WHERE id=cast(? as UUID);
                UPDATE pending_table SET status = 'APPROVED' WHERE id = (cast(? as UUID) )
                """;
                res=jdbcTemplate.update(sql,data.get("id"),id);
            break;


        }

        return res;
    }

    public int topics(Map<String,Object> data,DashboardActions action){
        int res = 0;
        String sql="";
        switch (action){
            case INSERT:   
                sql= """   
                WITH ins as(
                INSERT INTO topics (name, area)
                VALUES (:topicName,:topicArea)
                ON CONFLICT (name, area)
                DO UPDATE SET name=:topicName, area=:topicArea
                RETURNING id
                )
                INSERT INTO scripture_topics (scripture_id,topic_id)
                VALUES (cast(:scriptureId as UUID), (SELECT id from ins))
                ON CONFLICT (scripture_id,topic_id)
                DO NOTHING
                """;
                res = namedParameterJdbcTemplate.update(sql,Map.of(
                "scriptureId",data.get("scriptureId"),
                "topicName",data.get("name"),
                "topicArea",data.get("area")));                          
            break;
            
            case UPDATE:
                sql= """
                UPDATE topics t
                SET (name, area) = (:topicName, :topicName)
                WHERE id = cast(:id as UUID)
                AND NOT EXISTS (
                   SELECT 1 FROM topics WHERE name = :topicName AND area = :topicArea
                );
                """;
    
                res = namedParameterJdbcTemplate.update(sql,Map.of(
                "id",data.get("id"),
                "topicName",data.get("name"),
                "topicArea",data.get("area")));     
            break;
            case DELETE:
                sql="""
                DELETE from topics WHERE id=cast(? as UUID)
                """;
                res=jdbcTemplate.update(sql,data.get("id"));
            break;

        }
        return res;
    }

    public int mindfulMinute(Map<String,Object> data,DashboardActions action) throws DataAccessException, JsonProcessingException{
        int res = 0;
        String sql="";
        ObjectMapper objectMapper = new ObjectMapper();
        switch (action){
            case INSERT:  
            sql= """
            INSERT INTO mindful_minutes (name,arabic_name,translation,quran,hadith,dua) 
            VALUES (?,?,?,cast(? as jsonb),cast(? as jsonb),cast(? as jsonb))
            """;
            res = jdbcTemplate.update(sql.toString(),
            data.get("name"),
            data.get("arabic_name"),
            data.get("translation"),
            objectMapper.writeValueAsString(data.get("quran")),
            objectMapper.writeValueAsString(data.get("hadith")),
            objectMapper.writeValueAsString(data.get("dua")));                          
            break;

            case UPDATE:
            sql= """
            UPDATE mindful_minutes
            SET(name,arabic_name,translation,quran,hadith,dua)
            = (?,?,?,cast(? as jsonb),cast(? as jsonb),cast(? as jsonb))
            WHERE id = cast(? as UUID)
            """;
            res = jdbcTemplate.update(sql.toString(),
                    data.get("name"),
                    data.get("arabic_name"),
                    data.get("translation"),
                    objectMapper.writeValueAsString(data.get("quran")),
                    objectMapper.writeValueAsString(data.get("hadith")),
                    objectMapper.writeValueAsString(data.get("dua")),
                    data.get("id"));
            break;

            case DELETE:
            sql= """
            DELETE from mindful_minutes WHERE id=cast(? as UUID)
            """;
            res = jdbcTemplate.update(sql.toString(),
            data.get("id"));   
            break;
        }
        return res;

    }

    public int verseGroup(Map<String,Object> data,DashboardActions action){
        int res = 0;
        String sql="";
        switch (action){
            case INSERT:  

            break;

            case UPDATE:

            break;

            case DELETE:

            break;
        }
        return res;
    }   

    public int verseGroupMappings(Map<String,Object> data,DashboardActions action){
        int res = 0;
        String sql="";
        switch (action){
            case INSERT:  

            break;

            case UPDATE:

            break;

            case DELETE:

            break;
        }
        return res;
    }   
    
    public int topicVerseGroup(Map<String,Object> data,DashboardActions action){
        int res = 0;
        String sql="";
        switch (action){
            case INSERT:  

            break;

            case UPDATE:

            break;

            case DELETE:

            break;
        }
        return res;
    } 
}