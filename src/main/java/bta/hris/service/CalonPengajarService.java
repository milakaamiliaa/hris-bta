package bta.hris.service;

import bta.hris.model.CalonPengajarModel;

import java.util.List;

public interface CalonPengajarService {
    CalonPengajarModel getCalonById(Long id);
    CalonPengajarModel addCalon(CalonPengajarModel calon);
    List<CalonPengajarModel> getAllCalon();
    public String encrypt(String password);
    CalonPengajarModel rekrutCalon(CalonPengajarModel calon);
    boolean hapusCalon(CalonPengajarModel calon);
    CalonPengajarModel tolakCalon(CalonPengajarModel calon);
    CalonPengajarModel undangCalon(CalonPengajarModel calon);

}