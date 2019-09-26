package dedalus.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="users")
public class User extends IdentifiableEntity {
	private static final long serialVersionUID = -5291098241890581612L;
	
    private String username;

    private String password;

    private String fullName;

    private String email;

    @Transient
    private String oldPassword;

    @Transient
    private String resetPassword1;

    @Transient
    private String resetPassword2;
    
    public User() {
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getResetPassword1() {
		return resetPassword1;
	}

	public void setResetPassword1(String resetPassword1) {
		this.resetPassword1 = resetPassword1;
	}

	public String getResetPassword2() {
		return resetPassword2;
	}

	public void setResetPassword2(String resetPassword2) {
		this.resetPassword2 = resetPassword2;
	}
}