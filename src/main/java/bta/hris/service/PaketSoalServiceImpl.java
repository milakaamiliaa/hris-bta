package bta.hris.service;

import bta.hris.model.PaketSoalModel;
import bta.hris.repository.PaketSoalDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ArrayList;

@Service
@Transactional
public class PaketSoalServiceImpl implements PaketSoalService{
    @Autowired
    private PaketSoalDB paketSoalDB;

    @Override
    public List<PaketSoalModel> getAllPaketsoal() {
        return paketSoalDB.findAll();
    }

    @Override
    public PaketSoalModel addPaketSoal(PaketSoalModel paketSoal) {
        paketSoal.setActive(true);
        paketSoalDB.save(paketSoal);
        return paketSoal;
    }

    @Override
    public PaketSoalModel getPaketSoalByIdPaket(Long idPaket) {
        return paketSoalDB.findByIdPaket(idPaket).get();
    }

    @Override
    public PaketSoalModel updatePaketSoal(PaketSoalModel paketSoal) {
        PaketSoalModel newPaket = paketSoalDB.findByIdPaket(paketSoal.getIdPaket()).get();

        try{
            newPaket.setNama(paketSoal.getNama());
            newPaket.setMataPelajaran(paketSoal.getMataPelajaran());

            paketSoalDB.save(newPaket);
            return newPaket;
        }
        catch (NullPointerException nullException){
            return null;
        }
    }

    @Override
    public PaketSoalModel deletePaketSoal(PaketSoalModel paketSoal) {
        PaketSoalModel target = paketSoalDB.findByIdPaket(paketSoal.getIdPaket()).get();
        target.setActive(false);
        paketSoalDB.save(target);

        return target;
    }

    @Override
    public PaketSoalModel getRandomPaketSoalByMataPelajaran(String mataPelajaran) {
        List<PaketSoalModel> paketSoal = paketSoalDB.findByMataPelajaranContains(mataPelajaran);
        List<PaketSoalModel> activePaket = new ArrayList<PaketSoalModel>();
        Random rand = new Random();
        for(PaketSoalModel p : paketSoal){
            if(p.isActive()){
                activePaket.add(p);
            }
        }
        PaketSoalModel chosenPaketSoal = activePaket.get(rand.nextInt(activePaket.size()));
        return chosenPaketSoal;
    }

    @Override
    public PaketSoalModel getPaketSoalByMataPelajaran(String mataPelajaran){
        for (PaketSoalModel paket : paketSoalDB.findByMataPelajaranContains(mataPelajaran)){
            if (paket.isActive()){
                return paket;
            }
        }return null;
    }
}
