package ir.urmia.cloudservice.service;

import ir.urmia.cloudservice.base.service.BaseService;
import ir.urmia.cloudservice.domain.User;

import java.util.Optional;

public interface UserService extends BaseService<User, Long> {

    Optional<User> findByUsername(String username);
}
