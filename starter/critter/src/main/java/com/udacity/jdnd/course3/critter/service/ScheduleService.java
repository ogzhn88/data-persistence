package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public interface ScheduleService {

    public List<Schedule> getAllSchedules();

    public List<Schedule> getAllPetSchedules (long petId);

    public List<Schedule> getAllCustomerSchedules (long customerId);

    public Schedule getSchedule(long scheduleId);

    public List<Schedule> getAllEmployeeSchedules(long employeeId);

    public boolean updateSchedule(Schedule s, List<Employee> employees, List<Pet> pets, LocalDate date, Set<EmployeeSkill> activities);

    public void saveSchedule(Schedule s);

    public void deleteSchedule(Schedule s);
    
}
