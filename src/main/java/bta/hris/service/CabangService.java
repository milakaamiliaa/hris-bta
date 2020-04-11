package bta.hris.service;

import bta.hris.model.CabangModel;
import bta.hris.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface CabangService {
    List<CabangModel> getCabangList();
    Optional<CabangModel> getCabangByIdCabang(Long idCabang);
    void createCabang(CabangModel cabang);
    CabangModel updateCabang(CabangModel cabang);
    void deleteCabang(CabangModel cabang);
    Optional<CabangModel> getCabangByStafCabang(UserModel stafCabang);
    void detailCabang(CabangModel cabang);

}
