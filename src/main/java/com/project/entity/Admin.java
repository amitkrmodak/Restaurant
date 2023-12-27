package com.project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * Amit Kumar Modak 
 */

@Entity
@Table(name = "tbl_Admin")
public class Admin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admin_id")
	private Integer admin_id;
	
	@Column(name = "admin_name", nullable = false, length = 100)
	private String admin_name;
	
	@Column(name = "admin_mno", nullable = false, length = 50)
	private Long admin_mno;
	
	@Column(name = "admin_email", nullable = false, length = 150)
	private String admin_email;              
	
	@Column(name = "admin_password", nullable = false, length = 2550)
	private String admin_password;

	public Admin() {
		
	}

	public Admin(Integer admin_id, String admin_name, Long admin_mno, String admin_email, String admin_password) {
		super();
		this.admin_id = admin_id;
		this.admin_name = admin_name;
		this.admin_mno = admin_mno;
		this.admin_email = admin_email;
		this.admin_password = admin_password;
	}

	public Admin(String admin_name, Long admin_mno, String admin_email, String admin_password) {
		super();
		this.admin_name = admin_name;
		this.admin_mno = admin_mno;
		this.admin_email = admin_email;
		this.admin_password = admin_password;
	}

	public Integer getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(Integer admin_id) {
		this.admin_id = admin_id;
	}

	public String getAdmin_name() {
		return admin_name;
	}

	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}

	public Long getAdmin_mno() {
		return admin_mno;
	}

	public void setAdmin_mno(Long admin_mno) {
		this.admin_mno = admin_mno;
	}

	public String getAdmin_email() {
		return admin_email;
	}

	public void setAdmin_email(String admin_email) {
		this.admin_email = admin_email;
	}

	public String getAdmin_password() {
		return admin_password;
	}

	public void setAdmin_password(String admin_password) {
		this.admin_password = admin_password;
	}

	@Override
	public String toString() {
		return "Admin [admin_id=" + admin_id + ", admin_name=" + admin_name + ", admin_mno=" + admin_mno
				+ ", admin_email=" + admin_email + ", admin_password=" + admin_password + "]";
	}
	
	
}


