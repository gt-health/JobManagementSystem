package gatech.edu.ListManagementSystem.model.ProcessImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.web.client.RestTemplate;

import gatech.edu.ListManagementSystem.model.Action;
import gatech.edu.ListManagementSystem.model.ActionType;
import gatech.edu.ListManagementSystem.model.ListRunType;
import gatech.edu.ListManagementSystem.model.Person;
import gatech.edu.ListManagementSystem.model.PersonProcessState;

public class RestAction extends Action{
	
	public RestAction() {
		super();
		actionType = ActionType.REST;
	}
	
	public void run() {
		Set<Person> list = runnableList();
		for(Person person : list) {
			boolean runBefore = false;
			switch(person.getProcessState()) {
			case NEW_COMPLETE:
			case OLD_COMPLETE:
			case ERROR:
				runBefore = true;
				break;
			}
			person.setProcessState(PersonProcessState.PROCESSING);
			RestTemplate rest = new RestTemplate();
			Map<String,String> paramsCopy = new HashMap<String,String>(params);
			String endpoint = params.get("endpoint");
			String operation = params.get("operation");
			String body = params.get("body");
			params.remove(endpoint);
			params.remove(operation);
			try {
				switch(operation) {
				case "GET":
					rest.getForEntity(endpoint, String.class, paramsCopy);
					break;
				case "POST":
					rest.postForEntity(endpoint, body, String.class, paramsCopy);
					break;
				case "PUT":
					rest.put(endpoint, body, paramsCopy);
					break;
				case "DELETE":
					rest.delete(endpoint, paramsCopy);
					break;
				}
			}
			catch(Exception e) {
				person.setProcessState(PersonProcessState.ERROR);
				continue;
			}
			if(runBefore)
				person.setProcessState(PersonProcessState.OLD_COMPLETE);
			else
				person.setProcessState(PersonProcessState.NEW_COMPLETE);
		}
		personList.setRunType(ListRunType.ALL);
	}
}
