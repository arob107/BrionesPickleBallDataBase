package intensives.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import intensives.controller.model.IntensiveCustomer;
import intensives.controller.model.IntensiveData;
import intensives.controller.model.IntensiveLocation;
import intensives.dao.CustomerDao;
import intensives.dao.IntensiveDao;
import intensives.dao.LocationDao;
import intensives.entity.Customer;
import intensives.entity.Intensive;
import intensives.entity.Location;

@Service
public class IntensiveService {

	/* 
	 * Add the intensive, location, and customer dao private instance variables, annotated with
	 * @Autowired, so that Spring can inject the DAO objects into the variables
	 */
	@Autowired
	private IntensiveDao intensiveDao;
	
	@Autowired
	private LocationDao locationDao;
	
	@Autowired
	private CustomerDao customerDao;

	// ---------------INTENSIVE---------------
	/*
	 * This method will save an intensive. It first instantiates an intensiveId, then
	 * checks to see if the intensive already exists using findOrCreateIntensive
	 * method. This method will create an intensive if it doesn't exist. This method
	 * will also copy intensive fields using a method. Matching fields are copied
	 * from the IntensiveData object to the Intensive object. The customers and
	 * location fields are not copied in this method.
	 * 
	 * This section of code, including the associated methods, is repeated for 
	 * customer and location.
	 */
	public IntensiveData saveIntensive(IntensiveData intensiveData) {
		Long intensiveId = intensiveData.getIntensiveId();
		
		Intensive intensive = findOrCreateIntensive(intensiveId);
		
		copyIntensiveFields(intensive, intensiveData);
		
		Intensive dbIntensive = intensiveDao.save(intensive);
		return new IntensiveData(dbIntensive);
	}

	private void copyIntensiveFields(Intensive intensive, IntensiveData intensiveData) {
		intensive.setIntensiveId(intensiveData.getIntensiveId());
		intensive.setStatus(intensiveData.getStatus());
		intensive.setStartDate(intensiveData.getStartDate());
		intensive.setDuration(intensiveData.getDuration());
	}

	//  This method will create an intensive if it doesn't exist.
	private Intensive findOrCreateIntensive(Long intensiveId) {
		Intensive intensive;
		
		if (Objects.isNull(intensiveId)) {
			intensive = new Intensive();
		} else {
			intensive = findIntensiveById(intensiveId);
		}
		
		return intensive;
	}

	private Intensive findIntensiveById(Long intensiveId) {
		return intensiveDao.findById(intensiveId)
				.orElseThrow(() -> new NoSuchElementException("Intensive with ID=" + intensiveId + " does not exist."));
	}
	
	@Transactional(readOnly = true)
	public List<IntensiveData> retrieveAllIntensives() {
		List<Intensive> intensives = intensiveDao.findAll();
		List<IntensiveData> response = new LinkedList<>();
		
		for (Intensive intensive : intensives) {
			response.add(new IntensiveData(intensive));
		}
		
		return response;
	}
	
	@Transactional(readOnly = true)
	public IntensiveData retrieveIntensiveById(Long intensiveId) {
		Intensive intensive = findIntensiveById(intensiveId);
		return new IntensiveData(intensive);
	}
	
	@Transactional(readOnly = false)
	public void deleteIntensiveById(Long intensiveId) {
		Intensive intensive = findIntensiveById(intensiveId);
		intensiveDao.delete(intensive);
	}
	
	// ---------------LOCATION---------------
	public IntensiveLocation saveLocation(Long intensiveId, IntensiveLocation intensiveLocation) {
		Intensive intensive = findIntensiveById(intensiveId);
		Long locationId = intensiveLocation.getLocationId();
		Location location = findOrCreateLocation(locationId, intensiveId);
		
		copyLocationFields(location, intensiveLocation);
		
		location.getIntensives().add(intensive);
		intensive.setLocation(location);
		
		Location dbLocation = locationDao.save(location);
		return new IntensiveLocation(dbLocation);
	}

	private void copyLocationFields(Location location, IntensiveLocation intensiveLocation) {
		location.setLocationId(intensiveLocation.getLocationId());
		location.setName(intensiveLocation.getName());
		location.setVenueType(intensiveLocation.getVenueType());
		location.setStreetAddress(intensiveLocation.getStreetAddress());
		location.setCity(intensiveLocation.getCity());
		location.setState(intensiveLocation.getState());
		location.setZip(intensiveLocation.getZip());;
	}

	private Location findOrCreateLocation(Long locationId, Long intensiveId) {
		Location location;
		
		if (Objects.isNull(locationId)) {
			location = new Location();
		} else {
			location = findLocationById(intensiveId, locationId);
		}
		return location;
	}

	private Location findLocationById(Long intensiveId, Long locationId) {
		return locationDao.findById(locationId)
				.orElseThrow(() -> new NoSuchElementException("Location with ID=" + locationId + " does not exist."));
	}
	
	@Transactional(readOnly = true)
	public List<IntensiveLocation> retrieveAllLocations() {
		List<Location> locations = locationDao.findAll();
		List<IntensiveLocation> response = new LinkedList<>();
		
		for (Location location : locations) {
			response.add(new IntensiveLocation(location));
		}
		
		return response;
	}
	
	@Transactional(readOnly = true)
	public IntensiveLocation retrieveLocationById(Long locationId) {
		Location location = findLocationById(null, locationId);
		return new IntensiveLocation(location);
	}
	
	@Transactional(readOnly = false)
	public void deleteLocationById(Long locationId) {
		Location location = findLocationById(null, locationId);
		locationDao.delete(location);
	}
	
	// ---------------CUSTOMER---------------
	@Transactional(readOnly = false)
	public IntensiveCustomer saveCustomer(Long intensiveId, IntensiveCustomer intensiveCustomer) {
		Intensive intensive = findIntensiveById(intensiveId);
		Long customerId = intensiveCustomer.getCustomerId();
		Customer customer = findOrCreateCustomer(customerId, intensiveId);
		
		copyCustomerFields(customer, intensiveCustomer);
		
		// Add the Intensive object into the Set of Intensive objects in the Customer object
		customer.getIntensives().add(intensive);
		
		// Add the Customer object in to the Set of Customer objects in the Intensive object
		intensive.getCustomers().add(customer);
		
		Customer dbCustomer = customerDao.save(customer);
		return new IntensiveCustomer(dbCustomer);
	}

	private void copyCustomerFields(Customer customer, IntensiveCustomer intensiveCustomer) {
		customer.setCustomerId(intensiveCustomer.getCustomerId());
		customer.setFirstName(intensiveCustomer.getFirstName());
		customer.setLastName(intensiveCustomer.getLastName());
		customer.setDupr(intensiveCustomer.getDupr());
		customer.setPaymentStatus(intensiveCustomer.getPaymentStatus());
		customer.setFootageStatus(intensiveCustomer.getFootageStatus());
		customer.setIntensiveStatus(intensiveCustomer.getIntensiveStatus());
		customer.setPhone(intensiveCustomer.getPhone());
		customer.setEmail(intensiveCustomer.getEmail());
	}

	private Customer findOrCreateCustomer(Long customerId, Long intensiveId) {
		Customer customer;
		
		if (Objects.isNull(customerId)) {
			customer = new Customer();
		} else {
			customer = findCustomerById(intensiveId, customerId);
		}
		
		return customer;
	}

	private Customer findCustomerById(Long intensiveId, Long customerId) {
		return customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException("Customer with ID=" + customerId + " does not exist."));
	}

	@Transactional(readOnly = true)
	public List<IntensiveCustomer> retrieveAllCustomers() {
		List<Customer> customers = customerDao.findAll();
		List<IntensiveCustomer> response = new LinkedList<>();
		
		for (Customer customer : customers) {
			response.add(new IntensiveCustomer(customer));
		}
		
		return response;
	}

	@Transactional(readOnly = true)
	public IntensiveCustomer retrieveCustomerById(Long customerId) {
		Customer customer = findCustomerById(null, customerId);
		return new IntensiveCustomer(customer);
	}

	@Transactional(readOnly = false)
	public void deleteCustomerById(Long intensiveId, Long customerId) {
		//Customer customer = findCustomerById(intensiveId, customerId);
		customerDao.deleteById(customerId);
	}


}
