package bta.hris.service;

import bta.hris.model.UserModel;

import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    UserModel addUser(UserModel user);
    List<UserModel> getAllUser();
    public String encrypt(String password);
    UserModel getByNip(String nip);

}
