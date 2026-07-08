package com.SpringBoot.SpringBootCrudApplication.service;


import com.SpringBoot.SpringBootCrudApplication.dto.CreateStudentRequestDto;
import com.SpringBoot.SpringBootCrudApplication.dto.CreateStudentResponseDto;
import com.SpringBoot.SpringBootCrudApplication.dto.UpdateStudentRequestDto;
import com.SpringBoot.SpringBootCrudApplication.dto.UpdateStudentResponseDto;
import com.SpringBoot.SpringBootCrudApplication.entity.Student;
import com.SpringBoot.SpringBootCrudApplication.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**********************************************
 Developer Name: Vikas
 Created on: 30-06-2026 00:16
 Project Name: SpringBoot Crud Application
 /**********************************************/

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    //Create---
    public CreateStudentResponseDto createStudent(CreateStudentRequestDto createStudentRequestDto) {

        Student student = mapToEntity(createStudentRequestDto);    //createStudentRequestDto --to-- student entity

        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());

        Student studentResp = studentRepository.save(student);

        return mapToDto(studentResp);     //student entity --to-- studentResponseDto
//        studentReqD.setDeleted(false);   //setting false for setDeleted
//        Student studentResp = studentRepository.save(studentReq);
//        return studentResp;
    }

    //Mapping function (CreateStudentRequestDto --to--> Student entity)
    private Student mapToEntity(CreateStudentRequestDto createStudentRequestDto) {
        Student student = new Student();

        //later we will use builder pattern instead of using setter here
        student.setName(createStudentRequestDto.getName());
        student.setRollNo(createStudentRequestDto.getRollNo());
        student.setAge(createStudentRequestDto.getAge());
        student.setEmail(createStudentRequestDto.getEmail());
        student.setSubject(createStudentRequestDto.getSubject());

        student.setDeleted(false);

        return student;
    }

    //Mapping function (Student --to--> CreateStudentResponseDto)
    private CreateStudentResponseDto mapToDto(Student studentResp) {
        CreateStudentResponseDto createStudentResponseDto = new CreateStudentResponseDto();

        createStudentResponseDto.setId(studentResp.getId());
        createStudentResponseDto.setName(studentResp.getName());
        createStudentResponseDto.setRollNo(studentResp.getRollNo());
        createStudentResponseDto.setAge(studentResp.getAge());
        createStudentResponseDto.setEmail(studentResp.getEmail());
        createStudentResponseDto.setSubject(studentResp.getSubject());

        createStudentResponseDto.setCreatedAt(studentResp.getCreatedAt());
        createStudentResponseDto.setUpdatedAt(studentResp.getUpdatedAt());

        createStudentResponseDto.setMessage("Student Saved Successfully");


        return createStudentResponseDto;
    }

    //Get----
    public Student gettingStudent(Long studentId) {
        Optional<Student> getStudents = studentRepository.findByIdAndDeletedIsFalse(studentId);
        if (getStudents.isPresent()) {
            return getStudents.get();
        } else {
            return null;
        }
    }

    //update with put---
    public UpdateStudentResponseDto updateStudent(UpdateStudentRequestDto updateStudentReq, Long id) {
        Optional<Student> existingStudent = studentRepository.findByIdAndDeletedIsFalse(id);
        if (existingStudent.isPresent()) {

            Student studentToSave = existingStudent.get();

            studentToSave.setName(updateStudentReq.getName());
            studentToSave.setSubject(updateStudentReq.getSubject());
            studentToSave.setAge(updateStudentReq.getAge());
            studentToSave.setRollNo(updateStudentReq.getRollNo());
            studentToSave.setUpdatedAt(LocalDateTime.now());
            updateStudentReq.setDeleted(false);    //setting false so that any user can not make it true

            Student savedStudent = studentRepository.save(studentToSave);
            return mapToUpdateDto(savedStudent);
        } else return null;
    }

    private UpdateStudentResponseDto mapToUpdateDto(Student savedStudent) {

        UpdateStudentResponseDto responseDto = new UpdateStudentResponseDto();

        responseDto.setId(savedStudent.getId());
        responseDto.setName(savedStudent.getName());
        responseDto.setSubject(savedStudent.getSubject());
        responseDto.setAge(savedStudent.getAge());
        responseDto.setRollNo(savedStudent.getRollNo());
        responseDto.setUpdatedAt(LocalDateTime.now());
        responseDto.setMessage("Student Updated Successfully!");

        return responseDto;
    }


    //hard delete---
    public String deletingByID(Long id) {
        if (studentRepository.existsByIdAndDeletedIsFalse(id)) {
            studentRepository.deleteById(id);
            return "Deleted Successfully!";
        } else {
            return "Student of this id= " + id + ", is not found!";
        }
    }

    //patch update---
    public String patchStudent(Student studentReq, Long id) {
        //1.check exist or not
        if (studentRepository.existsByIdAndDeletedIsFalse(id)) {
            Student existingStudent = studentRepository.findById(id).get();
            if (studentReq.getAge() != 0) {
                existingStudent.setAge((Integer) studentReq.getAge());
            }
            if (studentReq.getEmail() != null) {
                existingStudent.setEmail(studentReq.getEmail());
            }
            if (studentReq.getName() != null) {
                existingStudent.setName(studentReq.getName());
            }
            if (studentReq.getRollNo() != 0) {
                existingStudent.setRollNo((Integer) studentReq.getRollNo());
            }
            if (studentReq.getSubject() != null) {
                existingStudent.setSubject(studentReq.getSubject());
            }
            //here also update will have always (deleted = false) until we don't do Soft delete
            if (studentReq.getDeleted() != true) {
                existingStudent.setDeleted(false);
            }
            studentRepository.save(existingStudent);
            return "Patch update done successfully";
        } else {
            return "Student does Not Exist of this id = " + id;
        }

    }

    //soft-delete
    public String deletingSoftly(Long id) {
        if (studentRepository.existsById(id)) {
            Student existingStudent = studentRepository.findById(id).get();
            existingStudent.setDeleted(true);
            studentRepository.save(existingStudent);
            return "Student with id: " + id + " deleted softly!";
        } else
            return null;
    }

    //read/get all active students---
    public List<Student> getAllActiveStudents() {
        return studentRepository.findAllByDeletedIsFalse();
    }
}
