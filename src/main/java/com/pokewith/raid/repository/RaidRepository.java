package com.pokewith.raid.repository;

import com.pokewith.raid.Raid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RaidRepository extends JpaRepository<Raid, Long> {
    Optional<Raid> findById(Long raidId);
}
