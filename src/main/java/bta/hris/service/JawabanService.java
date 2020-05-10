package bta.hris.service;


import bta.hris.model.JawabanModel;

import java.util.List;

public interface JawabanService {
    List<JawabanModel> getAllJawabanByIdSoal(Long idSoal);
    JawabanModel getJawabanById(Long idJawaban);
}
