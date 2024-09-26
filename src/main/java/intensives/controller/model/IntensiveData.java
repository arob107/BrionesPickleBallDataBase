package intensives.controller.model;

import java.util.HashSet;
import java.util.Set;

import intensives.entity.Customer;
import intensives.entity.Intensive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IntensiveData {

	private Long intensiveId;
	private String status; // booked, scheduled, tentative
	private String startDate;
	private String duration;
	private Set<IntensiveCustomer> customers = new HashSet<>(); // owning side of the many-to-many
	
	/*
	 * Create a constructor that takes Intensive as a parameter. Set all matching fields 
	 * in the IntensiveData class to the data in the Intensive class. Also set the customers 
	 * fields to the IntensiveCustomer object. Note that customers is a set, so use a loop.
	 */
	public IntensiveData(Intensive intensive) {
		intensiveId = intensive.getIntensiveId();
		status = intensive.getStatus();
		startDate = intensive.getStartDate();
		duration = intensive.getDuration();
		
		for(Customer customer : intensive.getCustomers()) {
			customers.add(new IntensiveCustomer(customer));
		}
	}
}
