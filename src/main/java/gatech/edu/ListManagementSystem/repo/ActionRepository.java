package gatech.edu.ListManagementSystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import gatech.edu.ListManagementSystem.model.Action;

public interface ActionRepository extends JpaRepository<Action, Integer>{

}
