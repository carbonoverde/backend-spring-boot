package com.carbonoverde.backend.repositories;

import com.carbonoverde.backend.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>
{

}
