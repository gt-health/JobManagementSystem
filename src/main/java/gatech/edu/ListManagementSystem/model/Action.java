package gatech.edu.ListManagementSystem.model;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "action", schema = "listmanagementsystem")
public class Action implements Runnable{
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	@Column(name = "name")
	protected String name;
	@Column(name = "actiontype")
	protected ActionType actionType;
	@Column(name = "cronstring")
	protected String cronString;
	@OneToOne(mappedBy = "action")
	protected PersonList personList;
	@ElementCollection
	protected Map<String,String> params;
	
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
	public String getCronString() {
		return cronString;
	}
	public void setCronString(String cronString) {
		this.cronString = cronString;
	}
	public ActionType getActionType() {
		return actionType;
	}
	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}
	public PersonList getPersonList() {
		return personList;
	}
	public void setPersonList(PersonList personList) {
		this.personList = personList;
	}
	public Map<String, String> getParams() {
		return params;
	}
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	public Set<Person> runnableList(){
		Set<Person> returnSet = personList.getListElements();
		switch(personList.getRunType()) {
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
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
