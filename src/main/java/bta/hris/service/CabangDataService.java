package bta.hris.service;

import bta.hris.model.CabangDataModel;
import bta.hris.model.CabangModel;

import java.util.List;

public interface CabangDataService {
    CabangDataModel addCabangData(CabangDataModel cabangData);
    CabangDataModel getCabangDataByCabangAndPeriode(CabangModel cabang, String periode);
    CabangDataModel updateCabangData(CabangDataModel cabangData);
    Float calculateRasio(Float gaji, Long jumlahSiswa);
    List<CabangDataModel> getCabangDatasForXPeriodBeforeNow(int x, CabangModel cabang);
}
