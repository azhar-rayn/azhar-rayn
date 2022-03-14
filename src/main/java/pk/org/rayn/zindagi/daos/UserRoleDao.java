package pk.org.rayn.zindagi.daos;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import pk.org.rayn.zindagi.domain.UserRole;

import java.util.UUID;

    @Repository
    public class UserRoleDao extends AbstractDao{
        private static final Logger LOG = LoggerFactory.getLogger(UserDao.class);

        private static RowMapper<UserRole> USERROLEMAPPER = (rs, i) ->
                new UserRole(
                        UUID.fromString(rs.getString("id")),
                        UUID.fromString(rs.getString("user_id")),
                        UUID.fromString(rs.getString("role_id")));

        public UUID addUserRole(UUID userID, UUID userRole)
        {


            String sql = """
						INSERT INTO user_roles (user_id, role_id)
						VALUES (:userId, :roleId)
						RETURNING id
                        """;

            return namedParameterJdbcTemplate.queryForObject(sql,
                    ImmutableMap.of("userId", userID , "roleId", userRole), UUID.class);

        }

        public int deleteUserRole(UUID userID)
        {
            String sql = """
						Delete FROM user_roles
						WHERE user_id = :userId
                        """;

            SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("userId",userID);
            return namedParameterJdbcTemplate.update(sql,namedParameters);

        }
    }
