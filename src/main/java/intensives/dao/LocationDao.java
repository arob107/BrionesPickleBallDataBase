package intensives.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import intensives.entity.Location;

public interface LocationDao extends JpaRepository<Location, Long> {

}
