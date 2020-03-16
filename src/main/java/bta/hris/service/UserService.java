package bta.hris.service;

import bta.hris.model.UserModel;

import java.util.List;

public interface UserService {
    UserModel getUserById(String id);
    List<UserModel> getUserList();
    void addUser(UserModel user);
    List<UserModel> getUserByRole(Long role);
}
