package bta.hris.service;

import bta.hris.model.CalonPengajarModel;
import bta.hris.model.HasilTesModel;

public interface HasilTesService {
    HasilTesModel addHasilTes(HasilTesModel hasilTes);

    HasilTesModel getHasilTesByCalonPengajar(CalonPengajarModel calonPengajar);

    HasilTesModel updateHasilTes(HasilTesModel hasilTes);
}
