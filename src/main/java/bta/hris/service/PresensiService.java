package bta.hris.service;

import bta.hris.model.CabangModel;
import bta.hris.model.PresensiModel;
import bta.hris.model.UserModel;

import java.util.List;

public interface PresensiService {
    PresensiModel getPresensiById(Long id);
    PresensiModel addPresensi(PresensiModel presensi, String nip);
    List<PresensiModel> getAllPresensi();
    List<PresensiModel> getAllPresensiByNip(String nip);
    PresensiModel updatePresensi(PresensiModel presensi);
    PresensiModel approvePresensi(PresensiModel presensi);
    PresensiModel rejectPresensi(PresensiModel presensi);
    List<PresensiModel> getAllPresensiByCabang(CabangModel cabang);
    List<PresensiModel> getAllPresensiByKodeGaji(String kodeGaji, String nip);
    List<PresensiModel> getAllPresensiByCabangAndStatus(CabangModel cabang, String status);
    List<PresensiModel> getAllPresensiByCabangAndPegawaiAndStatus(CabangModel cabang, UserModel userModel, String status);

}
