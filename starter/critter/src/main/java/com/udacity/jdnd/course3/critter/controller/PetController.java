package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PetService petService;
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        try {
            Pet pet = new Pet();
            BeanUtils.copyProperties(petDTO, pet);
            Customer owner = customerService.getCustomer(petDTO.getOwnerId());
            pet.setOwner(owner);
            if(owner.getPets() == null)
                owner.setPets(new ArrayList<>());
            owner.getPets().add(pet);
            petService.savePet(pet);
            return convertPetToPetDTO(pet);

        }catch (Exception e){
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }

    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        try {
            PetDTO myPetDto = convertPetToPetDTO(petService.getPet(petId));
            return myPetDto;

        }catch (Exception e){
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }
    }

    @GetMapping
    public List<PetDTO> getPets(){
        try {
            List<Pet> myPets = petService.getAllPets();

            List<PetDTO> petDTOList = Arrays.asList(modelMapper.map(myPets, PetDTO[].class));

            return petDTOList;

        }catch (Exception e){
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        try {
            List<Pet> myPets = petService.getPetsByOwnerId(ownerId);
            System.out.println(myPets+"AAAAAAAAAA");
            List<PetDTO> petDTOList = Arrays.asList(modelMapper.map(myPets, PetDTO[].class));

            return petDTOList;

        }catch (Exception e){
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }

    }

    private static PetDTO convertPetToPetDTO(Pet pet)
    {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getOwner().getId());
        return petDTO;
    }

    private static List<PetDTO> convertPetsToPetDTOs(List<Pet> pets)
    {
        List<PetDTO> petDTOs = new ArrayList<PetDTO>();
        PetDTO petDTO;
        for(Pet p : pets)
        {
            petDTO = convertPetToPetDTO(p);
            petDTOs.add(petDTO);
        }
        return petDTOs;
    }
}
