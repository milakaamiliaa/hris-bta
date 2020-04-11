package bta.hris.service;

import bta.hris.model.CalonPengajarModel;

import java.util.List;
import java.util.Optional;

public interface CalonPengajarService {
    CalonPengajarModel createCalonPengajar(CalonPengajarModel calonPengajar);
    Optional<CalonPengajarModel> findById(Long id);
    List<CalonPengajarModel>findAllCalonPengajar();


}
