package bta.hris.service;

import bta.hris.model.PresensiModel;

import java.util.List;

public interface PresensiService {
    PresensiModel addPresensi(PresensiModel presensi);
//
    List<PresensiModel> getAllPresensi();

    List<PresensiModel> getAllPresensiByNip(String nip);

    PresensiModel getPresensiById(Long idPresensi);

    PresensiModel updatePresensi(PresensiModel presensi);

}
