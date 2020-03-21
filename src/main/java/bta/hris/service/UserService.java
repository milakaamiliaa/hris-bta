package bta.hris.service;

import bta.hris.model.UserModel;

import java.util.List;

public interface UserService {
    UserModel getUserById(String id);
    UserModel addUser(UserModel user);
    List<UserModel> getAllUser();
    public String encrypt(String password);
    List<UserModel> getUserByRole(Long role);
    UserModel changeUser(UserModel pegawai);
    boolean deleteUser(UserModel user);
    UserModel getByNip(String nip);

}
