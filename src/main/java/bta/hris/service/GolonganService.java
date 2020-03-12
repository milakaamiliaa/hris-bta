package bta.hris.service;

import bta.hris.model.GolonganModel;

import java.util.List;

public interface GolonganService {
    GolonganModel addGolongan(GolonganModel golongan);
    GolonganModel editGolongan(GolonganModel existingGolongan);
    // void deleteGolongan(GolonganModel existingGolongan);
    List<GolonganModel> getAllGolongan();
}
