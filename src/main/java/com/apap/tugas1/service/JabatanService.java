package com.apap.tugas1.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;


public interface JabatanService {
	JabatanModel addJabatan(JabatanModel jabatan);
	
	List<JabatanModel> allJabatan();
	
	//Optional<JabatanModel> getjabatanDetailById(Optional<Long> idJabatan);
	
	void updateJabatan(JabatanModel jabatan);
	
	void deleteJabatan(long id);

	Optional<JabatanModel> getjabatanDetailById(Long id);
	

}
