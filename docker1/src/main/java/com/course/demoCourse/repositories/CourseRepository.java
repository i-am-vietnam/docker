package com.course.demoCourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.course.demoCourse.models.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{

}
