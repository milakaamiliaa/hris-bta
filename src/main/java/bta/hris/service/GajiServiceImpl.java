package bta.hris.service;

import bta.hris.model.GajiModel;
import bta.hris.model.GolonganModel;
import bta.hris.model.PresensiModel;
import bta.hris.model.UserModel;
import bta.hris.repository.GajiDB;
import bta.hris.repository.GolonganDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GajiServiceImpl implements GajiService{

    @Autowired
    private GajiDB gajiDB;

    @Autowired
    private UserService userService;


    @Override
    public GajiModel addGaji(GajiModel gaji) {
        return gajiDB.save(gaji);
    }

    @Override
    public List<GajiModel> getAllGajiByNip(String nip) {
        UserModel user =  userService.getByNip(nip);
        return gajiDB.findAllByPegawai(user);
    }

    @Override
    public GajiModel updateGaji(GajiModel gaji) {
        return gajiDB.save(gaji);
    }

    @Override
    public List<GajiModel> getAllGaji() {
        return gajiDB.findAll();
    }

}
