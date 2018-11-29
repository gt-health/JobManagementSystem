package gatech.edu.ListManagementSystem.model.ProcessImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import gatech.edu.ListManagementSystem.model.ActionStep;
import gatech.edu.ListManagementSystem.model.ActionStepType;

public class RestActionStep extends ActionStep{
	
	public RestActionStep() {
		super();
		type = ActionStepType.REST;
	}
	
	public void run() {
		RestTemplate rest = new RestTemplate();
		Map<String,String> paramsCopy = new HashMap<String,String>(params);
		String endpoint = params.get("endpoint");
		String operation = params.get("operation");
		String body = params.get("body");
		params.remove(endpoint);
		params.remove(operation);
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
}
