package ir.urmia.cloudservice.service.impl;

import ir.urmia.cloudservice.base.service.impl.BaseServiceImpl;
import ir.urmia.cloudservice.config.RedisConfig;
import ir.urmia.cloudservice.domain.User;
import ir.urmia.cloudservice.exception.UniqueAttributeAlreadyTakenException;
import ir.urmia.cloudservice.repository.UserRepository;
import ir.urmia.cloudservice.service.UserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Import({RedisConfig.class})
@EnableCaching
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository> implements UserService {
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        super(repository);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Cacheable(value = "usernameCache")
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    @Cacheable(value = "usernameCache")
    public Optional<User> findById(Long aLong) {
        return super.findById(aLong);
    }

    @Override
    public User singUp(User user) {
        boolean flag = false;
        String error = "";
        Optional<User> dbUser = repository.findByUsername(user.getUsername());
        if (dbUser.isPresent()) {
            flag = true;
            error = "username " + user.getUsername() + " already exist!\n";
        }

        dbUser = repository.findByEmail(user.getEmail());
        if (dbUser.isPresent()) {
            flag = true;
            error += "email " + user.getEmail() + " already exist!\n";
        }

        dbUser = repository.findByPhoneNumber(user.getPhoneNumber());
        if (dbUser.isPresent()) {
            flag = true;
            error += "phone number " + user.getPhoneNumber() + " already exist!\n";
        }
        if (flag) {
            throw new UniqueAttributeAlreadyTakenException(error);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return save(user);
    }
}
