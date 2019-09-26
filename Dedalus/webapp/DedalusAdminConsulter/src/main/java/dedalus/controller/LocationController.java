package dedalus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dedalus.domain.Location;
import dedalus.service.api.LocationService;

@Controller
public class LocationController {
	private LocationService locationService;
	
	@Autowired
	public LocationController(LocationService locationService) {
		this.locationService = locationService;
	}
	
	@RequestMapping("/app/parameters/locations")
	public String Locations() {
		return "/app/parameters/locations";
	}
	
	@RequestMapping("/data/locations")
	public @ResponseBody List<Location> getLocations() {
		return this.locationService.findAll();
	}

	@RequestMapping(value="/data/location/create", method = RequestMethod.POST)
	public @ResponseBody Location createLocation() {
		return this.locationService.create();
	}
	
	@RequestMapping(value="/data/location/save", method = RequestMethod.POST)
	public @ResponseBody Location saveLocation(@RequestBody Location location) {
		return this.locationService.save(location);
	}
	
	@RequestMapping(value="/data/location/delete", method = RequestMethod.POST)
	public @ResponseBody Location deleteLocation(@RequestBody Location location) {
		this.locationService.delete(location);
		
		return location;
	}	
}