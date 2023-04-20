package com.ssafy.mata.repository;

import com.ssafy.mata.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByToken(String token);
}