package bta.hris.service;

import bta.hris.model.CalonPengajarModel;
import bta.hris.model.HasilTesModel;
import bta.hris.repository.HasilTesDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HasilTesServiceImpl implements HasilTesService {

    @Autowired
    HasilTesDB hasilTesDB;

    @Override
    public HasilTesModel addHasilTes(HasilTesModel hasilTes) {
        hasilTesDB.save(hasilTes);

        return hasilTes;
    }

    @Override
    public HasilTesModel getHasilTesByCalonPengajar(CalonPengajarModel calonPengajar) {
        return hasilTesDB.findByCalonPengajar(calonPengajar);
    }

    @Override
    public HasilTesModel updateHasilTes(HasilTesModel hasilTes) {
        HasilTesModel target = hasilTesDB.findById(hasilTes.getIdHasil()).get();

        try{
            target.setNilai(hasilTes.getNilai());
            target.setSubmittedPaketSoal(hasilTes.getSubmittedPaketSoal());
            target.setStartedAt(hasilTes.getStartedAt());
            target.setFinishedAt(hasilTes.getFinishedAt());
            target.setCalonPengajar(hasilTes.getCalonPengajar());
            hasilTesDB.save(target);

            return target;
        }
        catch (NullPointerException nullException){
            return null;
        }
    }
}
