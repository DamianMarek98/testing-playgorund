package deny.testing.playground.controller;

import deny.testing.playground.model.Person;
import deny.testing.playground.repository.PersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.openMocks;


class PersonControllerTest {
    private PersonController subject;
    @Mock
    private PersonRepository personRepository;
    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        closeable = openMocks(this);
        subject = new PersonController(personRepository);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    void shouldReturnFullNameOfAPerson() throws Exception {
        //given
        Person peter = new Person("Peter", "Pan");
        given(personRepository.findByLastName("Pan")).willReturn(Optional.of(peter));

        //when
        String greeting = subject.hello("Pan");

        //then
        assertEquals("Hello Peter Pan!", greeting);
    }

    @Test
    void shouldTellIfPersonIsUnknown() throws Exception {
        //given
        given(personRepository.findByLastName(anyString()))
                .willReturn(Optional.empty());

        //when
        String greeting = subject.hello("Pan");

        //then
        assertEquals("Who is this 'Pan' you're talking about?", greeting);
    }
}
