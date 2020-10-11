package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.model.Employee;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Service
public interface EmployeeService {

    public List<Employee> getAllEmployees();

    public Employee getEmployee(long employeeId);

    public boolean updateEmployee(Employee e, String name, Set<EmployeeSkill> employeeSkills, List<DayOfWeek> daysAvailable);

    public void saveEmployee(Employee e);

    public void deleteEmployee(Employee e);

    public List<Employee> getAvailableEmployees (Set<EmployeeSkill> employeeSkills, DayOfWeek dayOfWeek);

}
