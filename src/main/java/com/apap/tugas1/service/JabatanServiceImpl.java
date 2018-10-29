package com.apap.tugas1.service;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
    @Autowired
    private JabatanDb jabatanDb;

	@Override
	public JabatanModel addJabatan(JabatanModel jabatan) {
		return jabatanDb.save(jabatan);
	}

	@Override
	public List<JabatanModel> allJabatan() {
		// TODO Auto-generated method stub
		return jabatanDb.findAll();
	}

	@Override
	public Optional<JabatanModel> getjabatanDetailById(Long id) {
		// TODO Auto-generated method stub
		return jabatanDb.findById(id);
	}

	@Override
	public void updateJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		jabatanDb.save(jabatan);
	}

	@Override
	public void deleteJabatan(long id) {
		// TODO Auto-generated method stub
		JabatanModel temp = jabatanDb.getOne(id);
		jabatanDb.delete(temp);
	}

}