package dedalus.domain.structures;

import java.util.List;

import dedalus.domain.IdentifiableEntity;

public class IdentifiableEntityEligibleEntity <I extends IdentifiableEntity, E extends EligibleEntry>{
	private I entity;
	private List<E> EligibleEntries;

	public IdentifiableEntityEligibleEntity() {
		
	}

	public IdentifiableEntityEligibleEntity(I entity, List<E> eligibleEntries) {
		this.entity = entity;
		EligibleEntries = eligibleEntries;
	}


	public I getEntity() {
		return entity;
	}

	public void setEntity(I entity) {
		this.entity = entity;
	}

	public List<E> getEligibleEntries() {
		return EligibleEntries;
	}

	public void setEligibleEntries(List<E> eligibleEntries) {
		EligibleEntries = eligibleEntries;
	}
	
	
	
}
