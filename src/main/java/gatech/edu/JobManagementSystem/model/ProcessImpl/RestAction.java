package gatech.edu.JobManagementSystem.model.ProcessImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.web.client.RestTemplate;

import gatech.edu.JobManagementSystem.model.Action;
import gatech.edu.JobManagementSystem.model.ActionType;
import gatech.edu.JobManagementSystem.model.ListRunType;
import gatech.edu.JobManagementSystem.model.Person;
import gatech.edu.JobManagementSystem.model.PersonProcessState;
import gatech.edu.JobManagementSystem.util.JMSUtil;

public class RestAction extends Action{
	
	public RestAction() {
		super();
		actionType = ActionType.REST;
	}
	
	public void run() {
		Set<Person> list = personList.getRunnableList();
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
			String endpoint = paramsCopy.get("endpoint");
			endpoint = JMSUtil.deannotateString(this, person, endpoint);
			String operation = paramsCopy.get("operation");
			String body = params.get("body");
			body = JMSUtil.deannotateString(this, person, body);
			paramsCopy.remove(endpoint);
			paramsCopy.remove(operation);
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
	}
}