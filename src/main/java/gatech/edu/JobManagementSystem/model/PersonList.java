package gatech.edu.JobManagementSystem.model;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "personList")
public class PersonList {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "jobType")
	private String jobType;
	@Column(name = "listType")
	private ListType listType = ListType.SINGLE_USE;
	@Column(name = "runType")
	private ListRunType runType = ListRunType.ALL;
	@Column(name = "listElements")
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="personList")
	private Set<Person> listElements;
	@OneToOne(mappedBy = "personList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Action action;
	
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
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public ListType getListType() {
		return listType;
	}
	public void setListType(ListType type) {
		this.listType = type;
	}
	public ListRunType getRunType() {
		return runType;
	}
	public void setRunType(ListRunType runType) {
		this.runType = runType;
	}
	public Set<Person> getListElements() {
		return listElements;
	}
	public void setListElements(Set<Person> listElements) {
		this.listElements = listElements;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	
	@JsonIgnore
	public Set<Person> getRunnableList(){
		Set<Person> returnSet = listElements;
		switch(runType) {
		case NEW_ONLY:
			returnSet = returnSet.stream().filter(x -> x.getProcessState().equals(PersonProcessState.NONE)
					|| x.getProcessState().equals(PersonProcessState.INLIST)
					|| x.getProcessState().equals(PersonProcessState.ERROR)).collect(Collectors.toSet());
			break;
		default:
		}
		return returnSet;
	}
	
	@Override
	public String toString() {
		return "PersonList [id=" + id + ", name=" + name + ", jobType=" + jobType + ", listType=" + listType
				+ ", runType=" + runType + ", listElements=" + listElements + ", action=" + action + "]";
	}
	
	
}