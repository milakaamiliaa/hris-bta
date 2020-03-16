package bta.hris.service;

import bta.hris.model.PresensiModel;
import bta.hris.repository.PresensiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PresensiServiceImpl implements PresensiService {
    @Autowired
    private PresensiDB presensiDB;

    @Override
    public PresensiModel getPresensiById(Long id){
        return presensiDB.findById(id).get();
    }

    @Override
    public List<PresensiModel> getPresensiList(){
        return presensiDB.findAll();
    }

    @Override
    public void addPresensi(PresensiModel presensi){
        presensiDB.save(presensi);
    }
}
