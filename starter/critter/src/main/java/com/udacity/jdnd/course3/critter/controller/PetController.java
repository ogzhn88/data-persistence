package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        try {
            Pet myPet = modelMapper.map(petDTO, Pet.class);
            petService.savePet(myPet);
            return petDTO;

        }catch (Exception e){
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }

    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        try {
            PetDTO myPetDto = modelMapper.map(petService.getPet(petId), PetDTO.class);
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

            List<PetDTO> petDTOList = Arrays.asList(modelMapper.map(myPets, PetDTO[].class));

            return petDTOList;

        }catch (Exception e){
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }

    }
}
