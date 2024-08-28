package com.climate_monitoring_system.repository.userauth;

import com.climate_monitoring_system.domain.userauth.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GenderRepositoryTest {

    @BeforeEach
    public void cleanUpGenderRepository() {
        genderRepository.deleteAll();
    }

    @Autowired
    private GenderRepository genderRepository;
    private final String mGender = "Male";
    private final String fGender = "Female";

    public Gender genderEntityGen(String genderStr) {
        Gender gender = new Gender();

        gender.setGenderName(genderStr);

        return gender;
    }

    public Gender saveGender(Gender gender) {
        return genderRepository.save(gender);
    }

    @Test
    public void testGenderRepositoryNotNull() {
        assertThat(genderRepository).isNotNull();
    }

    @Test
    public void testGenderRepositoryEmpty() {
        long count = genderRepository.count();

        assertThat(count).isEqualTo(0);
    }

    @Test
    public void testGenderRepositorySave() {
        saveGender(genderEntityGen(mGender));

        long count = genderRepository.count();
        assertThat(count).isEqualTo(1);
    }

    @Test
    public void testGenderRepositorySaveIsTransactional() {
        long countBeforeSave = genderRepository.count();

        saveGender(genderEntityGen(fGender));

        long countAfterSave = genderRepository.count();

        assertThat(countBeforeSave).isEqualTo(0);
        assertThat(countAfterSave).isEqualTo(countBeforeSave + 1);
    }

    @Test
    public void testGenderRepositoryDelete() {
        Gender savedGender = saveGender(genderEntityGen(fGender));
        genderRepository.delete(savedGender);

        long count = genderRepository.count();
        assertThat(count).isEqualTo(0);
    }

    @Test
    public void testGenderRepositoryDeleteIsTransactional() {
        Gender savedGenderFemale = saveGender(genderEntityGen(fGender));
        Gender savedGenderMale = saveGender(genderEntityGen(mGender));

        assertThat(genderRepository.count()).isEqualTo(2);
        genderRepository.delete(savedGenderFemale);
        assertThat(genderRepository.count()).isEqualTo(1);
        genderRepository.delete(savedGenderMale);
        assertThat(genderRepository.count()).isEqualTo(0);
    }

    @Test
    public void testGenderRepositoryEntityValuesCorrect() {
        Gender savedGenderMale = saveGender(genderEntityGen(mGender));
        Gender savedGenderFemale = saveGender(genderEntityGen(fGender));

        Optional<Gender> foundGenderMale = genderRepository.findById(savedGenderMale.getGenderId());
        Optional<Gender> foundGenderFemale = genderRepository.findById(savedGenderFemale.getGenderId());

        assertThat(foundGenderMale).isPresent();
        assertThat(foundGenderFemale).isPresent();
        assertThat(foundGenderMale.get().getGenderName()).isEqualTo(mGender);
        assertThat(foundGenderFemale.get().getGenderName()).isEqualTo(fGender);
    }
}
