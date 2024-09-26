package intensives.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PreRemove;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Customer {

	// add @ID and @GeneratedValue annotations to primary key(s)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;

	private String firstName;
	private String lastName;
	private double dupr;
	private String paymentStatus; // none, partial, full, invoiced
	private String footageStatus; // none, received,
	private String intensiveStatus; // confirmed/accepted, tentative, wait list
	private String phone;
	private String email;

	/*
	 * Add @EqualsAndHashCode.Exclude and @ToString.Exclude to all of the recursive
	 * relationship variables. This will prevent recursion from occurring when the
	 * .toString(), .equals(), or .hashCode() methods are called.
	 */
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "customers", cascade = CascadeType.PERSIST)
	private Set<Intensive> intensives = new HashSet<>();
	
	/*
	 * This section of code allows us to delete a customer (many-to-many with intensive) without throwing a
	 * "Cannot delete or update a parent row: a foreign key constraint fails" error.
	 */
	@PreRemove
	private void removeCustomerFromIntensive() {
		for(Intensive intensive : intensives) {
			intensive.getCustomers().remove(this);
		}
	}
}
