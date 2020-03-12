package bta.hris.service;

import bta.hris.model.GolonganModel;
import bta.hris.repository.GolonganDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GolonganServiceImpl implements GolonganService {
    @Autowired
    private GolonganDB golonganDb;

    @Override
    public GolonganModel addGolongan(GolonganModel golongan) {
        golonganDb.save(golongan);
        return golongan;
    }

    @Override
    public GolonganModel editGolongan(GolonganModel newGolongan) {
        // Mengambil objek golongan yang ingin diubah.
        GolonganModel targetGolongan = golonganDb.findById(newGolongan.getIdGolongan()).get();

        try {
            targetGolongan.setNama(newGolongan.getNama());
            targetGolongan.setPajak(newGolongan.getPajak());
            targetGolongan.setRate(newGolongan.getRate());
            return targetGolongan;

        } catch (NullPointerException nullExeption) {
            return null;
        }
    }

    @Override
    public List<GolonganModel> getAllGolongan() {
        return golonganDb.findAll();
    }

}
