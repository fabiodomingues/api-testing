package br.com.fd.domain.users;

import org.junit.Before;
import org.junit.Test;

import static br.com.fd.infrastructure.builders.UserBuilder.aUser;
import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest {

    private static final User ALICE = aUser().withUsername("Alice").build();
    private static final User CHARLIE = aUser().withUsername("Charlie").build();

    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository = new UserRepository();
    }

    @Test
    public void should_inform_when_a_username_is_already_taken() {
        userRepository.add(ALICE);

        assertThat(userRepository.isUsernameTaken(ALICE.getUsername())).isTrue();
        assertThat(userRepository.isUsernameTaken(CHARLIE.getUsername())).isFalse();
    }

    @Test
    public void should_return_all_users() {
        userRepository.add(ALICE);
        userRepository.add(CHARLIE);

        assertThat(userRepository.all()).containsExactly(ALICE, CHARLIE);
    }

}