package bta.hris.service;

import bta.hris.model.CabangModel;
import bta.hris.model.PresensiModel;
import bta.hris.model.UserModel;
import bta.hris.repository.PresensiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        return presensiDB.findAllByPegawai(user);
    }

    @Override
    public PresensiModel updatePresensi(PresensiModel presensi) {
        PresensiModel newPresensi = presensiDB.findById(presensi.getIdPresensi()).get();
            newPresensi.setCabang(presensi.getCabang());
            newPresensi.setSesiMengajar(presensi.getSesiMengajar());
            newPresensi.setSesiTambahan(presensi.getSesiTambahan());
            presensiDB.save(newPresensi);
            return newPresensi;
    }

    @Override
    public PresensiModel approvePresensi(PresensiModel presensi) {
        PresensiModel newPresensi = presensiDB.findById(presensi.getIdPresensi()).get();
        float pajak = presensi.getPegawai().getGolongan().getPajak() /100;
        float golongan = pajak * presensi.getPegawai().getGolongan().getRate();
        Long sesiTambahan;
        if(presensi.getSesiTambahan() == null){
            sesiTambahan = (long)0;
        }
        else{
            sesiTambahan = presensi.getSesiTambahan();

        }
        long jumlahSesi = presensi.getSesiMengajar() + sesiTambahan;
        float gaji = golongan * jumlahSesi;
            newPresensi.setCabang(presensi.getCabang());
            newPresensi.setSesiMengajar(presensi.getSesiMengajar());
            newPresensi.setSesiTambahan(presensi.getSesiTambahan());
            newPresensi.setStatus(presensi.getStatus());
            newPresensi.setKodeGaji(presensi.getKodeGaji());
            newPresensi.setUangKonsum(presensi.getUangKonsum());
            newPresensi.setNominal(gaji + presensi.getUangKonsum());
            presensiDB.save(newPresensi);
            return newPresensi;
    }

    @Override
    public List<PresensiModel> getAllPresensiByCabang(CabangModel cabang) {
        return presensiDB.findAllByCabang(cabang);
    }

    @Override
    public List<PresensiModel> getAllPresensiByKodeGaji(String kodeGaji) {
        return presensiDB.findByKodeGaji(kodeGaji);
    }
}
