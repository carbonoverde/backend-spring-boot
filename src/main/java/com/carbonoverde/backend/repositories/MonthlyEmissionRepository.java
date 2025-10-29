package com.carbonoverde.backend.repositories;

import com.carbonoverde.backend.entities.MonthlyEmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyEmissionRepository extends JpaRepository<MonthlyEmission, Long>
{
    @Query("SELECT SUM(m.emissionsCo2) FROM MonthlyEmission m")
    Double sumCO2Emitted();

    @Query("SELECT me FROM MonthlyEmission me WHERE me.company.address.city = :city")
    List<MonthlyEmission> findByCompanyCity(@Param("city") String city);

    @Query("SELECT me FROM MonthlyEmission me WHERE me.company.id = :companyId")
    List<MonthlyEmission> findByCompanyId(@Param("companyId") Long companyId);

    @Query("SELECT CASE WHEN COUNT(me) > 0 THEN true ELSE false END " +
            "FROM MonthlyEmission me WHERE me.id = :emissionId AND me.company.address.city = :city")
    boolean existsByIdAndCompanyCity(@Param("emissionId") Long emissionId, @Param("city") String city);
}
