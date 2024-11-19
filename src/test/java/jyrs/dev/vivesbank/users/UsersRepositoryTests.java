package jyrs.dev.vivesbank.users;

import jyrs.dev.vivesbank.users.models.Direction;
import jyrs.dev.vivesbank.users.models.Role;
import jyrs.dev.vivesbank.users.models.User;
import jyrs.dev.vivesbank.users.users.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UsersRepositoryTests {
    User user = User.builder()
            .username("usuario@correo.com")
            .password("password123")
            .fotoPerfil("profile.jpg")
            .roles(Set.of( Role.USER))
            .build();
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp(){
        testEntityManager.persist(user);
        testEntityManager.flush();
    }

    @Test
    void findByUsername() {
        User result = usersRepository.findByUsername(user.getUsername());
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(user.getUsername(), result.getUsername())
        );
    }

    @Test
    void findByUsernameNotFound(){
        User result = usersRepository.findByUsername("user@notfound.com");
        assertNull(result);
    }
}