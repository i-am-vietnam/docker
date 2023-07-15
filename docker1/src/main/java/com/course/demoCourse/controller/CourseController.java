package com.course.demoCourse.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.demoCourse.mapper.Mapper;
import com.course.demoCourse.models.Course;
import com.course.demoCourse.repositories.CourseRepository;

import redis.clients.jedis.Jedis;


@RestController
@RequestMapping("/course")
public class CourseController {
	@Autowired
	private CourseRepository courseRepo;
	private Jedis jedis = new Jedis();
	private Mapper<Course> mapCourse = new Mapper<Course>(Course.class);
	
	
	@PostMapping("/insert")
	public Course insert(@RequestBody Course course) {
		courseRepo.save(course);
		jedis.set(course.getId() + "", mapCourse.Object2Json(course));
		return course;
	}
	
	@GetMapping("/findById/{id}")
	public Course findById(@PathVariable Long id) {
		Course course = null;
		String json = jedis.get(id + "");
		if(json != null) {
			course = mapCourse.Json2Object(json);
		} else {
			course = courseRepo.findById(id).get();
			jedis.set(course.getId() + "", mapCourse.Object2Json(course));
		}
		return course;
	}
	
	@GetMapping("/getAll")
	public List<Course> getAll() {
		return courseRepo.findAll();
	}
	
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		Optional<Course> course = courseRepo.findById(id);
		if(course.isPresent()) {
			courseRepo.delete(course.get());
			return "success";
		}
		return "fail";
	}
	
	@PutMapping("/update/{id}")
	public Course update(@RequestBody Course courseNew,@PathVariable Long id) {
		Optional<Course> course = courseRepo.findById(id);
		if(course.isPresent()) {
			Course course1 =  course.get();
			courseNew.setId(id);
			course1 = courseNew;
			return courseRepo.save(course1);
		}
		return courseRepo.save(courseNew);
	}
	
	
}
