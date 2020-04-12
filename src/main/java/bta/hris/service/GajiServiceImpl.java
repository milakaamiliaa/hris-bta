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
import java.util.Optional;

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
    public Optional<GajiModel> getGajiByIdGaji(Long idGaji) {
        return gajiDB.findById(idGaji);
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

    @Override
    public GajiModel approveGaji(GajiModel gaji) {
        GajiModel newGaji = gajiDB.findById(gaji.getIdGaji()).get();

        newGaji.setTotalGaji(gaji.getTotalGaji());
        newGaji.setTotalSesi(gaji.getTotalSesi());
        newGaji.setRateGaji(gaji.getRateGaji());
        newGaji.setPajakGaji(gaji.getPajakGaji());
        newGaji.setStatus(gaji.getStatus());

        gajiDB.save(newGaji);

        return newGaji;
    }

    @Override
    public GajiModel rejectGaji(GajiModel gaji) {
        GajiModel targetGaji = gajiDB.findById(gaji.getIdGaji()).get();
        try {
            targetGaji.setStatus("ditolak");
            gajiDB.save(targetGaji);
            return targetGaji;
        } catch (NullPointerException nullException) {
            return null;
        }
    }

    @Override
    public GajiModel paidGaji(GajiModel gaji) {
        GajiModel targetGaji = gajiDB.findById(gaji.getIdGaji()).get();
        try {
            targetGaji.setStatus("sudah dibayar");
            gajiDB.save(targetGaji);
            return targetGaji;
        } catch (NullPointerException nullException) {
            return null;
        }
    }


}
