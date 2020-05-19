package bta.hris.service;

import bta.hris.model.CabangDataModel;
import bta.hris.model.CabangModel;

public interface CabangDataService {
    CabangDataModel addCabangData(CabangDataModel cabangData);
    CabangDataModel getCabangDataByCabangAndPeriode(CabangModel cabang, String periode);
    CabangDataModel updateCabangData(CabangDataModel cabangData);
    Float calculateRasio(Float gaji, Long jumlahSiswa);
}
