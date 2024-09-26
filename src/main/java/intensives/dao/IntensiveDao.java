package intensives.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import intensives.entity.Intensive;

public interface IntensiveDao extends JpaRepository<Intensive, Long> {

}
