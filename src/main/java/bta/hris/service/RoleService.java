package bta.hris.service;

import bta.hris.model.RoleModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    List<RoleModel> findAll();
}
