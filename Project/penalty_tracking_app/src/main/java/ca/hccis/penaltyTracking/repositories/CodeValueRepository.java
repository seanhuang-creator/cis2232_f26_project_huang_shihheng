package ca.hccis.penaltyTracking.repositories;

import ca.hccis.penaltyTracking.jpa.entity.CodeValue;
import ca.hccis.penaltyTracking.jpa.entity.CodeValueId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeValueRepository extends CrudRepository<CodeValue, CodeValueId> {
}