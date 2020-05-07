package bta.hris.service;

import bta.hris.model.CalonPengajarModel;

import java.util.List;

public interface CalonPengajarService {
    CalonPengajarModel getCalonById(String id);
    CalonPengajarModel getCalonByUsername(String username);
    CalonPengajarModel addCalon(CalonPengajarModel calon);
    List<CalonPengajarModel> getAllCalon();
    public String encrypt(String password);
    CalonPengajarModel rekrutCalon(CalonPengajarModel calon);
    boolean hapusCalon(CalonPengajarModel calon);
    CalonPengajarModel tolakCalon(CalonPengajarModel calon);
    CalonPengajarModel undangCalon(CalonPengajarModel calon);
    CalonPengajarModel createCalonPengajar(CalonPengajarModel calonPengajar);

}

