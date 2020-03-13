package bta.hris.service;

import bta.hris.model.CabangModel;

import java.util.List;
import java.util.Optional;

public interface CabangService {
    List<CabangModel> getCabangList();
    Optional<CabangModel> getCabangByIdCabang(Long idCabang);
    void createCabang(CabangModel cabang);
    CabangModel updateCabang(CabangModel cabang);
    void deleteCabang(CabangModel cabang);
}
