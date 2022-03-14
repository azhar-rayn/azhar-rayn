package pk.org.rayn.zindagi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pk.org.rayn.zindagi.daos.RoleDao;
import pk.org.rayn.zindagi.domain.Role;

import java.util.List;
import java.util.UUID;

@Service
public class RoleService {
    @Autowired
    RoleDao roleDao;

    public List<Role> getAllRoles()
    {
        return roleDao.getAllRoles();
    }

    public UUID addNewRole(Role newRole)
    {
        return roleDao.addNewRole(newRole);
    }
}
