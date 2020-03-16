package bta.hris.service;

import bta.hris.model.GolonganModel;
import bta.hris.repository.GolonganDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GolonganServiceImpl implements GolonganService {
    @Autowired
    private GolonganDB golonganDB;

    @Override
    public GolonganModel getGolonganById(Long id){
        return golonganDB.findById(id).get();
    }

    @Override
    public List<GolonganModel> getGolonganList(){
        return golonganDB.findAll();
    }
}
