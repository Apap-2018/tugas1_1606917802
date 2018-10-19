package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.PegawaiService;

@Controller
public class InstansiController {
	@Autowired
    private InstansiService instansiService;
	
	@Autowired
    private PegawaiService pegawaiService;

	 
	 @RequestMapping(value = "/pegawai/termuda-tertua", method = RequestMethod.GET)
	    private String viewmudatua(@RequestParam(value = "idInstansi") Long instansiId, Model model) {
		InstansiModel temp = instansiService.getInstansiDetailById(instansiId).get();
		List<PegawaiModel> listpegawaiorder = pegawaiService.getListOrderByTanggalLahir(temp);
		PegawaiModel pegawaimuda = listpegawaiorder.get(listpegawaiorder.size()-1);
		PegawaiModel pegawaitua = listpegawaiorder.get(0);
		List<JabatanModel> jabatanmuda = pegawaimuda.getListJabatan();
		List<JabatanModel> jabatantua = pegawaitua.getListJabatan();
		model.addAttribute("pegawaitua", pegawaitua);
		model.addAttribute("pegawaimuda", pegawaimuda);
		model.addAttribute("instansi", temp);
		model.addAttribute("jabatantua", jabatantua);
		model.addAttribute("jabatanmuda", jabatanmuda);
		model.addAttribute("gajitua", pegawaitua.getGaji());
		model.addAttribute("gajimuda", pegawaimuda.getGaji());
		return "view-tua-muda";
	 }
}
