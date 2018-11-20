package com.codingsquid.spring4Tutorial.rest_controller;

import com.codingsquid.spring4Tutorial.dao.StudentRepository;
import com.codingsquid.spring4Tutorial.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControllerTest {

    private StudentRepository studentRepository;

    @Autowired
    public RestControllerTest(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @RequestMapping(value = "/restControllerTest", method = RequestMethod.GET)
    public Student restAPITest(@RequestParam("name") String name) {
        return studentRepository.findStudentByName(name);
    }
}