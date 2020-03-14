package bta.hris.service;

import bta.hris.model.UserModel;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserModel addUser(UserModel user);
    public String encrypt(String password);
}
