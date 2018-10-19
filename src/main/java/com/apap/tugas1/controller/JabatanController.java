package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;

@Controller
public class JabatanController {
	@Autowired
    private JabatanService jabatanService;

	@Autowired
    private PegawaiService pegawaiService;

	
	 @RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	    private String add(Model model) {
	        model.addAttribute("jabatan", new JabatanModel());
	        return "add-jabatan";
	    }
	 
	 @RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	    private String addJabatanSubmit(@ModelAttribute JabatanModel jabatan) {
	        jabatanService.addJabatan(jabatan);
	        return "add-jabatan-success";
	    }
	 
	 @RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
	    private String view(@RequestParam(value = "idJabatan") Long jabatanId, Model model) {
		JabatanModel temp = jabatanService.getjabatanDetailById(jabatanId).get();
		model.addAttribute("jabatan", temp);
		model.addAttribute("jumlah", temp.getPegawai().size());
		return "view-jabatan";
	 }
	 
	 @RequestMapping(value = "/jabatan/viewall", method = RequestMethod.GET)
	    private String viewall(Model model) {
		List<JabatanModel> temp = jabatanService.allJabatan();
		model.addAttribute("jabatan", temp);
		return "viewall-jabatan";
	 }
	 
	 @RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
	    private String update(@RequestParam(value = "idJabatan") Long jabatanId, Model model) {
	        JabatanModel temp = jabatanService.getjabatanDetailById(jabatanId).get();
	        JabatanModel baru = new JabatanModel();
	        baru.setId(temp.getId());
	        model.addAttribute("jabatanlama", temp);
	        model.addAttribute("jabatanbaru", baru);
	        return "update-jabatan";
	    }
	 
	 @RequestMapping(value = "/jabatan/update", method = RequestMethod.POST)
	    private String updateSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		 	JabatanModel temp = jabatanService.getjabatanDetailById(jabatan.getId()).get();
		 	temp.setNama(jabatan.getNama());
			temp.setDeskripsi(jabatan.getDeskripsi());
			temp.setGaji_pokok(jabatan.getGaji_pokok());
	        jabatanService.updateJabatan(temp);
	        return "update-jabatan-success";
	    }
	 
	 @RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST)
	    private String delete(@ModelAttribute JabatanModel jabatan, Model model) {
		 JabatanModel temp = jabatanService.getjabatanDetailById(jabatan.getId()).get();
		 	if(temp.getPegawai().isEmpty()) {
			 jabatanService.deleteJabatan(temp.getId());
			 return "delete-jabatan-success";
		 }
		 return "delete-jabatan-error";
	    }
	
}
