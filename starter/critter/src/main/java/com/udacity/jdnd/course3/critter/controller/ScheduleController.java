package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private PetService petService;
    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
          try {
              Schedule s = dto2Schedule(scheduleDTO);
              scheduleService.saveSchedule(s);
              return schedule2Dto(s);
          }catch (Exception e){
              e.printStackTrace();
              throw new UnsupportedOperationException();
          }

    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        try {
            List<Schedule> mySchedules = scheduleService.getAllSchedules();
            List<ScheduleDTO> scheduleDTOS = mySchedules.stream().map(this::schedule2Dto).collect(Collectors.toList()); ;
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
            List<ScheduleDTO> scheduleDTOS = mySchedules.stream().map(this::schedule2Dto).collect(Collectors.toList());
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
            List<ScheduleDTO> scheduleDTOS = mySchedules.stream().map(this::schedule2Dto).collect(Collectors.toList());
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
            List<ScheduleDTO> scheduleDTOS = mySchedules.stream().map(this::schedule2Dto).collect(Collectors.toList());
            return scheduleDTOS;
        }catch (Exception e){
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }
    }

    public ScheduleDTO schedule2Dto (Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
        scheduleDTO.setPetIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        scheduleDTO.setActivities(schedule.getActivities());
        scheduleDTO.setDate(schedule.getDate());
        return  scheduleDTO;
    }

    public Schedule dto2Schedule(ScheduleDTO scheduleDTO){
          Schedule schedule = new Schedule();
          schedule.setDate(scheduleDTO.getDate());
          schedule.setActivities(scheduleDTO.getActivities());
          schedule.setPets(scheduleDTO.getPetIds().stream().map(petService::getPet).collect(Collectors.toList()));
          schedule.setEmployees(scheduleDTO.getEmployeeIds().stream().map(employeeService::getEmployee).collect(Collectors.toList()));
          return schedule;
    }
}
