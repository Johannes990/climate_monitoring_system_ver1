package com.climate_monitoring_system.repository.userauth;

import com.climate_monitoring_system.domain.userauth.HashAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HashAlgorithmRepositoryTest {
    @Autowired
    private HashAlgorithmRepository hashAlgorithmRepository;

    @Autowired
    TestEntityManager entityManager;

    private final String hashAlgorithmName = "MD5";

    private HashAlgorithm hashAlgorithmEntityGen(String algorithmName) {
        HashAlgorithm hashAlgorithm = new HashAlgorithm();

        hashAlgorithm.setHashAlgorithmName(algorithmName);

        return hashAlgorithm;
    }

    private HashAlgorithm saveHashAlgorithmEntity(HashAlgorithm hashAlgorithm) {
        return hashAlgorithmRepository.save(hashAlgorithm);
    }

    @Test
    public void testHashAlgorithmRepositoryNotNull() {
        assertThat(hashAlgorithmRepository)
                .isNotNull();
    }

    @Test
    public void testHashAlgorithmRepositorySaveSuccess() {
        final String algorithmName = "Super special algorithm";
        HashAlgorithm createdAlgorithm = hashAlgorithmEntityGen(algorithmName);
        HashAlgorithm savedAlgorithm = saveHashAlgorithmEntity(createdAlgorithm);
        assertThat(entityManager.find(HashAlgorithm.class, savedAlgorithm.getHashAlgorithmId()))
                .isEqualTo(createdAlgorithm);
    }

    @Test
    public void testHashAlgorithmRepositoryUpdateSuccess() {
        final String algorithmName = "Super special algorithm #2";
        final String newAlgorithmName = "Super special algorithm #3";
        HashAlgorithm createdAlgorithm = hashAlgorithmEntityGen(algorithmName);
        entityManager.persist(createdAlgorithm);
        createdAlgorithm.setHashAlgorithmName(newAlgorithmName);
        hashAlgorithmRepository.save(createdAlgorithm);
        assertThat(entityManager.find(HashAlgorithm.class, createdAlgorithm.getHashAlgorithmId())
                .getHashAlgorithmName()).isEqualTo(newAlgorithmName);
    }

    @Test
    public void testHashAlgorithmRepositoryFindByIdSuccess() {
        final String algorithmName = "SHA256";
        HashAlgorithm createdAlgorithm = hashAlgorithmEntityGen(algorithmName);
        entityManager.persist(createdAlgorithm);
        Optional<HashAlgorithm> retrievedHashAlgorithm = hashAlgorithmRepository
                .findById(createdAlgorithm.getHashAlgorithmId());
        assertThat(retrievedHashAlgorithm).contains(createdAlgorithm);
    }

    @Test
    public void testHasAlgorithmRepositoryDeleteSuccess() {
        final String algorithmName = "SHA256";
        HashAlgorithm createdAlgorithm = hashAlgorithmEntityGen(algorithmName);
        entityManager.persist(createdAlgorithm);
        hashAlgorithmRepository.delete(createdAlgorithm);
        assertThat(entityManager.find(HashAlgorithm.class, createdAlgorithm.getHashAlgorithmId()))
                .isNull();
    }
}
