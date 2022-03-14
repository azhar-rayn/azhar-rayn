package pk.org.rayn.zindagi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pk.org.rayn.zindagi.domain.ResponseObject;
import pk.org.rayn.zindagi.domain.Role;
import pk.org.rayn.zindagi.services.RoleService;

import java.util.List;
import java.util.UUID;


@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true" )
@RestController
@RequestMapping("/v1/roles")
public class RoleController {
    @Autowired
    RoleService roleService;

    @GetMapping({"/getRoles"})
    public ResponseObject<List<Role>> getRoles()
    {
        return new ResponseObject<List<Role>>(roleService.getAllRoles());
    }

    @PreAuthorize("hasRole('Admin')")
    @PostMapping ({"/addNewRole"})
    public ResponseObject<UUID> addNewRole(
            @RequestBody(required = true) Role newRole  
            )
    {
        return new ResponseObject<UUID>(roleService.addNewRole(newRole));
    }

}
