package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.enums.PetType;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public interface PetService {

    public List<Pet> getAllPets();

    public Pet getPet(long petId);

    public boolean updatePet(Pet p, PetType petType, String petName, Customer owner, LocalDate birthDate, String notes);

    public void savePet(Pet p);

    public void deletePet(Pet p);

    public List<Pet> getPetsByOwnerId(long ownerId);

}
