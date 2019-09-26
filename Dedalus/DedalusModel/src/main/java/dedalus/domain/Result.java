package dedalus.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="results")
public class Result extends IdentifiableEntity {
	private static final long serialVersionUID = 7670502661511204351L;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "token_id")
	private Token token;

	@Column(name="results")
	@Lob
	private String results;

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

}
