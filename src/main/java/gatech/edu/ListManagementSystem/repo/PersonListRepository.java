package gatech.edu.ListManagementSystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gatech.edu.ListManagementSystem.model.PersonList;

@Repository
public interface PersonListRepository extends JpaRepository<PersonList, Integer>{
	public PersonList findByName(String name);
}
