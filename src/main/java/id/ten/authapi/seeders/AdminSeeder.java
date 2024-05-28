package id.ten.authapi.seeders;

import id.ten.authapi.enums.RoleEnum;
import id.ten.authapi.model.Role;
import id.ten.authapi.model.User;
import id.ten.authapi.records.RegisterUserRecord;
import id.ten.authapi.repository.RoleRepository;
import id.ten.authapi.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public AdminSeeder(
            RoleRepository roleRepository,
            UserRepository  userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createSuperAdministrator();
    }

    private void createSuperAdministrator() {
        RegisterUserRecord userDto = new RegisterUserRecord("super.admin@email.com","123456","Super Admin");

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);
        Optional<User> optionalUser = userRepository.findByEmail(userDto.email());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        var user = new User();
                user.setFullName(userDto.fullName());
                user.setEmail(userDto.email());
                user.setPassword(passwordEncoder.encode(userDto.password()));
                user.setRole(optionalRole.get());

        userRepository.save(user);
    }
}