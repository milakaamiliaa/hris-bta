package bta.hris.service;

import bta.hris.model.RoleModel;
import bta.hris.repository.RoleDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDB RoleDB;

    @Override
    public RoleModel getRoleById(Long id){
        return RoleDB.findById(id).get();
    }

    @Override
    public List<RoleModel> getRoleList(){
        return RoleDB.findAll();
    }
}
