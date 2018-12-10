package gatech.edu.ListManagementSystem.model;

import java.util.List;
import java.util.Set;

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
@Table(name = "personList", schema = "listmanagementsystem")
public class PersonList {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "type")
	private ListType type;
	@Column(name = "runType")
	private ListRunType runType = ListRunType.ALL;
	@Column(name = "listElements")
	@OneToMany
	private Set<Person> listElements;
	@OneToOne
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
	public ListType getType() {
		return type;
	}
	public void setType(ListType type) {
		this.type = type;
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
}