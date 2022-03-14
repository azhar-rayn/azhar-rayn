package pk.org.rayn.zindagi.daos;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pk.org.rayn.zindagi.domain.Role;

import java.util.List;
import java.util.UUID;

@Repository
public class RoleDao extends AbstractDao{
    private static final Logger LOG = LoggerFactory.getLogger(RoleDao.class);

    private static RowMapper<Role> ROLEMAPPER = (rs, i) ->
            new Role(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("name"),
                    rs.getString("description")
            );


    public List<Role> getAllRoles() {
        String sql = " SELECT * FROM roles";
        List<Role> res = jdbcTemplate.query(sql, ROLEMAPPER);

        return res;
    }

    public List<Role> getRole(UUID id) {
        String sql = """
                    SELECT * FROM roles 
                    Where id=?
                    """;
        List<Role> res = jdbcTemplate.query(sql, ROLEMAPPER,id);

        return res;
    }

    public List<Role> getRoleByName(List<String> name) {
        String sql = """
                    SELECT * FROM roles 
                    Where name In (:names)
                    """;

        List<Role> res = namedParameterJdbcTemplate.query(sql,ImmutableMap.of("names",name),ROLEMAPPER);

        return res;
    }


    public UUID addNewRole(Role newRole) {
        String sql = """
						INSERT INTO roles (name, description)
						VALUES (:roleName, :roleDesc)
						RETURNING id
                        """;
		return namedParameterJdbcTemplate.queryForObject(sql,
				ImmutableMap.of("roleName", newRole.name() , "roleDesc", newRole.description() ), UUID.class);
    }

}




