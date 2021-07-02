package gatech.edu.JobManagementSystem.service;

import java.util.List;
import java.util.UUID;

import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.Coding;
import org.hl7.fhir.dstu3.model.DateTimeType;
import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.dstu3.model.Resource;
import org.springframework.stereotype.Service;
import org.hl7.fhir.dstu3.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.dstu3.model.Bundle.BundleType;
import org.hl7.fhir.dstu3.model.Observation.ObservationStatus;
import org.hl7.fhir.dstu3.model.Reference;

import gatech.edu.JobManagementSystem.model.ClarityNLPaaS.NLPaaSResult;
import gatech.edu.JobManagementSystem.model.ClarityNLPaaS.NLPaaSTuple;

@Service
public class NLPaaSToRegistryBundleService {

	public Bundle convert(List<NLPaaSResult> results) {
		Bundle returnBundle = new Bundle();
		initResource(returnBundle);
		returnBundle.setType(BundleType.COLLECTION);
		NLPaaSResult slyMedResult = getResultNamed(results, "Syphilis_Clinical_Treatment_Medication");
		
		List<NLPaaSTuple> tuples = slyMedResult.getTuples();
		for(NLPaaSTuple tuple:tuples) {
			Observation slyMedObs = new Observation();
			initResource(slyMedObs);
			slyMedObs.setStatus(ObservationStatus.FINAL);
			CodeableConcept concept = new CodeableConcept()
					.addCoding(new Coding("Syphilis Registry",tuple.dictionary.get("questionConcept"),"Syphilis.Clinical.Treatment.Medication"));			
			concept.setText("Drug");
			slyMedObs.setCode(concept);
			CodeableConcept valueCode = convertCQLAnswerValueToCodeableConcept(tuple.dictionary.get("answerValue"));
			slyMedObs.setValue(valueCode);
			slyMedObs.setEffective(new DateTimeType(tuple.dictionary.get("dateTime")));
			slyMedObs.setSubject(new Reference("Patient/32")); //Will work with new nlpql definition to pull patient in future
			returnBundle.addEntry(new BundleEntryComponent().setResource(slyMedObs));
		}
		return returnBundle;
	}
	
	public NLPaaSResult getResultNamed(List<NLPaaSResult> results, String name) {
		return results.stream().filter(r -> r.getCql_feature().equalsIgnoreCase(name)).findFirst().get();
	}
	
	public CodeableConcept convertCQLAnswerValueToCodeableConcept(String answerValue) {
		String[] components = answerValue.split("\\^");
		String system = components[0];
		String code = components[1];
		String display = components[2];
		CodeableConcept returnCode = new CodeableConcept()
				.addCoding(new Coding(system,code,display));
		returnCode.setText(answerValue);
		return returnCode;
	}
	
	private void initResource(Resource resource) {
		resource.setId(UUID.randomUUID().toString());
	}
	
	
}
