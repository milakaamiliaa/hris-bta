package bta.hris.service;

import bta.hris.model.RoleModel;
import bta.hris.repository.RoleDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDB roleDb;

    @Override
    public List<RoleModel> findAll() {
        return roleDb.findAll();
    }

}
