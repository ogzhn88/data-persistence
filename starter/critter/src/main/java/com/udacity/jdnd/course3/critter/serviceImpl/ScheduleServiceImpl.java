package com.udacity.jdnd.course3.critter.serviceImpl;

import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@Transactional
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    CustomerService customerService;

    public List<Schedule> getAllSchedules() {

        List<Schedule> allSchedules = scheduleRepository.findAll();

        return allSchedules;
    }

    public Schedule getSchedule(long scheduleId) {
        Optional<Schedule> s = scheduleRepository.findById(scheduleId);
        if (s.isPresent()) {
            return s.get();
        } else {
            return null;
        }
    }

    public boolean updateSchedule(Schedule s, List<Employee> employees, List<Pet> pets, LocalDate date, Set<EmployeeSkill> activities){
        try {
            s.setEmployees(employees);
            s.setPets(pets);
            s.setDate(date);
            s.setActivities(activities);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Schedule> getAllPetSchedules (long petId){

         return scheduleRepository.findAllByPetsId(petId);
    }

    public List<Schedule> getAllEmployeeSchedules (long employeeId){

        return scheduleRepository.findAllByEmployeesId(employeeId);
    }

    public List<Schedule> getAllCustomerSchedules (long customerId){
        Customer customer = customerService.getCustomer(customerId);
        List<Pet> pets = customer.getPets();
        ArrayList<Schedule> schedules = new ArrayList<>();
        for (Pet pet : pets) {
            schedules.addAll(getAllPetSchedules(pet.getId()));
        }
        return schedules;

    }

    public void saveSchedule(Schedule s) {

        try {
            scheduleRepository.save(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSchedule(Schedule s) {
        try {
            scheduleRepository.delete(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
