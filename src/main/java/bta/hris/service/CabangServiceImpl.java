package bta.hris.service;

import bta.hris.model.CabangModel;
import bta.hris.repository.CabangDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CabangServiceImpl implements CabangService{
    @Autowired
    private CabangDb cabangDb;

    @Override
    public List<CabangModel> getCabangList(){
        return cabangDb.findAll();
    }
}
