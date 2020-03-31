package bta.hris.service;

import bta.hris.model.GolonganModel;
import bta.hris.repository.GolonganDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GajiServiceImpl implements GajiService{
    @Autowired
    private GolonganDB golonganDb;


    @Override
    public List<GolonganModel> getAllGolongan() {
        return golonganDb.findAll();
    }
}
