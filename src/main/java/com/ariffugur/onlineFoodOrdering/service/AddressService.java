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


    public Address createAddress(Address request) {
        Address address = new Address();
        address.setStreetAddress(request.getStreetAddress());
        address.setCity(request.getCity());
        address.setStateProvince(request.getStateProvince());
        address.setCountry(request.getCountry());
        address.setPostalCode(request.getPostalCode());
        return address;
    }

}
