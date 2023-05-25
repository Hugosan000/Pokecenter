package poke.center.api.domain.role;

import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository {
    Role findByRoleName(String roleName);
}
