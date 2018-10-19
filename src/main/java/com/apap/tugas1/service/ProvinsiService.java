package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.ProvinsiModel;


public interface ProvinsiService {
	Optional<ProvinsiModel> getProvinsiDetailById(Long id);
	List<ProvinsiModel> allProvinsi();
}
