package bta.hris.service;

import bta.hris.model.UserModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserModel addUser(UserModel user);
    List<UserModel> getAllUser();
    public String encrypt(String password);
}
