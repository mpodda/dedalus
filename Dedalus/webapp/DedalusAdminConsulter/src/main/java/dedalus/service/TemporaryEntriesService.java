package dedalus.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import dedalus.domain.IdentifiableEntity;
import dedalus.domain.structures.EligibleEntry;

/**
 * U: Entity Type (Master)
 * T: Entry Type (Detail)
 * E: Eligible Type
 * */
@Service
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TemporaryEntriesService<U extends IdentifiableEntity, T extends IdentifiableEntity, E extends EligibleEntry> {
	List<T> entriesList;
	List<E> eligibleEntriesList;
	List<T> deletedEntriesList;
	List<E> deletedEligibleEntriesList;
	U entity;
	
	public TemporaryEntriesService() {
		this.entriesList = new ArrayList<>();
		this.eligibleEntriesList = new ArrayList<>();
		this.deletedEntriesList = new ArrayList<>();
		this.deletedEligibleEntriesList = new ArrayList<>();
	}
	
	public void init(U entity) {
		this.entity = entity;
		
		this.entriesList = new ArrayList<>();
		this.eligibleEntriesList = new ArrayList<>();
		this.deletedEntriesList = new ArrayList<>();
		this.deletedEligibleEntriesList = new ArrayList<>();
	}
	
	public void clearEntries() {
		this.entriesList.clear();
	}
	
	public void clearEligibleEntries() {
		this.eligibleEntriesList.clear();
	}
	
	public void clearDeletedEntries() {
		this.deletedEntriesList.clear();
	}
	
	public void clearDeletedEligibleEntriesList() {
		this.deletedEligibleEntriesList.clear();
	}

	@SuppressWarnings("unchecked")
	public <E extends EligibleEntry> void addSelectedEligibleEntriesToEntries(List<E> selectedEligileEntries) {
		selectedEligileEntries.forEach (
			e -> {
				if (e.isSelected() && !entriesList.contains(e.getEntry())) {
					this.entriesList.add((T)e.getEntry());
				}
				
				if (deletedEntriesList.contains(e.getEntry())) {
					deletedEntriesList.remove(e.getEntry());
				}
				
				if (this.deletedEligibleEntriesList.contains(e)) {
					this.deletedEligibleEntriesList.remove(e);
				}
			}
		);
	}
	
	public void deleteEntry (T entry) {
		this.deletedEntriesList.add(entry);
		
		/*
		this.eligibleEntriesList.forEach (
			ee -> {
//				System.out.println(String.format("Eligible %s equals Entity = %s ", 
//						ee.getEntry().getId(),  
//						ee.getEntry().getId().equals(
//								entry.getId()) 
//						));
				
				//if (ee.getEntry().getId().equals(entry.getId())) {
				if (ee.getEntry().equals(entry)) {
					System.out.println("Remove!!!");
					
					this.eligibleEntriesList.remove(ee);
					this.deletedEligibleEntriesList.add(ee);
				}
			}
		);
		*/
		
		this.entriesList.remove(entry);
	}
	
	public List<T> getEntriesList() {
		return entriesList;
	}

	public void setEntriesList(List<T> entriesList) {
		this.entriesList = entriesList;
	}

	public List<E> getEligibleEntriesList() {
		
		/* Remove temporary entries from eligible entries */
		List<Long> idsToDelete = new ArrayList<>();
		
		eligibleEntriesList.forEach(
				eligibleEntry -> {
					if (entriesList.contains(eligibleEntry.getEntry())){
						idsToDelete.add(eligibleEntry.getEntry().getId());
					}
				}	
		);
		
		idsToDelete.forEach (
			entryId -> {
				eligibleEntriesList.remove(eligibleEntriesList.stream().filter(e->e.getEntry().getId().equals(entryId)).findFirst().get());
			}
		);

		
		/* Add temporary deleted entries to eligible entries */
		deletedEligibleEntriesList.forEach (
			dee -> {
				this.eligibleEntriesList.add(dee);
			}
		);
		
		return eligibleEntriesList;
	}

	public void setEligibleEntriesList(List<E> eligibleEntriesList) {
		this.eligibleEntriesList = eligibleEntriesList;
	}

	public List<T> getDeletedEntriesList() {
		return deletedEntriesList;
	}

	public void setDeletedEntriesList(List<T> deletedEntriesList) {
		this.deletedEntriesList = deletedEntriesList;
	}

	public List<E> getDeletedEligibleEntriesList() {
		return deletedEligibleEntriesList;
	}
	
	public void setDeletedEligibleEntriesList(List<E> deletedEligibleEntriesList) {
		this.deletedEligibleEntriesList = deletedEligibleEntriesList;
	}
	
	public U getEntity() {
		return entity;
	}
}
