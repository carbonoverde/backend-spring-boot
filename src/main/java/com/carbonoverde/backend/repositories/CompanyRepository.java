package com.carbonoverde.backend.repositories;

import com.carbonoverde.backend.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>
{
    boolean existsByCnpj(String cnpj);

    @Query("SELECT c FROM Company c WHERE c.address.city = :city")
    List<Company> findByCity(@Param("city") String city);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
            "FROM Company c WHERE c.id = :companyId AND c.address.city = :city")
    boolean existsByIdAndCity(@Param("companyId") Long companyId, @Param("city") String city);
}
