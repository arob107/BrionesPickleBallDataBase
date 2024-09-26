package intensives.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import intensives.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {

}
