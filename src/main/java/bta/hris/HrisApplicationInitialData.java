package bta.hris;

import bta.hris.model.RoleModel;
import bta.hris.model.UserModel;
import bta.hris.service.RoleService;
import bta.hris.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class HrisApplicationInitialData implements ApplicationRunner {
    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (roleService.getRoleById(Long.parseLong("1")) == null) {
            RoleModel role = new RoleModel();
            role.setNama("ADMIN");
            roleService.addRole(role);
        }

        if (userService.getByNip("admin") == null) {
            UserModel u = new UserModel();
            u.setPassword("admin");
            u.setActive(true);
            u.setCreatedAt(LocalDate.now());
            u.setAlamat("admin");
            u.setEmail("admin@bta.com");
            u.setNama("Admin");
            u.setNip("admin");
            u.setNoTelp("000000");
            u.setTglLahir(LocalDate.now());
            u.setRole(roleService.getRoleById(Long.parseLong("1")));

            userService.addUser(u);
        }
    }
}
