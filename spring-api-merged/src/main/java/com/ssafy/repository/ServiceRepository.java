package com.ssafy.repository;

import com.ssafy.entity.Servi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Servi, Long> {

}
