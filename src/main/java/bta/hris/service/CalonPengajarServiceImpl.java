package bta.hris.service;

import bta.hris.model.CalonPengajarModel;
import bta.hris.repository.CalonPengajarDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CalonPengajarServiceImpl implements CalonPengajarService {
    @Autowired
    CalonPengajarDB calonPengajarDB;

    @Override
    public CalonPengajarModel createCalonPengajar(CalonPengajarModel calonPengajar){
        return calonPengajarDB.save(calonPengajar);
    }

    @Override
    public Optional<CalonPengajarModel> findById(Long id){
        return calonPengajarDB.findById(id);
    }

    @Override
    public List<CalonPengajarModel>findAllCalonPengajar() {
        return calonPengajarDB.findAll();
    }
}
