package com.carbonoverde.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyEmission extends JpaRepository<MonthlyEmission, Long>
{

}
