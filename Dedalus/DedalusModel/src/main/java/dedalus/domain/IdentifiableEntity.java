package dedalus.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public class IdentifiableEntity implements Serializable {
	private static final long serialVersionUID = -6498927616695139458L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	protected Long id;
	
    @Version
    protected long version = 1;

	public Long getId() {
		return id;
	}

	public long getVersion() {
		return version;
	}
		
	public void setId(Long id) {
		this.id = id;
	}
}