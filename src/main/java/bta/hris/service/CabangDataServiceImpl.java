package bta.hris.service;

import bta.hris.model.CabangDataModel;
import bta.hris.model.CabangModel;
import bta.hris.model.PresensiModel;
import bta.hris.repository.CabangDataDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

@Service
public class CabangDataServiceImpl implements CabangDataService {

    @Autowired
    CabangDataDB cabangDataDB;

    @Override
    public CabangDataModel addCabangData(CabangDataModel cabangData) {
        cabangData.setJumlahSiswa(cabangData.getCabang().getJumlahSiswa());

        Float gaji = Float.parseFloat("0");

        if (cabangData.getListPresensi() != null) {
            for (PresensiModel p : cabangData.getListPresensi()) {
                gaji += p.getNominal();
            }
        }

        Long jumlahSiswa = cabangData.getCabang().getJumlahSiswa();
        Float rasio = calculateRasio(gaji, jumlahSiswa);
        cabangData.setRasio(rasio);

        List<PresensiModel> listPresensi = new ArrayList<PresensiModel>();
        cabangData.setListPresensi(listPresensi);
        LocalDate tanggal = LocalDate.now();
        String month = "";
        if (String.valueOf(tanggal.getMonthValue()).length() == 1) {
            month = "0" + tanggal.getMonthValue();
        }

        else {
            month = String.valueOf(tanggal.getMonthValue());
        }
        String year = String.valueOf(tanggal.getYear()).substring(2, 4);
        cabangData.setPeriode(month + year);

        cabangData.setCreatedAt(LocalDate.now());

        return cabangDataDB.save(cabangData);
    }

    @Override
    public CabangDataModel getCabangDataByCabangAndPeriode(CabangModel cabang, String periode) {
        return cabangDataDB.findByCabangAndPeriode(cabang, periode);
    }

    @Override
    public CabangDataModel updateCabangData(CabangDataModel cabangData) {
        Float gaji = Float.parseFloat("0");

        for (PresensiModel p : cabangData.getListPresensi()) {
            gaji += p.getNominal();
        }

        cabangData.setRasio(calculateRasio(gaji, cabangData.getJumlahSiswa()));
        return cabangDataDB.save(cabangData);
    }

    @Override
    public List<CabangDataModel> getCabangDatasForXPeriodBeforeNow(int x, CabangModel cabang) {
        LocalDate nowTime = LocalDate.now();
        LocalDate nowMinusOne = nowTime.withMonth(nowTime.getMonthValue() - 1);

        LocalDate end = nowMinusOne.with(lastDayOfMonth()); // 'end' untuk query

        LocalDate minusOneMinusX = end.minusMonths(x);

        LocalDate start = minusOneMinusX.with(firstDayOfMonth()); // 'start' untuk query

        return cabangDataDB.findAllByCabangAndCreatedAtBetweenOrderByCreatedAtAsc(cabang, start, end);
    }

    @Override
    public Float calculateRasio(Float gaji, Long jumlahSiswa) {
        if (gaji.equals(Float.parseFloat("0"))) {
            return Float.parseFloat("0");
        }

        Float rasio = jumlahSiswa / gaji * 1000;
        return rasio;
    }

    @Override
    public List<CabangDataModel> getCabangDataByCabang(CabangModel cabang) {
        return cabangDataDB.findByCabang(cabang);
    }

    @Override
    public CabangDataModel getCabangDataByCabangAndCreatedAt(CabangModel cabang, LocalDate createdAt) {
        CabangDataModel cabangData = new CabangDataModel();
        List<CabangDataModel> cabangDataList = getCabangDataByCabang(cabang);
        for (CabangDataModel cabangDataModel : cabangDataList) {
            if (cabangDataModel.getCreatedAt().getMonthValue() == createdAt.getMonthValue()
                    && cabangDataModel.getCreatedAt().getYear() == createdAt.getYear()) {
                cabangData = cabangDataModel;
            }
        }
        return cabangData;
    }

    public int calculateTotalPayroll(CabangDataModel cabangDataModel) {
        Float totalPayroll = (float) 0;
        List<PresensiModel> presensiModelList = cabangDataModel.getListPresensi();
        for (PresensiModel presensi : presensiModelList) {
            totalPayroll += presensi.getNominal();
        }
        int total = Math.round(totalPayroll);
        return total;
    }

}