package intensives.controller.model;

import java.util.HashSet;
import java.util.Set;

import intensives.entity.Intensive;
import intensives.entity.Location;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IntensiveLocation {

	private Long locationId;
	private String name;
	private String venueType;
	private String streetAddress;
	private String city;
	private String state;
	private String zip;
	private Set<IntensiveData> intensives = new HashSet<>(); // owning side of the one-to-many
	
	/*
	 * Create a constructor that takes Location as a parameter. Set all matching fields 
	 * in the IntensiveLocation class to the data in the Location class. Also set the intensives 
	 * fields to the IntensiveData object. Note that intensives is a set, so use a loop.
	 */
	public IntensiveLocation(Location location) {
		locationId = location.getLocationId();
		name = location.getName();
		venueType = location.getVenueType();
		streetAddress = location.getStreetAddress();
		city = location.getCity();
		state = location.getState();
		zip = location.getZip();
		
		for(Intensive intensive : location.getIntensives()) {
			intensives.add(new IntensiveData(intensive));
		}
	}
}
