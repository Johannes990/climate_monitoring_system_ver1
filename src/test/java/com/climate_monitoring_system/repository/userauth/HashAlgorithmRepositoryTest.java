package com.climate_monitoring_system.repository.userauth;

import com.climate_monitoring_system.domain.userauth.HashAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HashAlgorithmRepositoryTest {
    @Autowired
    private HashAlgorithmRepository hashAlgorithmRepository;
    private final String hashAlgorithmName = "MD5";

    public HashAlgorithm hashAlgorithmEntityGen() {
        HashAlgorithm hashAlgorithm = new HashAlgorithm();

        hashAlgorithm.setHashAlgorithmName(hashAlgorithmName);

        return hashAlgorithm;
    }

    @Test
    public void testHashAlgorithmRepositoryNotNull() {
        assertThat(hashAlgorithmRepository).isNotNull();
    }

    @Test
    public void testHashAlgorithmRepository() {
        HashAlgorithm hashAlgorithm = hashAlgorithmEntityGen();

        long countBefore = hashAlgorithmRepository.count();
        assertThat(countBefore).isEqualTo(0);

        HashAlgorithm savedHashAlgorithm = hashAlgorithmRepository.save(hashAlgorithm);

        long countAfter = hashAlgorithmRepository.count();
        assertThat(countAfter).isEqualTo(1);

        Optional<HashAlgorithm> hashAlgorithmOptional = hashAlgorithmRepository
                .findById(savedHashAlgorithm.getHashAlgorithmId());

        if (hashAlgorithmOptional.isPresent()) {
            HashAlgorithm hashAlgorithmSaved = hashAlgorithmOptional.get();

            assertThat(hashAlgorithmSaved.getHashAlgorithmName()).isEqualTo(hashAlgorithmName);
        }
    }
}
