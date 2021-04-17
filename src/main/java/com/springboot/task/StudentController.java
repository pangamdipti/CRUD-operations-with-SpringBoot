package com.springboot.task;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;

	@GetMapping("/students")
	public Page<Student> getAllStudents(Pageable pageable) {
		return studentRepository.findAll(pageable);
	}

	@PostMapping("/students")
	public Student createStudent(@Valid @RequestBody Student student) {
		return studentRepository.save(student);
	}

	@PutMapping("/students/{studentId}")
	public Student updateStudent(@PathVariable Long studentId, @Valid @RequestBody Student studentRequest) {
		return studentRepository.findById(studentId).map(student -> {
			student.setFirstName(studentRequest.getFirstName());
			student.setLastName(studentRequest.getLastName());
			return studentRepository.save(student);
		}).orElseThrow(() -> new ResourceNotFoundException("StudentId " + studentId + " not found"));
	}
	
	@DeleteMapping("/students/{studentId}")
	public ResponseEntity<?> deleteStudent(@PathVariable Long studentId){
		return studentRepository.findById(studentId).map(student -> {
			studentRepository.delete(student);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("StudentId "+studentId+" not found"));
	}

}
