package bta.hris.service;

import bta.hris.model.PresensiModel;

import java.util.List;

public interface PresensiService {
    PresensiModel getPresensiById(Long id);
    PresensiModel addPresensi(PresensiModel presensi, String nip);
    List<PresensiModel> getAllPresensi();
    List<PresensiModel> getAllPresensiByNip(String nip);
    PresensiModel updatePresensi(PresensiModel presensi);
    PresensiModel getGajiByPeriode (PresensiModel presensi);
    // cek gaji udh dibuat atau blm
}
