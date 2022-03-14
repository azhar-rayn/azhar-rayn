package pk.org.rayn.zindagi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pk.org.rayn.zindagi.daos.RoleDao;
import pk.org.rayn.zindagi.daos.UserDao;
import pk.org.rayn.zindagi.daos.UserRoleDao;
import pk.org.rayn.zindagi.domain.Role;
import pk.org.rayn.zindagi.domain.User;
import pk.org.rayn.zindagi.domain.UserData;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    UserRoleDao userRoleDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<UserData> getAllUsers()
    {
        return userDao.getAllUsers();
    }

    public UserData getUserDetail(String userName)
    {
        return userDao.getUser(userName);
    }

    public UUID registerNewUser(UserData newUser)
    {
        BCryptPasswordEncoder Encoder= new BCryptPasswordEncoder();
        User user = new User(null,newUser.user().fullName(),newUser.user().userName(),Encoder.encode(newUser.user().password()));
        UserData userData = new UserData(user,newUser.roles());
        UUID userId = userDao.addNewUser(userData);
        List<Role> roles = roleDao.getRoleByName(newUser.roles());
        for(int i=0; i < roles.size();i++)
        {
            userRoleDao.addUserRole(userId,roles.get(i).id());
        }

        return userId;
    }

    public UUID updateUser(UserData newUser)
    {
        BCryptPasswordEncoder Encoder= new BCryptPasswordEncoder();
        User user = new User(null,newUser.user().fullName(),newUser.user().userName(),Encoder.encode(newUser.user().password()));
        UserData userData = new UserData(user,newUser.roles());
        UUID userId = userDao.updateUser(userData);
        userRoleDao.deleteUserRole(userId);
        List<Role> roles = roleDao.getRoleByName(newUser.roles());
        for(int i=0; i < roles.size();i++)
        {
            userRoleDao.addUserRole(userId,roles.get(i).id());
        }

        return userId;
    }

    public int deleteUser(String userName) {
        return userDao.deleteUser(userName);
    }
}
