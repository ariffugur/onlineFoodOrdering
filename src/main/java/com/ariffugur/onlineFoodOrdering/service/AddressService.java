package com.ariffugur.onlineFoodOrdering.service;

import com.ariffugur.onlineFoodOrdering.model.Address;
import com.ariffugur.onlineFoodOrdering.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public static Address createAddress(Address address) {
        return Address.builder()
                .city(address.getCity())
                .streetAddress(address.getStreetAddress())
                .stateProvince(address.getStateProvince())
                .postalCode(address.getPostalCode())
                .country(address.getCountry())
                .build();
    }
}
