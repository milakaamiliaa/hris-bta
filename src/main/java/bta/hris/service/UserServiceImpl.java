package bta.hris.service;

import bta.hris.model.UserModel;
import bta.hris.repository.UserDB;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDB userDB;


    @Override
    public UserModel getByNip(String nip) {
        return userDB.findByNip(nip);
    }
}
