package brokilone.todo.service;

import brokilone.todo.dto.UserDto;
import brokilone.todo.model.Role;
import brokilone.todo.model.User;
import brokilone.todo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


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

    @Autowired
    private MailSender mailSender;

    public boolean registerNewUserAccount(final UserDto accountDto){
        Optional<User> userByEmail = findUserByEmail(accountDto.getEmail());
        if (userByEmail.isPresent()) {
            return false;
        }
        final User user = new User();

        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        user.setActivationCode(UUID.randomUUID().toString());
        user.setRole(Role.ROLE_USER);

        userRepo.save(user);

        String message = String.format("Добро пожаловать в умный календарь, %s %s! \n" +
                        "Для активации аккаунта необходимо перейти по ссылке: http://localhost:8080/activate/%s",
                user.getFirstName(), user.getLastName(), user.getActivationCode());
        mailSender.send(user.getEmail(), "Activation code", message);
        return true;
    }

    public Optional<User> findUserByEmail(final String email) {
        return userRepo.findByEmail(email);
    }


    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void save(User user) {
        userRepo.save(user);
    }

    public Optional<User> findUserById(Long id) {
        return userRepo.findById(id);
    }

    public boolean activateUser(String code) {
        Optional<User> byActivationCode = userRepo.findByActivationCode(code);
        if(!byActivationCode.isPresent()) {
            return false;
        }
        User user = byActivationCode.get();
        user.setActivationCode(null);
        user.setEnabled(true);
        userRepo.save(user);
        return true;
    }
}
