package com.apap.tugas1.service;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.ProvinsiModel;

public interface InstansiService {
	List<InstansiModel> allInstansi();
	
	//Optional<InstansiModel> getInstansiDetailById(Optional<Long> idInstansi);
	
	List<InstansiModel> getInstansiByProvinsi(ProvinsiModel provinsi);

	Optional<InstansiModel> getInstansiDetailById(Long id);
}
