package intensives.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import intensives.controller.model.IntensiveCustomer;
import intensives.controller.model.IntensiveData;
import intensives.controller.model.IntensiveLocation;
import intensives.service.IntensiveService;
import lombok.extern.slf4j.Slf4j;

/*
 *  The controller will allow Spring to map HTTP requests to specific methods. The URI for 
 *  every request that is mapped to the controller must start with "/intensives". 
 *  You can control the class-level mapping by specifying "/intensives" as the 
 *  value inside the @RequestMapping annotation.
 */

@RestController
@RequestMapping("/intensives")
@Slf4j
public class IntensivesController {

	@Autowired
	private IntensiveService intensiveService;

	// ---------------CREATE INTENSIVE, LOCATION, CUSTOMER---------------

	@PostMapping("/intensive")
	@ResponseStatus(code = HttpStatus.CREATED)
	public IntensiveData insertIntensive(@RequestBody IntensiveData intensiveData) {
		log.info("Creating intensive {}", intensiveData);
		return intensiveService.saveIntensive(intensiveData);
	}

	@PostMapping("/intensive/{intensiveId}/location")
	@ResponseStatus(code = HttpStatus.CREATED)
	public IntensiveLocation insertLocation(@PathVariable Long intensiveId,
			@RequestBody IntensiveLocation intensiveLocation) {
		log.info("Creating location {}", intensiveLocation);
		return intensiveService.saveLocation(intensiveId, intensiveLocation);
	}

	@PostMapping("/intensive/{intensiveId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public IntensiveCustomer insertCustomer(@PathVariable Long intensiveId,
			@RequestBody IntensiveCustomer intensiveCustomer) {
		log.info("Creating customer {} for intensive with ID={}", intensiveCustomer, intensiveId);
		return intensiveService.saveCustomer(intensiveId, intensiveCustomer);
	}

	// ---------------UPDATE INTENSIVE, LOCATION, CUSTOMER---------------
	/*
	 * When using PUT in ARC, be sure to include the updated info in the body, as
	 * these methods all contain
	 * 
	 * @RequestBody
	 */
	@PutMapping("/intensive/{intensiveId}")
	public IntensiveData updateIntensive(@PathVariable Long intensiveId, @RequestBody IntensiveData intensiveData) {
		intensiveData.setIntensiveId(intensiveId);
		log.info("Updating intensive {}", intensiveData);
		return intensiveService.saveIntensive(intensiveData);
	}

	@PutMapping("/intensive/{intensiveId}/location/{locationId}")
	public IntensiveLocation updateLocation(@PathVariable Long intensiveId, @PathVariable Long locationId,
			@RequestBody IntensiveLocation intensiveLocation) {
		intensiveLocation.setLocationId(locationId);
		log.info("Updating location {} for intensive with ID={}", intensiveLocation, intensiveId);
		return intensiveService.saveLocation(locationId, intensiveLocation);
	}

	@PutMapping("/intensive/{intensiveId}/customer/{customerId}")
	public IntensiveCustomer updateCustomer(@PathVariable Long intensiveId, @PathVariable Long customerId,
			@RequestBody IntensiveCustomer intensiveCustomer) {
		intensiveCustomer.setCustomerId(customerId);
		log.info("Updating customer {} for intensive with ID={}", intensiveCustomer, intensiveId);
		return intensiveService.saveCustomer(customerId, intensiveCustomer);
	}

	// ---------------RETRIEVE ALL/BY ID INTENSIVE, LOCATION,
	// CUSTOMER---------------
	@GetMapping("/intensive")
	public List<IntensiveData> retrieveAllIntensives() {
		log.info("Retrieve all intensives called");
		return intensiveService.retrieveAllIntensives();
	}

	@GetMapping("/intensive/{intensiveId}")
	public IntensiveData retrieveIntensiveById(@PathVariable Long intensiveId) {
		log.info("Retrieving intensive with ID={}", intensiveId);
		return intensiveService.retrieveIntensiveById(intensiveId);
	}

	@GetMapping("/location")
	public List<IntensiveLocation> retrieveAllLocations() {
		log.info("Retrieve all locations called");
		return intensiveService.retrieveAllLocations();
	}

	@GetMapping("/location/{locationId}")
	public IntensiveLocation retrieveLocationById(@PathVariable Long locationId) {
		log.info("Retrieving location with ID={}", locationId);
		return intensiveService.retrieveLocationById(locationId);
	}

	@GetMapping("/customer")
	public List<IntensiveCustomer> retrieveAllCustomers() {
		log.info("Retrieve all customers called");
		return intensiveService.retrieveAllCustomers();
	}

	@GetMapping("/customer/{customerId}")
	public IntensiveCustomer retrieveCustomerById(@PathVariable Long customerId) {
		log.info("Retrieving customer with ID={}", customerId);
		return intensiveService.retrieveCustomerById(customerId);
	}

	// ---------------DELETE BY ID INTENSIVE, LOCATION, CUSTOMER---------------
	@DeleteMapping("/intensive")
	public void deleteAllIntensives() {
		log.info("Attempting to delete all intensives");
		throw new UnsupportedOperationException("Deleting all intensives is not allowed.");
	}

	@DeleteMapping("/intensive/{intensiveId}")
	public Map<String, String> deleteIntensiveById(@PathVariable Long intensiveId) {
		log.info("Deleting intensive with ID={}", intensiveId);

		intensiveService.deleteIntensiveById(intensiveId);

		return Map.of("message", "Deletion of intensive with ID=" + intensiveId + " was successful.");
	}

	@DeleteMapping("/location")
	public void deleteAllLocations() {
		log.info("Attempting to delete all locations");
		throw new UnsupportedOperationException("Deleting all locations is not allowed.");
	}

	@DeleteMapping("/location/{locationId}")
	public Map<String, String> deleteLocationById(@PathVariable Long locationId) {
		log.info("Deleting location with ID={}", locationId);

		intensiveService.deleteLocationById(locationId);

		return Map.of("message", "Deletion of location with ID=" + locationId + " was successful.");
	}

	@DeleteMapping("/customer")
	public void deleteAllCustomers() {
		log.info("Attempting to delete all customers");
		throw new UnsupportedOperationException("Deleting all customers is not allowed.");
	}

	@DeleteMapping("/intensive/{intensiveId}/customer/{customerId}")
	public Map<String, String> deleteCustomerById(@PathVariable Long intensiveId, @PathVariable Long customerId) {
		log.info("Deleting customer with ID={} from intensive with ID={}", customerId, intensiveId);

		intensiveService.deleteCustomerById(intensiveId, customerId);

		return Map.of("message",
				"Deletion of customer with ID=" + customerId + " and intensive ID=" + intensiveId + " was successful.");
	}

}
