package com.sm.sm_project.repositories;

import com.sm.sm_project.modele.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findRoleByNomRole(String nomRole);
}
