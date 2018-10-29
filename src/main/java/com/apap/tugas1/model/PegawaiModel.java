package com.apap.tugas1.model;
import java.util.Comparator;
import java.util.List;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;
import java.util.Comparator;
import java.util.ArrayList;
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
	@ManyToMany()
	@JoinTable(name = "jabatan_pegawai", joinColumns = { @JoinColumn(name = "id_pegawai") }, inverseJoinColumns = { @JoinColumn(name = "id_jabatan") })
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private List<JabatanModel> listJabatan = new ArrayList<JabatanModel>();

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
	

	public void setTanggalLahir(Date tanggalLahir) {
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
	
	public List<JabatanModel> getListJabatanSortByGaji() {
		if(listJabatan.size()>1) {
			Collections.sort(listJabatan, new SortByGajiPokok());
		}
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
	
	public int getGaji() {
		JabatanModel jabatan = listJabatan.get(listJabatan.size()-1);
		double tunjangan = (this.getInstansi().getProvinsi().getPresentase_tunjangan()/100);
		return (int) ( jabatan.getGaji_pokok() + (tunjangan * jabatan.getGaji_pokok()));
	}

	class SortByGajiPokok implements Comparator<JabatanModel>{
		public int compare (JabatanModel a, JabatanModel b) {
			return (int) (a.getGaji_pokok()-b.getGaji_pokok());
		}
	}
}


