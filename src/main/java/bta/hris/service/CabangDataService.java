package bta.hris.service;

import bta.hris.model.CabangDataModel;
import bta.hris.model.CabangModel;

import java.time.LocalDate;
import java.util.List;

public interface CabangDataService {
    CabangDataModel addCabangData(CabangDataModel cabangData);
    CabangDataModel getCabangDataByCabangAndPeriode(CabangModel cabang, String periode);
    CabangDataModel getCabangDataByCabangAndCreatedAt(CabangModel cabang, LocalDate createdAt);
    CabangDataModel updateCabangData(CabangDataModel cabangData);
    Float calculateRasio(Float gaji, Long jumlahSiswa);
    int calculateTotalPayroll(CabangDataModel cabang);
    List<CabangDataModel> getCabangDatasForXPeriodBeforeNow(int x, CabangModel cabang);
    List<CabangDataModel> getCabangDataByCabang(CabangModel cabang);

}
