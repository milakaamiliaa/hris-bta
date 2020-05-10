package bta.hris.service;

import bta.hris.model.PaketSoalModel;
import bta.hris.model.SoalModel;
import bta.hris.repository.SoalDB;
import bta.hris.repository.PaketSoalDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SoalServiceImpl implements SoalService{
    @Autowired
    private SoalDB soalDB;

    @Autowired
    private PaketSoalDB paketSoalDB;

    @Override
    public List<SoalModel> getAllSoalByNamaPaketSoal(String nama){
        return soalDB.findAllByPaketSoal_Nama(nama);
    }

    @Override
    public SoalModel getSoalById(Long idSoal){
        return soalDB.findByIdSoal(idSoal);
    }

    @Override
    public List<SoalModel> getAllSoalByIdPaketSoal(Long idPaket){
        PaketSoalModel paket = paketSoalDB.findByIdPaket(idPaket);
        return soalDB.findByPaketSoal(paket);
    }
}
