package bta.hris.service;

import bta.hris.repository.UserDB;
import bta.hris.model.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.ArrayList;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDB userDB;

    @Override
    public UserModel getUserById(String id){
        return userDB.findById(id).get();
    }

    @Override
    public List<UserModel> getUserList(){
        return userDB.findAll();
    }

    @Override
    public void addUser(UserModel user){
        userDB.save(user);
    }

    @Override
    public List<UserModel> getUserByRole(Long role){
        List<UserModel> allUser = userDB.findAll();
        List<UserModel> result = new ArrayList<UserModel>();

        for (UserModel user : allUser){
            if (user.getRole().getIdRole() == role){
                result.add(user);
            }
        }return result;
    }
}
