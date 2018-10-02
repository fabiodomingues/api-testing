package br.com.fd.domain.users;

import java.util.List;

public class UserService {

    private IdGenerator idGenerator;
    private UserRepository userRepository;

    public UserService(IdGenerator idGenerator, UserRepository userRepository) {
        this.idGenerator = idGenerator;
        this.userRepository = userRepository;
    }

    public User createUser(RegistrationData registrationData) throws UsernameAlreadyInUseException {
        validateUsername(registrationData.getUsername());

        User user = createUserFrom(registrationData);

        userRepository.add(user);

        return user;
    }

    public List<User> allUsers() {
        return userRepository.all();
    }

    private void validateUsername(String username) throws UsernameAlreadyInUseException {
        if (userRepository.isUsernameTaken(username)) {
            throw new UsernameAlreadyInUseException();
        }
    }

    private User createUserFrom(RegistrationData registrationData) {
        String userId = idGenerator.next();
        return new User(userId,
                registrationData.getName(),
                registrationData.getUsername(),
                registrationData.getPassword());
    }
}