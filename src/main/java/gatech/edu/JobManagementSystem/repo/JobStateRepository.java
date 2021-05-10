package gatech.edu.JobManagementSystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gatech.edu.JobManagementSystem.model.JobState;
import gatech.edu.JobManagementSystem.model.PersonList;

@Repository
public interface JobStateRepository extends JpaRepository<JobState, Integer>{
	public JobState findByJobId(Integer jobId);
}
