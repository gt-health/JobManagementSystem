package gatech.edu.ListManagementSystem.controller;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import gatech.edu.ListManagementSystem.ConnectionConfiguration;
import gatech.edu.ListManagementSystem.model.Action;
import gatech.edu.ListManagementSystem.model.ActionStep;
import gatech.edu.ListManagementSystem.model.PersonList;
import gatech.edu.ListManagementSystem.repo.PersonListRepository;
import gatech.edu.ListManagementSystem.repo.ActionRepository;
import gatech.edu.STIECR.DB.model.ECRData;
import gatech.edu.STIECR.DB.repo.ECRDataRepository;
import gatech.edu.STIECR.JSON.ECR;

@CrossOrigin
@RestController
public class ListManagementController {
	
	private static final Logger log = LoggerFactory.getLogger(ListManagementController.class);
	private PersonListRepository personListRepository;
	private ActionRepository actionRepository;
	private TaskScheduler taskScheduler;
	
	@Autowired
	public ListManagementController(PersonListRepository personListRepository,ActionRepository actionRepository,TaskScheduler taskScheduler) {
		this.personListRepository = personListRepository;
		this.actionRepository = actionRepository;
		this.taskScheduler = taskScheduler;
	}
	
	@RequestMapping(value = "List", method = RequestMethod.POST)
	public ResponseEntity<PersonList> postPersonList(@RequestBody PersonList list){
		personListRepository.save(list);
		//TODO: Use TaskScheduler object to schedule process
		Action action = list.getProcess();
		for(ActionStep step: action.getSteps()) {
			taskScheduler.schedule(step, new CronTrigger(action.getCronString())); //This is where the fireworks are
		}
		return new ResponseEntity<PersonList>(list,HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "List/{name}", method = RequestMethod.GET)
	public ResponseEntity<PersonList> getECR(@PathVariable("name") String name){
		PersonList personList = personListRepository.findByName(name);
		return new ResponseEntity<PersonList>(personList,HttpStatus.OK);
	}
	
	@RequestMapping(value = "Action", method = RequestMethod.POST)
	public ResponseEntity<Action> postProcess(@RequestBody Action action){
		actionRepository.save(action);
		//TODO: Use TaskManager object to schedule process
		return new ResponseEntity<Action>(action,HttpStatus.CREATED);
	}
}