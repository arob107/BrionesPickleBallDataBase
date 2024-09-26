package intensives.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Location {

	// add @ID and @GeneratedValue annotations to primary key(s)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long locationId;
	
	private String name;
	private String venueType;	
	private String streetAddress;
	private String city;
	private String state;
	private String zip;
	
	@OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Intensive> intensives = new HashSet<>();
}
