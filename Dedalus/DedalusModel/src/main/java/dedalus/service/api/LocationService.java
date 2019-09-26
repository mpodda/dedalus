package dedalus.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dedalus.domain.Location;
import dedalus.repository.LocationRepository;
import dedalus.repository.ParameterRepository;

@Service
public class LocationService  extends ParameterService<Location> {
	private LocationRepository locationRepository;
	
	@Autowired
	public LocationService(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}
	
	@Override
	public ParameterRepository<Location> getParameterRepository() {
		return this.locationRepository;
	}
}