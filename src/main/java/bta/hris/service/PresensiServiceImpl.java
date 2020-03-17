package bta.hris.service;

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

    @Override
    public List<PresensiModel> getAllPresensiByNip(String nip) {
        UserModel user =  userService.getByNip(nip);
        return presensiDB.findAllByPegawai(user);
    }

    @Override
    public PresensiModel getPresensiById(Long idPresensi) {
        return presensiDB.findById(idPresensi).get();
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
}
