package bta.hris.service;

import bta.hris.model.CabangModel;
import bta.hris.model.PresensiModel;
import bta.hris.model.UserModel;
import bta.hris.repository.PresensiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.Null;
import java.util.List;

@Service
@Transactional
public class PresensiServiceImpl implements PresensiService {

    @Autowired
    private UserService userService;

    @Autowired
    private PresensiDB presensiDB;

    @Override
    public PresensiModel getPresensiById(Long id){
        return presensiDB.findById(id).get();
    }

    @Override
    public PresensiModel addPresensi(PresensiModel presensi, String nip) {
        UserModel user = userService.getByNip(nip);
        presensi.setPegawai(user);
        presensi.setStatus("pending");
        return presensiDB.save(presensi);
    }

    @Override
    public List<PresensiModel> getAllPresensi() {
        return presensiDB.findAll();
    }

    public List<PresensiModel> getAllPresensiByNip(String nip) {
        UserModel user =  userService.getByNip(nip);
        return presensiDB.findAllByPegawaiOrderByIdPresensiDesc(user);
    }

    @Override
    public PresensiModel updatePresensi(PresensiModel presensi) {
        PresensiModel newPresensi = presensiDB.findById(presensi.getIdPresensi()).get();
        newPresensi.setCabang(presensi.getCabang());
        newPresensi.setSesiMengajar(presensi.getSesiMengajar());
        newPresensi.setSesiTambahan(presensi.getSesiTambahan());
        newPresensi.setStatus("pending");
        presensiDB.save(newPresensi);
        return newPresensi;
    }

    @Override
    public PresensiModel approvePresensi(PresensiModel presensi) {
        PresensiModel newPresensi = presensiDB.findById(presensi.getIdPresensi()).get();
        float pajak = presensi.getPegawai().getGolongan().getPajak() /100;
        float golongan = presensi.getPegawai().getGolongan().getRate();
        Long sesiTambahan;
        if(presensi.getSesiTambahan() == null){
            sesiTambahan = (long)0;
        }
        else{
            sesiTambahan = presensi.getSesiTambahan();

        }
        long jumlahSesi = presensi.getSesiMengajar() + sesiTambahan;
        float gaji = (golongan * jumlahSesi) - (golongan * jumlahSesi * pajak);
        newPresensi.setCabang(presensi.getCabang());
        newPresensi.setSesiMengajar(presensi.getSesiMengajar());
        newPresensi.setSesiTambahan(presensi.getSesiTambahan());
        newPresensi.setStatus(presensi.getStatus());
        newPresensi.setKodeGaji(presensi.getKodeGaji());
        newPresensi.setUangKonsum(presensi.getUangKonsum());
        if (newPresensi.getUangKonsum() != null) {
            newPresensi.setNominal(gaji + presensi.getUangKonsum());
        }
        else {
            newPresensi.setNominal(gaji);
            System.out.println(gaji);
        }
        presensiDB.save(newPresensi);
        return newPresensi;
    }

    @Override
    public PresensiModel rejectPresensi(PresensiModel presensi) {
        PresensiModel targetPresensi = presensiDB.findById(presensi.getIdPresensi()).get();

        try {
            targetPresensi.setStatus("ditolak");
            presensiDB.save(targetPresensi);
            return targetPresensi;
        } catch (NullPointerException nullException) {
            return null;
        }
    }

    @Override
    public List<PresensiModel> getAllPresensiByCabang(CabangModel cabang) {
        return presensiDB.findAllByCabangOrderByIdPresensiDesc(cabang);
    }

    @Override
    public List<PresensiModel> getAllPresensiByKodeGaji(String kodeGaji, String nip) {
        UserModel user =  userService.getByNip(nip);
        return presensiDB.findByKodeGajiAndPegawai(kodeGaji, user);
    }
}