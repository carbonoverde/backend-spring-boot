package com.carbonoverde.backend.repositories;

import com.carbonoverde.backend.entities.Compensation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompensationRepository extends JpaRepository<Compensation, Long>
{
    @EntityGraph(attributePaths = {"company", "greenArea", "emissionsCompensadas"})
    Optional<Compensation> findWithDetailsById(Long id);

    @EntityGraph(attributePaths = {"company"})
    List<Compensation> findAllByCompanyId(Long companyId);

    @Query("SELECT SUM(c.co2CompensatedTons) FROM Compensation c")
    Double sumCO2Compensated();

    @Query("SELECT SUM(c.investedValue) FROM Compensation c")
    Double sumInvestedValue();

    List<Compensation> findTop10ByOrderByCompensationDateDesc();

    @Query("SELECT SUM(c.areaCompensated_M2) FROM Compensation c")
    Double sumAreaCompensated();

    @Query("SELECT c FROM Compensation c WHERE c.company.address.city = :city")
    List<Compensation> findByCompanyCity(@Param("city") String city);

    @Query("SELECT c FROM Compensation c JOIN c.company comp WHERE comp.address.city = :city")
    List<Compensation> findByUserCity(@Param("city") String city);
}
