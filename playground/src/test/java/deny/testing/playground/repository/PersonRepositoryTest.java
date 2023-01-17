package deny.testing.playground.repository;

import deny.testing.playground.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @AfterEach
    public void tearDown() {
        personRepository.deleteAll();
    }

    @Test
    void shouldSaveAndFetchPerson() {
        Person peter = new Person("Peter", "Parker");
        personRepository.save(peter);

        Optional<Person> maybePeter = personRepository.findByLastName("Parker");

        assertEquals(Optional.of(peter), maybePeter);
    }
}
