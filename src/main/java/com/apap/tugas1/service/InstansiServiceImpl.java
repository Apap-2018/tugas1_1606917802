package com.apap.tugas1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.InstansiDb;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService {
   
	@Autowired
    private InstansiDb instansiDb;

	@Override
	public List<InstansiModel> allInstansi() {
		// TODO Auto-generated method stub
		return instansiDb.findAll();
	}

	@Override
	public Optional<InstansiModel> getInstansiDetailById(Long id) {
		// TODO Auto-generated method stub
		return instansiDb.findById(id);
	}

	@Override
	public List<InstansiModel> getInstansiByProvinsi(ProvinsiModel provinsi) {
		// TODO Auto-generated method stub
		List<InstansiModel> selectedInstansi = new ArrayList<InstansiModel>();
		for(InstansiModel e : instansiDb.findAll()) {
			if(e.getProvinsi().equals(provinsi)) {
				selectedInstansi.add(e);
			}
		}
		return selectedInstansi;
	}

//	@Override
//	public Optional<InstansiModel> getInstansiDetailById(Optional<Long> idInstansi) {
//		// TODO Auto-generated method stub
//		return null;
//	}


}
