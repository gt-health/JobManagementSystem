package gatech.edu.ListManagementSystem.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	@Column(name = "listElements")
	private List<Integer> listElements;
	@Column(name = "Action")
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
	public List<Integer> getListElements() {
		return listElements;
	}
	public void setListElements(List<Integer> listElements) {
		this.listElements = listElements;
	}
	public Action getProcess() {
		return action;
	}
	public void setProcess(Action action) {
		this.action = action;
	}
	
	
}
