package id.ten.authapi.service;

import id.ten.authapi.enums.RoleEnum;
import id.ten.authapi.mappers.UserMappers;
import id.ten.authapi.model.Role;
import id.ten.authapi.model.User;
import id.ten.authapi.records.RegisterUserRecord;
import id.ten.authapi.repository.RoleRepository;
import id.ten.authapi.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMappers userMappers;

  public UserService(
      UserRepository userRepository,
      RoleRepository roleRepository,
      PasswordEncoder passwordEncoder,
      UserMappers userMappers) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.userMappers = userMappers;
  }

  public List<User> allUsers() {
    List<User> users = new ArrayList<>();

    userRepository.findAll().forEach(users::add);

    return users;
  }

  public User createAdministrator(RegisterUserRecord input) {
    Optional<Role> adminRole = roleRepository.findByName(RoleEnum.ADMIN);

    if (adminRole.isEmpty()) {
      return null;
    }

    var user = userMappers.toUser(input);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole(adminRole.get());

    return userRepository.save(user);
  }
}
