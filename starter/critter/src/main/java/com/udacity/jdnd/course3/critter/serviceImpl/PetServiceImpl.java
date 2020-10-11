package com.udacity.jdnd.course3.critter.serviceImpl;

import com.udacity.jdnd.course3.critter.enums.PetType;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class PetServiceImpl implements PetService {
    @Autowired
    PetRepository petRepository;
    @Autowired
    CustomerService customerService;

    public List<Pet> getAllPets() {

        List<Pet> allPets = petRepository.findAll();

        return allPets;
    }

    public Pet getPet(long petId) {
        Optional<Pet> p = petRepository.findById(petId);
        if (p.isPresent()) {
            return p.get();
        } else {
            return null;
        }
    }

    public boolean updatePet(Pet p, PetType petType, String petName, Customer owner, LocalDate birthDate, String notes) {
        try {
            p.setName(petName);
            p.setType(petType);
            p.setOwner(owner);
            p.setBirthDate(birthDate);
            p.setNotes(notes);
            savePet(p);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public void savePet(Pet p) {

        try {
            petRepository.save(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePet(Pet p) {
        try {
            petRepository.delete(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Pet> getPetsByOwnerId(long ownerId) {

        Customer c = customerService.getCustomer(ownerId);
//        System.out.println("bbbbbb"+c.toString());
//        List<Pet> pets = c.getPets();
        //        return  pets;

        List<Pet> pets =petRepository.findByOwner(c);
        return pets;


    }
}
