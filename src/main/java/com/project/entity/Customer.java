package com.project.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/*
 * Amit Kumar Modak 
 */
@Entity
@Table(name = "tbl_customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private Integer customer_id;
	
	@Column(name = "customer_name", nullable = false, length = 50)
	private String customer_name;            
	
	@Column(name = "customer_no", nullable = false, length = 15)
	private Long customer_no;
	
	@Column(name = "customer_email", nullable = false, length = 20)
	private String customer_email; 
	
	@Column(name = "customer_address", nullable = false, length = 100)
	private String customer_address;             
	
	@Column(name = "customer_password", nullable = false, length = 500)
	private String customer_password;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<Order> orders;

	public Customer() {
		
		// TODO Auto-generated constructor stub
	}

	public Customer(Integer customer_id, String customer_name, Long customer_no,
			String customer_email, String customer_address, String customer_password) {
		super();
		this.customer_id = customer_id;
		this.customer_name = customer_name;

		this.customer_no = customer_no;
		this.customer_email = customer_email;
		this.customer_address = customer_address;
		this.customer_password = customer_password;
	}

	public Customer(String customer_name, Long customer_no, String customer_email, String customer_address,
			String customer_password) {
		super();
		this.customer_name = customer_name;
		this.customer_no = customer_no;
		this.customer_email = customer_email;
		this.customer_address = customer_address;
		this.customer_password = customer_password;
	}

	public Integer getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}


	public Long getCustomer_no() {
		return customer_no;
	}

	public void setCustomer_no(Long customer_no) {
		this.customer_no = customer_no;
	}

	public String getCustomer_email() {
		return customer_email;
	}

	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}

	public String getCustomer_address() {
		return customer_address;
	}

	public void setCustomer_address(String customer_address) {
		this.customer_address = customer_address;
	}

	public String getCustomer_password() {
		return customer_password;
	}

	public void setCustomer_password(String customer_password) {
		this.customer_password = customer_password;
	}

	@Override
	public String toString() {
		return "Customer [Customer Id=" + customer_id + ", Customer Name=" + customer_name + ", Customer No=" + customer_no + ", Customer Email=" + customer_email
				+ ", Customer Address=" + customer_address + "]";
	}
	
}

