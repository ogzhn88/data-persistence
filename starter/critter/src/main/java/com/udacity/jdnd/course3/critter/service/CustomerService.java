package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface CustomerService {

    public List<Customer> getAllCustomers();

    public Customer getCustomer(long customerId);

    public Customer getCustomerByPet(long petId) ;

    public boolean updateCustomer(Customer c, String name, String phoneNumber, String notes, List<Pet> pets);

    public void saveCustomer(Customer c);

    public void deleteCustomer(Customer c);

}
