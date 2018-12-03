package gatech.edu.ListManagementSystem.model.ProcessImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import gatech.edu.ListManagementSystem.model.Action;
import gatech.edu.ListManagementSystem.model.ActionType;
import gatech.edu.ListManagementSystem.model.Person;
import gatech.edu.ListManagementSystem.model.PersonProcessState;

public class RestAction extends Action{
	
	public RestAction() {
		super();
		actionType = ActionType.REST;
	}
	
	public void run() {
		for(Person person : personList.getListElements()) {
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
			person.setProcessState(PersonProcessState.COMPLETE);
		}
	}
}
