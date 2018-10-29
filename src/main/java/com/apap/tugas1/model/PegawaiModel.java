package com.apap.tugas1.model;
import java.util.Comparator;
import java.util.List;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;
import java.util.Comparator;
import java.util.Collections;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="pegawai")
public class PegawaiModel implements Serializable{
	@Id
	@Size(max = 20)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(max = 255)
	@Column(name= "NIP", nullable= false, unique=true)
	private String nip;

	@NotNull
	@Size(max = 255)
	@Column(name= "nama", nullable= false)
	private String nama;

	@NotNull
	@Size(max = 255)
	@Column(name= "tempatLahir", nullable= false)
	private String tempatLahir;

	@NotNull
	@Column(name= "tanggalLahir", nullable= false)
	private Date tanggalLahir;

	@NotNull
	@Size(max = 255)
	@Column(name= "tahunMasuk", nullable= false)
	private String tahunMasuk;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_instansi", referencedColumnName="id", nullable= false)
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private InstansiModel instansi;
	
	@JsonIgnore
	@ManyToMany(mappedBy="pegawai", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	private List<JabatanModel> listJabatan;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getTempatLahir() {
		return tempatLahir;
	}

	public void setTempatLahir(String tempatLahir) {
		this.tempatLahir = tempatLahir;
	}

	public Date getTanggalLahir() {
		return tanggalLahir;
	}
	
	public double getGaji() {
		List<JabatanModel> listJabatan = this.getListJabatan();
		Collections.sort(listJabatan, compareGaji);
		double gaji = listJabatan.get(listJabatan.size()-1).getGaji_pokok();
		double tunjangan = (this.instansi.getProvinsi().getPresentase_tunjangan()/100);
		return gaji + (tunjangan*gaji);
	}

	public void setTanggalLahir(Date tanggaLahir) {
		this.tanggalLahir = tanggalLahir;
	}

	public String getTahunMasuk() {
		return tahunMasuk;
	}

	public void setTahunMasuk(String tahunMasuk) {
		this.tahunMasuk = tahunMasuk;
	}

	public InstansiModel getInstansi() {
		return instansi;
	}

	public void setInstansi(InstansiModel instansi) {
		this.instansi = instansi;
	}

	public List<JabatanModel> getListJabatan() {
		return listJabatan;
	}

	public void setListJabatan(List<JabatanModel> listJabatan) {
		this.listJabatan = listJabatan;
	}
	
	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}
	
	public static Comparator<JabatanModel> compareGaji = new Comparator<JabatanModel>() {
		  public int compare(JabatanModel o1, JabatanModel o2) {
		   Double gaji1 = o1.getGaji_pokok();
		   Double gaji2 = o2.getGaji_pokok();
		   
		   return gaji1.compareTo(gaji2);
		  }
		 };
}


