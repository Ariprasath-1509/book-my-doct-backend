package com.bookmydoct.auth.repository;

import com.bookmydoct.auth.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

//    Optional<Role> findByRoleCodeAndRoleName(String roleCode, String roleName);
    Optional<Role> findByRoleCode(String roleCode);

    boolean existsByRoleName(String roleName);
    boolean existsByRoleCode(String roleCode);

    Optional<Role> findByUuid(UUID uuid);

}
