package gatech.edu.JobManagementSystem;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import gatech.edu.JobManagementSystem.model.JobState;
import gatech.edu.JobManagementSystem.repo.JobStateRepository;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class JobRunnerService {

  private static final Logger logger = LoggerFactory.getLogger(JobRunnerService.class);
  private JobStateRepository jobStateRepository;
  
  public JobRunnerService(JobStateRepository jobStateRepository) {
	  this.jobStateRepository = jobStateRepository;
  }
  
  @Async
  public CompletableFuture<JobState> runJob(JobState job) throws InterruptedException {
    logger.info("Running Job " + job.getJobId());
    job.setJobState("Running");
    jobStateRepository.save(job);
    
    // Artificial delay of 5s for demonstration purposes
    Thread.sleep(10000L);
    RestTemplate restTemplate = new RestTemplate();
	String uri = "https://jsonplaceholder.typicode.com/todos/1";
	String response = restTemplate.getForObject(uri, String.class);
    job.setJobState("Completed");
    job.setResults(response);
    jobStateRepository.save(job);
    return CompletableFuture.completedFuture(job);
  }

}