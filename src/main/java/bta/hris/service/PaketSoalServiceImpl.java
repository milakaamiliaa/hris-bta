package bta.hris.service;

import bta.hris.model.PaketSoalModel;
import bta.hris.repository.PaketSoalDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PaketSoalServiceImpl implements PaketSoalService{
    @Autowired
    private PaketSoalDB paketSoalDB;

    @Override
    public PaketSoalModel getPaketById(Long idPaket){
        return paketSoalDB.findByIdPaket(idPaket);
    }

}
