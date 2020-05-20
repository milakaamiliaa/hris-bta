package bta.hris.service;

import bta.hris.model.CabangModel;
import bta.hris.model.PresensiModel;
import bta.hris.model.UserModel;
import bta.hris.repository.CabangDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CabangServiceImpl implements CabangService{
    @Autowired
    private CabangDB cabangDb;

    @Autowired
    private CabangService cabangService;

    @Autowired
    private PresensiService presensiService;

    @Override
    public List<CabangModel> getCabangList(){
        return cabangDb.findAllByOrderByIdCabangDesc();
    }

    @Override
    public Optional<CabangModel> getCabangByIdCabang(Long idCabang){
        return cabangDb.findByIdCabang(idCabang);
    }

    @Override
    public void detailCabang(CabangModel cabang){
        if (cabang.getStafCabang() != null) {
            if (cabang.getStafCabang().isActive() == false) {
                cabang.setStafCabang(null);
            }
        }
    }

    @Override
    public CabangModel createCabang(CabangModel cabang){
        cabang.setActive(true);
        return cabangDb.save(cabang);
    }

    @Override
    public CabangModel updateCabang(CabangModel cabang){
        CabangModel newCabang = cabangDb.findByIdCabang(cabang.getIdCabang()).get();

        try{
            newCabang.setCabang(cabang.getCabang());
            newCabang.setAlamat(cabang.getAlamat());
            newCabang.setEmail(cabang.getEmail());
            newCabang.setNoTelp(cabang.getNoTelp());
            newCabang.setJumlahSiswa(cabang.getJumlahSiswa());
            newCabang.setStafCabang(cabang.getStafCabang());
            cabangDb.save(newCabang);

            return newCabang;
        }
        catch (NullPointerException nullException){
            return null;
        }
    }

    @Override
    public void deleteCabang(CabangModel cabang){
        CabangModel targetCabang = cabangDb.findByIdCabang(cabang.getIdCabang()).get();
        if (targetCabang.isActive()){
            targetCabang.setActive(false);
            targetCabang.setStafCabang(null);
        }
    }

    @Override
    public Optional<CabangModel> getCabangByStafCabang(UserModel stafCabang) {
        return cabangDb.findByStafCabang(stafCabang);
    }

}
