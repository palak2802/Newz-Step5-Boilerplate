package com.stackroute.newz.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.newz.model.NewsSource;
import com.stackroute.newz.service.NewsSourceService;
import com.stackroute.newz.util.exception.NewsSourceNotFoundException;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
@RequestMapping("/api/v1")
public class NewsSourceController {

	/*
	 * Autowiring should be implemented for the NewsService. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword
	 */
	private NewsSourceService newsSourceService;
	
	@Autowired
	public NewsSourceController(NewsSourceService newsSourceService) {
		this.newsSourceService = newsSourceService;
	}
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/*
	 * Define a handler method which will create a specific newssource by reading the
	 * Serialized object from request body and save the newssource details in the
	 * database.This handler method should return any one of the status messages
	 * basis on different situations: 
	 * 1. 201(CREATED) - If the newssource created successfully. 
	 * 2. 409(CONFLICT) - If the newssourceId conflicts with any existing user.
	 * 
	 * This handler method should map to the URL "/api/v1/newssource" using HTTP POST method
	 */
	@PostMapping("/newssource")
	public ResponseEntity<NewsSource> createNewsSource(@RequestBody NewsSource newssource){
		
		try {
			boolean isNewsSourceExists = newsSourceService.addNewsSource(newssource);
				if(isNewsSourceExists == true) {
				logger.info("In controller - {}", "News Source created: " +newssource);
				return new ResponseEntity<NewsSource>(newssource, HttpStatus.CREATED);
			}
		} 
		catch(Exception e) {
			return new ResponseEntity<NewsSource>(HttpStatus.CONFLICT);
		}
		logger.info("In controller - {}", "News Source ID "+ newssource.getNewsSourceId() + " already exists.");
		return new ResponseEntity<NewsSource>(HttpStatus.CONFLICT);
	}

	/*
	 * Define a handler method which will delete a newssource from a database.
	 * This handler method should return any one of the status messages basis 
	 * on different situations: 
	 * 1. 200(OK) - If the newssource deleted successfully from database. 
	 * 2. 404(NOT FOUND) - If the newssource with specified newsId is not found.
	 *
	 * This handler method should map to the URL "/api/v1/newssource/{newssourceId}" 
	 * using HTTP Delete method where "userId" should be replaced by a valid userId 
	 * without {} and "newssourceId" should be replaced by a valid newsId 
	 * without {}.
	 * 
	 */
	@DeleteMapping("/newssource/{newssourceId}")
	public ResponseEntity<NewsSource> deleteNewsSource(@PathVariable("newssourceId") int newssourceId){
		
		try {
			boolean isnewsSourceDeleted = newsSourceService.deleteNewsSource(newssourceId);
			if(isnewsSourceDeleted == true) {
				logger.info("In controller - {}", "News Source with ID: "+newssourceId+" deleted.");
				return new ResponseEntity<NewsSource>(HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<NewsSource>(HttpStatus.NOT_FOUND);
		}
		logger.info("In controller - {}", "News Source ID "+ newssourceId + " not found.");
		return new ResponseEntity<NewsSource>(HttpStatus.NOT_FOUND);
	}
	
	/*
	 * Define a handler method which will update a specific newssource by reading the
	 * Serialized object from request body and save the updated newssource details in a
	 * database. This handler method should return any one of the status messages
	 * basis on different situations: 
	 * 1. 200(OK) - If the newssource updated successfully.
	 * 2. 404(NOT FOUND) - If the newssource with specified newssourceId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/newssource/{newssourceId}" using 
	 * HTTP PUT method where "newssourceId" should be replaced by a valid newssourceId
	 * without {}.
	 * 
	 */
	@PutMapping("/newssource/{newssourceId}")
	public ResponseEntity<NewsSource> updateNewsSource(@PathVariable("newssourceId") int newssourceId, @RequestBody NewsSource newssource){
		
		try {
			NewsSource newsSourceUpdated = newsSourceService.updateNewsSource(newssource, newssourceId);
			if(newsSourceUpdated != null) {
				logger.info("In controller - {}", "News Source Updated: " +newsSourceUpdated);
				return new ResponseEntity<NewsSource>(newsSourceUpdated, HttpStatus.OK);
			}
		} catch (NewsSourceNotFoundException e) {
			return new ResponseEntity<NewsSource>(HttpStatus.NOT_FOUND);
		}
		logger.info("In controller - {}", "User ID "+ newssource.getNewsSourceCreatedBy() + " not Found.");
		return new ResponseEntity<NewsSource>(HttpStatus.NOT_FOUND);
	}
	
	/*
	 * Define a handler method which will get us the specific newssource by a userId.
	 * This handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the newssource found successfully. 
	 * 2. 404(NOT FOUND) - If the newssource with specified newsId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/newssource/{userId}/{newssourceId}" 
	 * using HTTP GET method where "userId" should be replaced by a valid userId 
	 * without {} and "newssourceId" should be replaced by a valid newsId without {}.
	 * 
	 */
	@GetMapping("/newssource/{userId}/{newssourceId}")
	public ResponseEntity<NewsSource> getNewsSource(@PathVariable("newssourceId") int newssourceId, @PathVariable("userId") String userId){
		
		NewsSource newsSourceById;
		try {
			newsSourceById = newsSourceService.getNewsSourceById(userId, newssourceId);
			if(newsSourceById != null) {
				logger.info("In controller - {}", "News Source Retrieved: " +newsSourceById);
				return new ResponseEntity<NewsSource>(HttpStatus.OK);
			}
		} catch (NewsSourceNotFoundException e) {
			return new ResponseEntity<NewsSource>(HttpStatus.NOT_FOUND);
		}
		logger.info("In controller - {}", "User ID "+ userId+ " not found.");
		return new ResponseEntity<NewsSource>(HttpStatus.NOT_FOUND);
	}
	
	/*
	 * Define a handler method which will show details of all newssource created by specific 
	 * user. This handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the newssource found successfully. 
	 * 2. 404(NOT FOUND) - If the newssource with specified newsId is not found.
	 * This handler method should map to the URL "/api/v1/newssource/{userId}" using HTTP GET method
	 * where "userId" should be replaced by a valid userId without {}.
	 * 
	 */
	@GetMapping("/newssource/{userId}")
	public ResponseEntity<List<NewsSource>> getAllNewsSource(@PathVariable("userId") String userId){
		
		List<NewsSource> allNewsSource;
		try {
			allNewsSource = newsSourceService.getAllNewsSourceByUserId(userId);
			if(allNewsSource != null) {
				logger.info("In controller - {}", "News Sources Retrieved: " +allNewsSource);
				return new ResponseEntity<List<NewsSource>>(HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<List<NewsSource>>(HttpStatus.NOT_FOUND);
		}
		logger.info("In controller - {}", "User ID "+ userId + " not found.");
		return new ResponseEntity<List<NewsSource>>(HttpStatus.NOT_FOUND);
	}
    
}
