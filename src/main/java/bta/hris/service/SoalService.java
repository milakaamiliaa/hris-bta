package bta.hris.service;

import bta.hris.model.SoalModel;

import java.util.List;

public interface SoalService {
    List<SoalModel> getAllSoalByNamaPaketSoal(String nama);
}
