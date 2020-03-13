package bta.hris.service;

import bta.hris.model.CabangModel;
import bta.hris.repository.CabangDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CabangServiceImpl implements CabangService{
    @Autowired
    private CabangDb cabangDb;

    @Override
    public List<CabangModel> getCabangList(){
        return cabangDb.findAll();
    }

    @Override
    public Optional<CabangModel> getCabangByIdCabang(Long idCabang){
        return cabangDb.findByIdCabang(idCabang);
    }

    @Override
    public void createCabang(CabangModel cabang){
        cabangDb.save(cabang);
    }

    @Override
    public CabangModel updateCabang(CabangModel cabang){
        CabangModel newCabang = cabangDb.findByIdCabang(cabang.getIdCabang()).get();

        try{
            newCabang.setNama(cabang.getNama());
            newCabang.setAlamat(cabang.getAlamat());
            newCabang.setEmail(cabang.getEmail());
            newCabang.setNoTelp(cabang.getNoTelp());
            newCabang.setJumlahSiswa(cabang.getJumlahSiswa());
//            newCabang.setStafCabang(cabang.getStafCabang());
            cabangDb.save(newCabang);

            return newCabang;
        }
        catch (NullPointerException nullException){
            return null;
        }
    }
}
