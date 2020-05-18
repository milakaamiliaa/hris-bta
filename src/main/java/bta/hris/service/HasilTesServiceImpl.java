package bta.hris.service;

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
}
