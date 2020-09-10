package brokilone.todo.service;

import brokilone.todo.dto.UserDto;
import brokilone.todo.model.Role;
import brokilone.todo.model.User;
import brokilone.todo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * UserService
 * created by Ksenya_Ushakova at 26.08.2020
 */
@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User registerNewUserAccount(final UserDto accountDto) throws Exception {
        Optional<User> userByEmail = findUserByEmail(accountDto.getEmail());
        if (userByEmail.isPresent()) {
            throw new Exception("Пользователь с таким e-mail уже зарегистрирован в системе!");
        }
        final User user = new User();

        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        user.setRole(Role.ROLE_USER);
        return userRepo.save(user);
    }

    public Optional<User> findUserByEmail(final String email) {
        return userRepo.findByEmail(email);
    }


}
