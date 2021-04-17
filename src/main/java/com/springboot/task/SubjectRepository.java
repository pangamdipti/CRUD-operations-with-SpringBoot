package com.springboot.task;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long>{
	Page<Subject> findByStudentId(Long studentId, Pageable pageable);
	Optional<Subject> findByIdAndStudentId(Long id, Long studentId);
	

}
