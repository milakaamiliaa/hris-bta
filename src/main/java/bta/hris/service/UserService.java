package bta.hris.service;

import bta.hris.model.UserModel;

public interface UserService {
    UserModel getByNip(String nip);
}
