package com.udacity.jdnd.course3.critter.serviceImpl;

import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {

        List<Employee> allEmployees = employeeRepository.findAll();

        return allEmployees;
    }

    public Employee getEmployee(long employeeId) {
        Optional<Employee> p = employeeRepository.findById(employeeId);
        if (p.isPresent()) {
            return p.get();
        } else {
            return null;
        }
    }

    public boolean updateEmployee(Employee e, String name, Set<EmployeeSkill> employeeSkills, List<DayOfWeek> daysAvailable){
        try {
            e.setName(name);
            e.setEmployeeSkills(employeeSkills);
            e.setDaysAvailable(daysAvailable);
            saveEmployee(e);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;

        }
    }

    public void saveEmployee(Employee e) {

        try {
            employeeRepository.save(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteEmployee(Employee e) {
        try {
            employeeRepository.delete(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Employee> getAvailableEmployees(Set<EmployeeSkill> employeeSkills, DayOfWeek dayOfWeek) {
        List<Employee> employees = employeeRepository.findAllByDaysAvailableContaining(dayOfWeek);
        List<Employee> availableEmployees = new ArrayList<>();
        for (Employee e:employees ) {
            if (e.getEmployeeSkills().containsAll(employeeSkills)){
                availableEmployees.add(e);
            }

        }
        return availableEmployees;
    }
}
