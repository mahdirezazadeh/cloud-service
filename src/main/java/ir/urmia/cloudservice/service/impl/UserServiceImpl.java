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
import org.springframework.stereotype.Service;

import java.util.Optional;

@Import({RedisConfig.class})
@EnableCaching
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository> implements UserService {
    public UserServiceImpl(UserRepository repository) {
        super(repository);
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
        Optional<User> dbUser = repository.findByUsername(user.getUsername());
        if (dbUser.isPresent()) {
            throw new UniqueAttributeAlreadyTakenException("username " + user.getUsername() + " already exist!");
        }

        dbUser = repository.findByEmail(user.getEmail());
        if (dbUser.isPresent()) {
            throw new UniqueAttributeAlreadyTakenException("email " + user.getEmail() + " already exist!");
        }

        dbUser = repository.findByPhoneNumber(user.getPhoneNumber());
        if (dbUser.isPresent()) {
            throw new UniqueAttributeAlreadyTakenException("phone number " + user.getPhoneNumber() + " already exist!");
        }
        return save(user);
    }
}
