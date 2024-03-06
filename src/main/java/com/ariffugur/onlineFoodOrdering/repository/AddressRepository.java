package com.ariffugur.onlineFoodOrdering.repository;

import com.ariffugur.onlineFoodOrdering.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
