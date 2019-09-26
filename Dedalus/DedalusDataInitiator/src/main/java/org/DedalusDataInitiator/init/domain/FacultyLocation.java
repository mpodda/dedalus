package org.DedalusDataInitiator.init.domain;

public class FacultyLocation {
	private Faculty faculty;
	private Location location;
	
	public FacultyLocation(Faculty faculty, Location location) {
		this.faculty = faculty;
		this.location = location;
	}

	public FacultyLocation(int facultyId, String facultyDescription, int locationId, String locationDescription) {
		this.faculty = new Faculty(facultyId, facultyDescription);
		this.location = new Location(locationId, locationDescription);
	}
	
	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
