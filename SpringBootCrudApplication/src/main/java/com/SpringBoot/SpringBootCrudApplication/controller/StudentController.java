package com.SpringBoot.SpringBootCrudApplication.controller;

import com.SpringBoot.SpringBootCrudApplication.entity.Student;
import com.SpringBoot.SpringBootCrudApplication.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**********************************************
 Developer Name: Vikas
 Created on: 30-06-2026 00:35
 Project Name: SpringBoot Student Crud Application
 /**********************************************/

@RestController
@RequestMapping("/students")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //create student-----------
    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(@RequestBody Student studentReq) {

        Student createdStudent = studentService.createStudent(studentReq);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdStudent);
    }

    //get/read student by their Ids-------
    @GetMapping("/{id}")
    public ResponseEntity<?> gettingStudent(@PathVariable Long id) {
        Student getStudent = studentService.gettingStudent(id);

        if (getStudent != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(getStudent);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Student does not Exist!!!");
        }
    }

    //update student--------
    @PutMapping("/{id}")
    public ResponseEntity<String> updatingStudent(@RequestBody Student student, @PathVariable("id") Long id) {
        String str = studentService.updateStudent(student, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(str);
    }

    //delete student--------
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletingByID(@PathVariable Long id) {
        String str = studentService.deletingByID(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(str);
    }

    //Patch update----------
    @PatchMapping("/{id}")
    public ResponseEntity<String> patchStudent(@RequestBody Student studentReq, @PathVariable Long id) {
        String str = studentService.patchStudent(studentReq, id);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(str);
    }

    //Using Patch for soft delete------
    @PatchMapping("/soft-delete/{id}")
    public ResponseEntity<String> deletingSoftly(@PathVariable Long id) {
        String str = studentService.deletingSoftly(id);
        if (str != null) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(str);
        } else return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Student with id: " + id + " not found!");
    }

    //Get All Active Students---
    @GetMapping("/active")
    public List<Student> getAllStudents() {
        List<Student> list = studentService.getAllActiveStudents();
        return list;
    }
}

//Component is too generic so for controller layer we use @RestController
//@Component --> @Controller --> @RestController
//@RequestBody uses Jackson Library which converts Json ---convert---> java class