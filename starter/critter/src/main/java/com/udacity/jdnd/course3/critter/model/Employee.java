package com.udacity.jdnd.course3.critter.model;

import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> employeeSkills;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<DayOfWeek> daysAvailable;


    public Employee() {
    }

    public Employee(long id, String name, Set<EmployeeSkill> employeeSkills, List<DayOfWeek> daysAvailable) {
        this.id = id;
        this.name = name;
        this.employeeSkills = employeeSkills;
        this.daysAvailable = daysAvailable;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", employeeSkills=" + employeeSkills +
                ", daysAvailable=" + daysAvailable +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeSkill> getEmployeeSkills() {
        return employeeSkills;
    }

    public void setEmployeeSkills(Set<EmployeeSkill> employeeSkills) {
        this.employeeSkills = employeeSkills;
    }

    public List<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(List<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
}
