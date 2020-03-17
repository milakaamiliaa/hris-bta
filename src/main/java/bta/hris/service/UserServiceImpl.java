package bta.hris.service;

import bta.hris.model.UserModel;
import bta.hris.repository.UserDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    UserDB userDb;

    @Override
    public List<UserModel> getStafList(){
        return userDb.findAll();
    }
}
