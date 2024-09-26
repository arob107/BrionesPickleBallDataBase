package intensives.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Intensive {

	// add @ID and @GeneratedValue annotations to primary key(s)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long intensiveId;

	private String status; // booked, scheduled, tentative
	private String startDate;
	private String duration;

	/*
	 * Foreign Key Add @EqualsAndHashCode.Exclude and @ToString.Exclude to all of
	 * the recursive relationship variables. This will prevent recursion from
	 * occurring when the .toString(), .equals(), or .hashCode() methods are called.
	 */
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "location_id")
	private Location location;


	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "intensive_customer", 
		joinColumns = {@JoinColumn(name = "intensive_id")}, 
		inverseJoinColumns = @JoinColumn(name = "customer_id"))
	private Set<Customer> customers = new HashSet<>();

}
