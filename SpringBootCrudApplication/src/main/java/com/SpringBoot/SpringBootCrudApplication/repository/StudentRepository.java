package com.SpringBoot.SpringBootCrudApplication.repository;

import com.SpringBoot.SpringBootCrudApplication.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**********************************************
 Developer Name: Vikas
 Created on: 30-06-2026 00:36
 Project Name: SpringBootCrudApplication ${
 /**********************************************/
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {   //JpaRepository<ENTITY_cClass_Name, Primary_Key_Type>

    Optional<Student> findByIdAndDeletedIsFalse(Long id);

    Boolean existsByIdAndDeletedIsFalse(Long id);

    List<Student> findAllByDeletedIsFalse();
}
