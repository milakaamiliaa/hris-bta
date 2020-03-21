package bta.hris.service;

import bta.hris.model.RoleModel;

import java.util.List;

public interface RoleService {
    RoleModel getRoleById(Long id);
    List<RoleModel> getAllRole();
}
