package com.carbonoverde.backend.repositories;

import com.carbonoverde.backend.entities.GreenArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GreenAreaRepository extends JpaRepository<GreenArea, Long>
{

}
