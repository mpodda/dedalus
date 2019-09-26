package dedalus.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="pupils")
public class Pupil extends IdentifiableEntity implements Serializable {
	private static final long serialVersionUID = 4988081988239752221L;

	@Column(name="name")
	private String name;
	
	@Column(name="email")
	private String email;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="mobile")
	private String mobile;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	//@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tcid")
	private TuitionCentre tuitionCentre;

	/*
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name="pupils_tokens",
			joinColumns=
			@JoinColumn(name="pupil_id", referencedColumnName="id"),
			inverseJoinColumns=
			@JoinColumn(name="token_id", referencedColumnName="id")
	)

	private  Set<Token> tokens;
	*/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public TuitionCentre getTuitionCentre() {
		return tuitionCentre;
	}

	public void setTuitionCentre(TuitionCentre tuitionCentre) {
		this.tuitionCentre = tuitionCentre;
	}
	
	public Pupil tuitionCentre(TuitionCentre tuitionCentre) {
		this.tuitionCentre = tuitionCentre;
		return this;
	}

//	public Set<Token> getTokens() {
//		return tokens;
//	}
//
//	public void setTokens(Set<Token> tokens) {
//		this.tokens = tokens;
//	}
}