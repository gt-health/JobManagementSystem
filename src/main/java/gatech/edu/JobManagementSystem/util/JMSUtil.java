package gatech.edu.JobManagementSystem.util;

import gatech.edu.JobManagementSystem.model.Action;
import gatech.edu.JobManagementSystem.model.ActionType;
import gatech.edu.JobManagementSystem.model.Person;
import gatech.edu.JobManagementSystem.model.PersonList;
import gatech.edu.JobManagementSystem.model.ProcessImpl.RestAction;

public class JMSUtil {
	
	public static PersonList perparePersonListForPersistence(PersonList personList) {
		Action action = personList.getAction();
		if(action == null) {
			action = new RestAction();
			action.setName("Post to resultsmanager to update the ECR with FHIR data");
			action.setCronString("* * 8 * * *");
			action.addParam("endpoint","resultsmanager/case?patientId=${person.id}&firstName=${person.firstName}&${person.lastName}&cqlType=myCQL");
			action.addParam("operation","POST");
			action.addParam("body","");
		}
		else if(action.getActionType().equals(ActionType.REST)) {
			RestAction restAction = new RestAction();
			restAction.setCronString(action.getCronString());
			restAction.setId(action.getId());
			restAction.setName(action.getName());
			restAction.setParams(action.getParams());
			personList.setAction(restAction);
		}
		action = personList.getAction();
		action.setPersonList(personList);
		for(Person person: personList.getListElements()) {
			person.setPersonList(personList);
		}
		return personList;
	}
	
	public static String deannotateString(Action action,Person person,String inputString) {
		if(person.getReferenceId() != null) {
			inputString = inputString.replaceAll("\\$\\{person.id\\}", person.getReferenceId());
		}
		if(person.getName() != null) {
			inputString = inputString.replaceAll("\\$\\{person.name\\}", person.getName());
		}
		if(person.getName() != null) {
			inputString = inputString.replaceAll("\\$\\{person.firstName\\}", person.getFirstName());
		}
		if(person.getName() != null) {
			inputString = inputString.replaceAll("\\$\\{person.lastName\\}", person.getLastName());
		}
		if(action.getPersonList().getName() != null) {
			inputString = inputString.replaceAll("\\$\\{list.name\\}", action.getPersonList().getName());
		}
		return inputString;
	}
}
