package com.pierre.dsvendas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pierre.dsvendas.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
