package bta.hris.service;

import bta.hris.model.GolonganModel;

import java.util.List;

public interface GolonganService {
    GolonganModel getGolonganById(Long id);
    List<GolonganModel> getGolonganList();
}
