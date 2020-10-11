package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.*;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.hibernate.dialect.CUBRIDDialect;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {

        customerService.saveCustomer(modelMapper.map(customerDTO,Customer.class));
        return customerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){

        List<Customer> customers = customerService.getAllCustomers();
        List<CustomerDTO> customerDtos = Arrays.asList(modelMapper.map(customers, CustomerDTO[].class));
        return customerDtos;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){

        return modelMapper.map(customerService.getCustomerByPet(petId), CustomerDTO.class);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.saveEmployee(modelMapper.map(employeeDTO,Employee.class));
        return employeeDTO;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {

        return modelMapper.map(employeeService.getEmployee(employeeId),EmployeeDTO.class);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee e = employeeService.getEmployee(employeeId);
        List<DayOfWeek> daysAvailableList = new ArrayList<DayOfWeek>(daysAvailable);
        e.setDaysAvailable(daysAvailableList);
        employeeService.saveEmployee(e);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        DayOfWeek day = employeeDTO.getDate().getDayOfWeek();
        List<Employee> availableEmployees = employeeService.getAvailableEmployees(employeeDTO.getSkills(),day);
        List<EmployeeDTO> availableEmployeeDtos = Arrays.asList(modelMapper.map(availableEmployees, EmployeeDTO[].class));

        return availableEmployeeDtos;
    }

    private static EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee)
    {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

}
