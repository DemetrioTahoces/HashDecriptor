package com.demetrio.hashdecriptor.infrastructure.repositories;

import com.demetrio.hashdecriptor.domain.entity.Hash;
import com.demetrio.hashdecriptor.domain.repositories.HashRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
@ConditionalOnProperty(prefix = "hash", name = "repository", havingValue = "sql")
public interface HashRepositoryJpa extends JpaRepository<Hash, String>, HashRepository {

    Hash findByHashAndAlgorithm(String hash, String algorithm);

}
