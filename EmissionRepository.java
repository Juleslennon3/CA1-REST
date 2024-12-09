package com.greenhouse.repository;

import com.greenhouse.model.Emission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmissionRepository extends JpaRepository<Emission, Long> {
}
