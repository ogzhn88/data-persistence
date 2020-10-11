package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
          try {
              Schedule s = modelMapper.map(scheduleDTO, Schedule.class);
              scheduleService.saveSchedule(s);
              return modelMapper.map(s, ScheduleDTO.class);
          }catch (Exception e){
              e.printStackTrace();
              throw new UnsupportedOperationException();
          }

    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        try {
            List<Schedule> mySchedules = scheduleService.getAllSchedules();
            List<ScheduleDTO> scheduleDTOS = Arrays.asList(modelMapper.map(mySchedules, ScheduleDTO[].class));
            return scheduleDTOS;
        }catch (Exception e){
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        try {
            List<Schedule> mySchedules = scheduleService.getAllPetSchedules(petId);
            List<ScheduleDTO> scheduleDTOS = Arrays.asList(modelMapper.map(mySchedules, ScheduleDTO[].class));
            return scheduleDTOS;
        }catch (Exception e){
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        try {
            List<Schedule> mySchedules = scheduleService.getAllEmployeeSchedules(employeeId);
            List<ScheduleDTO> scheduleDTOS = Arrays.asList(modelMapper.map(mySchedules, ScheduleDTO[].class));
            return scheduleDTOS;
        }catch (Exception e){
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        try {
            List<Schedule> mySchedules = scheduleService.getAllCustomerSchedules(customerId);
            List<ScheduleDTO> scheduleDTOS = Arrays.asList(modelMapper.map(mySchedules, ScheduleDTO[].class));
            return scheduleDTOS;
        }catch (Exception e){
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }
    }
}
