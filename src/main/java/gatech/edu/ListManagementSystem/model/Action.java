package gatech.edu.ListManagementSystem.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "process", schema = "listmanagementsystem")
public class Action implements Runnable{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "steps")
	@OneToMany(mappedBy="process")
	private List<ActionStep> steps;
	@Column(name = "cronstring")
	private String cronString;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ActionStep> getSteps() {
		return steps;
	}
	public void setSteps(List<ActionStep> steps) {
		this.steps = steps;
	}
	public String getCronString() {
		return cronString;
	}
	public void setCronString(String cronString) {
		this.cronString = cronString;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
