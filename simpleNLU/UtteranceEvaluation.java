package personal.project.simpleNLU;

import java.util.ArrayList;

public class UtteranceEvaluation {

	private String phraseGuid;
	private String intentGuid;
	private String agentGuid;
	private int numOfFragmentMatched;
	private ArrayList<Pair<String, String>> entityGuidsWithUtteranceValue;

	public UtteranceEvaluation() {
		phraseGuid = null;
		intentGuid = null;
		agentGuid = null;
		numOfFragmentMatched = 0;
		entityGuidsWithUtteranceValue = new ArrayList<>();
	}

	public String getIntentGuid() {
		return intentGuid;
	}

	public void setIntentGuid(String intentGuid) {
		this.intentGuid = intentGuid;
	}

	public String getAgentGuid() {
		return agentGuid;
	}

	public void setAgentGuid(String agentGuid) {
		this.agentGuid = agentGuid;
	}

	public int getNumOfFragmentMatched() {
		return numOfFragmentMatched;
	}

	public void setNumOfFragmentMatched(int numOfFragmentMatched) {
		this.numOfFragmentMatched = numOfFragmentMatched;
	}

	public ArrayList<Pair<String, String>> getEntityGuidsWithUtteranceValue() {
		return entityGuidsWithUtteranceValue;
	}

	public void setEntityGuidsWithUtteranceValue(ArrayList<Pair<String, String>> entityGuidsWithUtteranceValue) {
		this.entityGuidsWithUtteranceValue = entityGuidsWithUtteranceValue;
	}

	public void addEntityGuidsWithUtteranceValue(ArrayList<Pair<String, String>> entityGuidsWithUtteranceValue) {
		this.entityGuidsWithUtteranceValue.addAll(entityGuidsWithUtteranceValue);
	}

	public String getPhraseGuid() {
		return phraseGuid;
	}

	public void setPhraseGuid(String phraseGuid) {
		this.phraseGuid = phraseGuid;
	}
}
