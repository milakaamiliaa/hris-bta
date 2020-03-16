package bta.hris.service;

import bta.hris.model.UserModel;

import java.util.List;

public interface UserService {
    UserModel getUserById(String id);
    List<UserModel> getAllUser();
    UserModel addUser(UserModel user);
    public String encrypt(String password);
    List<UserModel> getUserByRole(Long role);
    UserModel changeUser(UserModel pegawai);
    boolean deleteUser(UserModel user);
}
