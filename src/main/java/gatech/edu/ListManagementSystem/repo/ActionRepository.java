package gatech.edu.ListManagementSystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gatech.edu.ListManagementSystem.model.Action;

@Repository
public interface ActionRepository extends JpaRepository<Action, Integer>{

}
