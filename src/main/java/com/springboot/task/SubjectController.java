package com.springboot.task;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubjectController {

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private StudentRepository studentRepository;

	@GetMapping("/student/{studentId}/subjects")
	public Page<Subject> getAllSubjectsByStudentId(@PathVariable(value = "studentId") Long studentId,
			Pageable pageable) {
		return subjectRepository.findByStudentId(studentId, pageable);
	}

	@PostMapping("/students/{studentId}/subjects")
	public Subject createSubject(@PathVariable(value = "studentId") Long studentId,
			@Valid @RequestBody Subject subject) {
		return studentRepository.findById(studentId).map(student -> {
			subject.setStudent(student);
			return subjectRepository.save(subject);
		}).orElseThrow(() -> new ResourceNotFoundException("StudentId " + studentId + " Not found"));
	}

	@PutMapping("/students/{studentId}/subjects/{subjectId}")
	public Subject updateSubject(@PathVariable(value = "studentId") Long studentId,
			@PathVariable(value = "subjectId") Long subjectId, @Valid @RequestBody Subject subjectRequet) {
		if (!studentRepository.existsById(studentId)) {
			throw new ResourceNotFoundException("StudentId " + studentId + " not found");
		}
		return subjectRepository.findById(subjectId).map(subject -> {
			subject.setSubjectName(subjectRequet.getSubjectName());
			return subjectRepository.save(subject);
		}).orElseThrow(() -> new ResourceNotFoundException("SubjectId " + subjectId + " Not found"));
	}

	@DeleteMapping("/students/{studentId}/subjects/{subjectId}")
	public ResponseEntity<?> deleteSubject(@PathVariable(value = "subjectId") Long subjectId,
			@PathVariable(value = "studentId") Long studentId) {
		return subjectRepository.findByIdAndStudentId(subjectId, studentId).map(subject -> {
			subjectRepository.delete(subject);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException(
				"Subject not found with id " + subjectId + " and StudentId " + studentId));
	}
}


