package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

//    @Query(nativeQuery = true,value = "select * from schedule s where s.id =    ")
//    public List<Schedule> getSchedulesForPet(long petId);

      List<Schedule> findAllByPetsId(long petId);
      List<Schedule> findAllByEmployeesId(long employeeId);

}
