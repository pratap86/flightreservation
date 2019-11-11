package com.pratap.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pratap.flightreservation.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
