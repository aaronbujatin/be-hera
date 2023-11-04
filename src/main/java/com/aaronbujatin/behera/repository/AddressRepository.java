package com.aaronbujatin.behera.repository;

import com.aaronbujatin.behera.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
