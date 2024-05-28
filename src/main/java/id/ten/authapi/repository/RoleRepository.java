package id.ten.authapi.repository;

import id.ten.authapi.enums.RoleEnum;
import id.ten.authapi.model.Role;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
  Optional<Role> findByName(RoleEnum name);
}
