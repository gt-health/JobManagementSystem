package gatech.edu.JobManagementSystem.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="job")
public class JobState{
	@Id
	@Column(name = "jobId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer jobId;
	@Column(name = "params")
	private String params;
	@Column(name = "results")
	private String results;
	@Column(name = "timeStarted")
	private String timeStarted;
	@Column(name = "jobState")
	private String jobState;
	
	public Integer getJobId() {
		return jobId;
	}
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
	
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	
	public String getResults() {
		return results;
	}
	public void setResults(String results) {
		this.results = results;
	}
	
	public String getTimeStarted() {
		return timeStarted;
	}
	public void setTimeStarted(String timeStarted) {
		this.timeStarted = timeStarted;
	}
	
	public String getJobState() {
		return jobState;
	}
	
	public void setJobState(String jobState) {
		this.jobState = jobState;
	}
	
}