package pk.org.rayn.zindagi.daos;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import pk.org.rayn.zindagi.domain.User;
import pk.org.rayn.zindagi.domain.UserData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class UserDao extends AbstractDao{
    private static final Logger LOG = LoggerFactory.getLogger(UserDao.class);

    private static RowMapper<User> USERMAPPER = (rs, i) ->
            new User(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("name"),
                    rs.getString("username"),
                    rs.getString("password"));

    private static RowMapper<String> ROLEMAPPER = (rs, i) ->
            new String(rs.getString("user_role"));


    public List<UserData> getAllUsers() {
        String sql = " SELECT * FROM users";
        List<User> res = jdbcTemplate.query(sql, USERMAPPER);
        List<UserData> userData = new ArrayList<UserData>();
        for(int i=0;i< res.size();i++)
        {
            sql = """
                SELECT R.name as user_role FROM users AS U, user_roles AS UR , roles AS R
                Where username=? AND
                U.id = UR.user_id and UR.role_id = R.id
                """;

            List<String> user_roles = jdbcTemplate.query(sql, ROLEMAPPER ,res.get(i).userName());
            userData.add(new UserData(res.get(i),user_roles));
        }

        return userData;
    }

    public UserData getUser(String userName) {
        String sql = """
                    SELECT * FROM users
                    Where username=?
                    """;

        List<User> res = jdbcTemplate.query(sql, USERMAPPER,userName);

        sql = """
                SELECT R.name as user_role FROM users AS U, user_roles AS UR , roles AS R
                Where username=? AND
                U.id = UR.user_id and UR.role_id = R.id
                """;

        List<String> user_roles = jdbcTemplate.query(sql, ROLEMAPPER ,userName);

        UserData userData = new UserData(res.get(0),user_roles);

        return userData;
    }

    public List<User> getUserById(UUID userId) {
        String sql = """
                    SELECT * FROM users 
                    Where id=?
                    """;
        List<User> res = jdbcTemplate.query(sql, USERMAPPER,userId);

        return res;
    }


    public UUID addNewUser(UserData newUser) {
        String sql = """
						INSERT INTO users (name, username, password)
						VALUES ( :name, :username,:password )
						RETURNING id
						""";

        return namedParameterJdbcTemplate.queryForObject(sql,
                ImmutableMap.of("name", newUser.user().fullName(), "username", newUser.user().userName(), "password", newUser.user().password()), UUID.class);

    }


    public UUID updateUser(UserData newUser) {
        String sql = """
						UPDATE users
                        SET name = :name, password = :password
                        WHERE username=:username
                        RETURNING id
						""";

        UUID userID = null;
        try {
            userID = namedParameterJdbcTemplate.queryForObject(sql,
                    ImmutableMap.of("name", newUser.user().fullName(), "password", newUser.user().password(), "username", newUser.user().userName()), UUID.class);
        }
        catch (EmptyResultDataAccessException e)
        {
            throw new UsernameNotFoundException("UserName does not exist");
        }

        return userID;
    }

    public int deleteUser(String userName){
        String sql = """
						Delete FROM USERS
						WHERE username = :userName
                        """;

        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("userName",userName);
        return namedParameterJdbcTemplate.update(sql,namedParameters);
    }



}




