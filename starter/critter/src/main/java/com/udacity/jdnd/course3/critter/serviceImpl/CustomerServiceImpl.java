package com.udacity.jdnd.course3.critter.serviceImpl;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PetService petService;

    public List<Customer> getAllCustomers() {

        List<Customer> allCustomers = customerRepository.findAll();

        return allCustomers;
    }

    public Customer getCustomer(long customerId) {
        Optional<Customer> c = customerRepository.findById(customerId);
        if (c.isPresent()) {
            return c.get();
        } else {
            return null;
        }
    }

    public Customer getCustomerByPet(long petId) {
        Pet pet =  petService.getPet(petId);
        Customer customer = getCustomer(pet.getOwner().getId());
        return customer;
    }

    public boolean updateCustomer(Customer c, String name, String phoneNumber, String notes, List<Pet> pets){
        try {
            c.setName(name);
            c.setPhoneNumber(phoneNumber);
            c.setNotes(notes);
            c.setPets(pets);
            saveCustomer(c);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public void saveCustomer(Customer c) {

        try {
            customerRepository.save(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(Customer c) {
        try {
            customerRepository.delete(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
