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

        // Periode, formatnya String "MMYY", mirip sama kodeGaji (yang merupakan periode dari gaji tsb).
        LocalDate tanggal = LocalDate.now();
        String month = "";
        if (String.valueOf(tanggal.getMonthValue()).length() == 1) {
            month = "0" + tanggal.getMonthValue();
        }

        else {
            month = String.valueOf(tanggal.getMonthValue());
        }
        String year = String.valueOf(tanggal.getYear()).substring(2,4);
        cabangData.setPeriode(month + year);

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
    public Float calculateRasio(Float gaji, Long jumlahSiswa) {
        Float rasio = gaji/jumlahSiswa;
        return rasio;
    }
}