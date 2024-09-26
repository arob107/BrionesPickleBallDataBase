package intensives.controller.model;

import intensives.entity.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IntensiveCustomer {
	
	private Long customerId;
	private String firstName;
	private String lastName;
	private double dupr;
	private String paymentStatus; // none, partial, full, invoiced
	private String footageStatus; // none, received,
	private String intensiveStatus; // confirmed, tentative
	private String phone;
	private String email;
	
	public IntensiveCustomer(Customer customer) {
		customerId = customer.getCustomerId();
		firstName = customer.getFirstName();
		lastName = customer.getLastName();
		dupr = customer.getDupr();
		paymentStatus = customer.getPaymentStatus(); 
		footageStatus = customer.getFootageStatus(); 
		intensiveStatus = customer.getIntensiveStatus(); 
		phone = customer.getPhone();
		email = customer.getEmail();
	}
}
