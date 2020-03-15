package bta.hris.service;

import bta.hris.model.CabangModel;
import bta.hris.repository.CabangDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CabangServiceImpl implements CabangService {

    @Autowired
    private CabangDB cabangDB;

    @Override
    public List<CabangModel> getAllCabang() {
        return cabangDB.findAll();
    }
}
