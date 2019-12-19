package com.javamaster.initializer;
import com.javamaster.dao.RoleRepo;
import com.javamaster.dao.UserDAO;
import com.javamaster.model.Role;
import com.javamaster.model.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Set;
@Component
public class TestDataInitializer implements InitializingBean {
    @Autowired
    private PasswordEncoder passwordEncoder;// = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleRepo roleRepo;
    private void testUpdate() throws SQLException {
        Set<Role> roleSet = Collections.singleton(roleRepo.findById(1L).get());
        User user = new User();
        user.setId(1);
        user.setName("upd_Ivan");
        user.setPassword(passwordEncoder.encode("jjj"));
        user.setRoles(roleSet);
        user.setEmail(null);
        userDAO.save(user);
    }
    private void datainit() throws SQLException {
        Role role = new Role();
        role.setRole("ROLE_ADMIN");
        Role role2 = new Role();
        role2.setRole("ROLE_USER");
        roleRepo.save(role);
        roleRepo.save(role2);


        Set<Role> roleSet = Collections.singleton(roleRepo.findById(1L).get());
        User user = new User();
        user.setName("Ivan");
        user.setRoles(roleSet);
        user.setPassword(passwordEncoder.encode("123"));
        user.setEmail(null);
        userDAO.save(user);
        //userDAO.insertUser(user.getName(),user.getPassword(),user.getRoles());

        Set<Role> roleSet2 = Collections.singleton(roleRepo.findById(2L).get());
        User user2 = new User();
        user2.setName("Vovan");
        user2.setRoles(roleSet2);
        user2.setPassword(passwordEncoder.encode("9011"));
        user2.setEmail(null);
        userDAO.save(user2);
        //userDAO.insertUser(user2.getName(),user2.getPassword(),user2.getRoles());
    }
    private void init() throws SQLException {
        datainit();
        testUpdate();
    }
    @PostConstruct
    public void postConstruct() throws SQLException {
        init();
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        //
    }
}