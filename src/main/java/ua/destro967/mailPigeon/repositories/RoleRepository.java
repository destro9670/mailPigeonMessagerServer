package ua.destro967.mailPigeon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.destro967.mailPigeon.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
