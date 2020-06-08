package bta.hris.service;

import bta.hris.model.GolonganModel;

import java.util.List;
import java.util.Optional;

public interface GolonganService {
    GolonganModel addGolongan(GolonganModel golongan);

    GolonganModel editGolongan(GolonganModel existingGolongan);

    GolonganModel deleteGolongan(GolonganModel golongan);

    List<GolonganModel> getAllGolongan();

    Optional<GolonganModel> getGolonganByIdGolongan(Long idGolongan);
}
