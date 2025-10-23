package com.carbonoverde.backend.repositories;

import com.carbonoverde.backend.entities.Compensation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompensationRepository extends JpaRepository<Compensation, Long>
{

}
