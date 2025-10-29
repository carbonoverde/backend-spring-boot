package com.carbonoverde.backend.repositories;

import com.carbonoverde.backend.entities.GreenArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GreenAreaRepository extends JpaRepository<GreenArea, Long>
{
    @Query("SELECT ga FROM GreenArea ga WHERE ga.address.city = :city")
    List<GreenArea> findByCity(@Param("city") String city);

    @Query("SELECT ga FROM GreenArea ga WHERE ga.statusArea = 'EXISTENTE'")
    List<GreenArea> findActiveAreas();
}
