package com.promy.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.promy.student.entity.Student;
import com.promy.student.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentsController {

	
	@Autowired
	private StudentService studentService;

	
	
	// add mapping for "/list"

	@RequestMapping("/list")
	public String listStudents(Model theModel) {

		// get Students from db
		List<Student> theStudents = studentService.findAll();

		// add to the spring model
		theModel.addAttribute("Students", theStudents);

		return "list-Students";
	}
	
	

	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		// create model attribute to bind form data
		Student theStudent = new Student();

		theModel.addAttribute("Student", theStudent);

		return "Student-form";
	}

	
	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("studentId") int theId, Model theModel) {

		// get the Student from the service
		Student theStudent = studentService.findById(theId);

		// set Student as a model attribute to pre-populate the form
		theModel.addAttribute("Student", theStudent);

		// send over to our form
		return "Student-form";
	}

	
	
	@PostMapping("/save")
	public String saveStudent(@RequestParam("id") int id, @RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname, @RequestParam("course") String course, 
			@RequestParam("country") String country) {

		Student theStudent;
		
		if (id != 0) {
			theStudent = studentService.findById(id);
			theStudent.setFirstName(firstname);
			theStudent.setLastName(lastname);
			theStudent.setCourse(course);
			theStudent.setCountry(country);
		} 
		
		else
			theStudent = new Student(firstname, lastname, course, country);
		// save the Student
		studentService.save(theStudent);

		// use a redirect to prevent duplicate submissions
		return "redirect:/students/list";

	}
	
	

	@RequestMapping("/delete")
	public String delete(@RequestParam("studentId") int theId) {

		// delete the Student
		studentService.deleteById(theId);

		// redirect to /Students/list
		return "redirect:/students/list";

	}

	

	}

