package pk.org.rayn.zindagi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pk.org.rayn.zindagi.domain.ResponseObject;
import pk.org.rayn.zindagi.domain.Role;
import pk.org.rayn.zindagi.domain.User;
import pk.org.rayn.zindagi.domain.UserData;
import pk.org.rayn.zindagi.services.RoleService;
import pk.org.rayn.zindagi.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @PreAuthorize("hasRole('Admin')")
    @GetMapping({"/getUsers"})
    public ResponseObject<List<UserData>> getUsers()
    {
        return new ResponseObject<List<UserData>>(userService.getAllUsers());
    }

    @PostMapping ({"/registerUser"})
    @PreAuthorize("hasRole('Admin')")
    public ResponseObject<UUID> registerUser(
            @RequestBody(required = true) UserData newUser
            ) {

          if(newUser.roles() == null || newUser.user() == null)
              throw new RequestRejectedException("Bad Request");


         return new ResponseObject<UUID>(userService.registerNewUser(newUser));
    }

    @PostMapping ({"/test"})
    @PreAuthorize("hasRole('Admin')")
    public ResponseObject<Boolean> Test(
            @RequestBody(required = true) String test
    ) {
        return new ResponseObject<Boolean>(true);
    }


    @PreAuthorize("hasRole('Admin')")
    @PatchMapping ({"/updateUser"})
    public ResponseObject<UUID> updateUser(
            @RequestBody(required = true) UserData updatedUser
    ) throws Exception {

        if(updatedUser.roles() == null || updatedUser.user() == null)
            throw new RequestRejectedException("Bad Request");

        return new ResponseObject<UUID>(userService.updateUser(updatedUser));
    }

    @PreAuthorize("hasRole('Admin') ||  hasRole('User')")
    @DeleteMapping ({"/deleteUser"})
    public ResponseObject<Integer> deleteUser(
            @RequestParam(required = true) String userName
    ) throws Exception {
        return new ResponseObject<Integer>(userService.deleteUser(userName));
    }


    @PreAuthorize("hasRole('Admin')")
    @GetMapping ({"/getUserByUsername"})
    public ResponseObject<UserData> getUser(
            @RequestParam(required = true) String userName
    )
    {
        return new ResponseObject<UserData>(userService.getUserDetail(userName));
    }

    @GetMapping ({"/load"})
    public ResponseObject<Boolean> getUser() throws Exception {
        roleService.addNewRole(new Role(null,"Admin","Administrator"));
        roleService.addNewRole(new Role(null,"User","User with Read Write Access"));
        roleService.addNewRole(new Role(null,"Approver","Approve updates"));

        List<String> roles = new ArrayList<String>();
        roles.add("Admin");
        roles.add("User");
        roles.add("Approver");

        registerUser(new UserData(new User(null,"admin","admin","admin"),roles));

            return new ResponseObject<Boolean>(true);
    }
}
