package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.*;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
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
    @Autowired
    private PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        customerService.saveCustomer(customer);
        return convertCustomerToCustomerDTO(customer);
    }



    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){

        List<CustomerDTO> customerDtos = convertCustomersToCustomerDTOs(customerService.getAllCustomers());
        return customerDtos;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){

        return convertCustomerToCustomerDTO(petService.getPet(petId).getOwner());
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee e = modelMapper.map(employeeDTO,Employee.class);
        employeeService.saveEmployee(e);
        return modelMapper.map(e,EmployeeDTO.class);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {

        return modelMapper.map(employeeService.getEmployee(employeeId),EmployeeDTO.class);
    }

    @PutMapping("/employee/{employeeId}")
    public String setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        try {
            Employee e = employeeService.getEmployee(employeeId);
            List<DayOfWeek> daysAvailableList = new ArrayList<DayOfWeek>(daysAvailable);
            e.setDaysAvailable(daysAvailableList);
            employeeService.saveEmployee(e);
            return "Success";
        }catch (Exception e){
            return "An Error Has Been Occured";
        }
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        DayOfWeek day = employeeDTO.getDate().getDayOfWeek();
        List<Employee> availableEmployees = employeeService.getAvailableEmployees(employeeDTO.getSkills(),day);
        List<EmployeeDTO> availableEmployeeDtos = Arrays.asList(modelMapper.map(availableEmployees, EmployeeDTO[].class));

        return availableEmployeeDtos;
    }

    private static CustomerDTO convertCustomerToCustomerDTO(Customer customer)
    {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        if(customer.getPets() != null)
        {
            customerDTO.setPetIds(new ArrayList<>());
            for(Pet p : customer.getPets())
                customerDTO.getPetIds().add(p.getId());
        }

        return customerDTO;
    }

    private static List<CustomerDTO> convertCustomersToCustomerDTOs(List<Customer> customers)
    {
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        CustomerDTO customerDTO;
        for(Customer c : customers)
        {
            customerDTO = convertCustomerToCustomerDTO(c);

            customerDTOs.add(customerDTO);
        }

        return customerDTOs;
    }

}
