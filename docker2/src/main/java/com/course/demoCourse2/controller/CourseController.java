package com.course.demoCourse2.controller;


import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.course.demoCourse2.dto.CourseResponse;



@RestController
@RequestMapping("/api")
public class CourseController {
   @GetMapping("/call")
   public List<CourseResponse> callAPI() {
		RestTemplate restTemplate = new RestTemplate();
		String fooResourceUrl
		  = "http://localhost:8080/course/getAll";
		ResponseEntity<List<CourseResponse> > response
		  = restTemplate.exchange(fooResourceUrl , HttpMethod.GET, null,
			        new ParameterizedTypeReference<List<CourseResponse>>() {});

		List<CourseResponse> courseResponses = response.getBody();
		return courseResponses;
		
//		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
   }
}
