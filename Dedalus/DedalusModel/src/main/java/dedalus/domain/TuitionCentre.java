package dedalus.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tuitionCentres")
public class TuitionCentre extends Parameter implements Serializable{
	private static final long serialVersionUID = 4528540739395468680L;

	@Column(name="address")
	private String address;
	
	@Column(name="userName", unique = true)
	private String userName;
	
	//@JsonIgnore
	@Column(name="password")
	private String password;
	
	@JsonIgnore
	private transient String passwordConfirmation;
	
	private Boolean active = Boolean.FALSE;
	
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy="tuitionCentre")
	private Set<Token> tokens;

	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy="tuitionCentre")
	private Set<Pupil> pupils;
	
	
	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}


	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}


	public Boolean getActive() {
		return active;
	}


	public void setActive(Boolean active) {
		this.active = active;
	}
	
}
