package bta.hris.service;

import bta.hris.model.SoalModel;

import java.util.List;

public interface SoalService {
    SoalModel addSoal(SoalModel soal);
    List<SoalModel> getAllSoal();
}
