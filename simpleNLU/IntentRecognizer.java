package personal.project.simpleNLU;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import java.util.TreeMap;

import personal.project.simpleNLU.ComponentManager.FRAGMENT_TYPE;

public class IntentRecognizer implements Recognizer {
	ComponentManager cm;

	private IntentRecognizer(ComponentManager cm) throws Exception {
		setComponentManager(cm);
	}

	public IntentRecognizer create(ComponentManager cm) throws Exception {
		return new IntentRecognizer(cm);
	}

	public void setComponentManager(ComponentManager cm) throws Exception {
		if (cm == null) {
			throw new Exception("ComponentManager cannot be null.");
		}

		this.cm = cm;
	}

	public ArrayList<UtteranceEvaluation> evaluate(String utterance) throws Exception {
		HashMap<String, TreeMap<Integer, Pair<String, String>>> agentGuidsWithEntityGuidsAndSynonyms = retrieveEntitiesFromUtterance(
				utterance);
		HashMap<String, HashMap<String, ArrayList<String>>> agentsIntentsPhrasesMatched = retrievePhrasesMatchedWithEntities(
				agentGuidsWithEntityGuidsAndSynonyms);
		ArrayList<UtteranceEvaluation> evaluationResults = retrievePhrasesWithSentencesMatched(
				agentsIntentsPhrasesMatched, utterance);

		return evaluationResults;
	}

	private HashMap<String, TreeMap<Integer, Pair<String, String>>> retrieveEntitiesFromUtterance(String utterance)
			throws Exception {
		utteranceExceptionCheck(utterance);
		utterance = utterance.trim().toLowerCase();

		HashMap<String, TreeMap<Integer, Pair<String, String>>> agentGuidsWithEntityGuidsAndSynonyms = new HashMap<>();
		ArrayList<String> agentGuids = cm.getAgents();

		for (String agentGuid : agentGuids) {
			ArrayList<String> entityGuids = cm.getEntities(agentGuid);
			TreeMap<Integer, Pair<String, String>> foundEntitiesInOrder = new TreeMap<>();

			for (String entityGuid : entityGuids) {
				ArrayList<String> entitySynonyms = cm.getEntitySynonyms(agentGuid, entityGuid);

				for (String synonym : entitySynonyms) {
					synonym = synonym.toLowerCase();

					if (utterance.contains(synonym)) {
						int indexOfMatched = utterance.indexOf(synonym);

						if (indexOfMatched == 0) {
							if (synonym.length() == utterance.length() || synonym.length() < utterance.length()
									&& utterance.charAt(synonym.length()) == ' ') {
								foundEntitiesInOrder.put(utterance.indexOf(synonym), new Pair<>(entityGuid, synonym));
							}
						} else if (indexOfMatched + synonym.length() == utterance.length()) {
							if (utterance.charAt(indexOfMatched - 1) == ' ') {
								foundEntitiesInOrder.put(utterance.indexOf(synonym), new Pair<>(entityGuid, synonym));
							}
						} else if (utterance.charAt(indexOfMatched - 1) == ' '
								&& utterance.charAt(indexOfMatched + 1) == ' ') {
							foundEntitiesInOrder.put(utterance.indexOf(synonym), new Pair<>(entityGuid, synonym));
						}
					}
				}
			}

			if (foundEntitiesInOrder.size() > 0) {
				agentGuidsWithEntityGuidsAndSynonyms.put(agentGuid, foundEntitiesInOrder);
			}
		}

		return agentGuidsWithEntityGuidsAndSynonyms;
	}

	private HashMap<String, HashMap<String, ArrayList<String>>> retrievePhrasesMatchedWithEntities(
			HashMap<String, TreeMap<Integer, Pair<String, String>>> agentGuidsWithEntityGuidsAndSynonyms)
			throws Exception {

		HashMap<String, HashMap<String, ArrayList<String>>> agentsIntentsPhrasesMatched = new HashMap<>();

		for (Entry<String, TreeMap<Integer, Pair<String, String>>> entry : agentGuidsWithEntityGuidsAndSynonyms
				.entrySet()) {
			String agentGuid = entry.getKey();

			ArrayList<String> intentGuids = cm.getIntents(agentGuid);

			for (String intentGuid : intentGuids) {
				ArrayList<String> phraseGuids = cm.getPhrases(agentGuid, intentGuid);

				for (String phraseGuid : phraseGuids) {
					ArrayList<String> phraseEntities = cm.getPhraseSentenceEntities(agentGuid, phraseGuid);

					if (phraseEntities.size() == entry.getValue().size()) {
						int i = 0;
						boolean matchOrdering = true;
						for (Entry<Integer, Pair<String, String>> treeMapEntry : entry.getValue().entrySet()) {
							if (!phraseEntities.get(i).equals(treeMapEntry.getKey())) {
								matchOrdering = false;
								break;
							}
						}

						if (matchOrdering) {
							if (agentsIntentsPhrasesMatched.containsKey(agentGuid)) {
								agentsIntentsPhrasesMatched.get(agentGuid).get(intentGuid).add(phraseGuid);
							} else {
								HashMap<String, ArrayList<String>> intentsPhrasesHm = new HashMap<>();
								ArrayList<String> newPhraseGuids = new ArrayList<>();
								newPhraseGuids.add(phraseGuid);
								intentsPhrasesHm.put(intentGuid, newPhraseGuids);
								agentsIntentsPhrasesMatched.put(agentGuid, intentsPhrasesHm);
							}
						}
					}
				}
			}
		}

		return agentsIntentsPhrasesMatched;
	}

	private ArrayList<UtteranceEvaluation> retrievePhrasesWithSentencesMatched(
			HashMap<String, HashMap<String, ArrayList<String>>> agentsIntentsPhrases, String utterance)
			throws Exception {
		utteranceExceptionCheck(utterance);
		utterance = utterance.trim().toLowerCase();

		ArrayList<UtteranceEvaluation> evaluationAl = new ArrayList<>();

		for (Entry<String, HashMap<String, ArrayList<String>>> agentEntries : agentsIntentsPhrases.entrySet()) {
			HashMap<String, ArrayList<String>> intentsPhrasesHm = agentEntries.getValue();
			String agentGuid = agentEntries.getKey();

			for (Entry<String, ArrayList<String>> intentEntries : intentsPhrasesHm.entrySet()) {
				ArrayList<String> phraseGuids = intentEntries.getValue();
				String intentGuid = intentEntries.getKey();

				for (String phraseGuid : phraseGuids) {
					ArrayList<Pair<String, FRAGMENT_TYPE>> sentenceElements = cm.getPhraseSentenceElements(agentGuid,
							intentGuid, phraseGuid);
					StringBuilder utteranceToProcess = new StringBuilder(utterance);

					int numberOfMatches = 0;
					ArrayList<Pair<String, String>> utteranceEntityValues = new ArrayList<>();

					for (int i = 0; i < sentenceElements.size(); i++) {
						Pair<String, FRAGMENT_TYPE> sentenceElement = sentenceElements.get(i);
						String sentenceElementGuid = sentenceElement.getKey();
						ArrayList<String> synonyms = null;

						if (sentenceElement.getValue() == ComponentManager.FRAGMENT_TYPE.ENTITY) {
							synonyms = cm.getEntitySynonyms(agentGuid, sentenceElementGuid);
						} else if (sentenceElement.getValue() == ComponentManager.FRAGMENT_TYPE.WORD) {
							synonyms = cm.getWordSynonyms(agentGuid, sentenceElementGuid);
						}

						boolean tokenFound = false;
						if (synonyms != null) {
							for (String synonym : synonyms) {
								synonym = synonym.toLowerCase();
								int indexOfMatched = utteranceToProcess.indexOf(synonym);

								if (indexOfMatched >= 0) {
									if ((indexOfMatched == 0 && (synonym.length() == utteranceToProcess.length()
											|| synonym.length() < utteranceToProcess.length()
													&& utteranceToProcess.charAt(synonym.length()) == ' '))
											|| (indexOfMatched + synonym.length() == utteranceToProcess.length()
													&& utteranceToProcess.charAt(indexOfMatched - 1) == ' ')
											|| (utteranceToProcess.charAt(indexOfMatched - 1) == ' '
													&& utteranceToProcess.charAt(indexOfMatched + 1) == ' ')) {
										utteranceToProcess.delete(0, indexOfMatched + synonym.length());

										if (sentenceElement.getValue() == ComponentManager.FRAGMENT_TYPE.ENTITY) {
											utteranceEntityValues
													.add(new Pair<String, String>(sentenceElementGuid, synonym));
										}

										tokenFound = true;
										numberOfMatches++;
										break;
									}
								}
							}
						}
						if (!tokenFound) {
							break;
						}
					}

					if (numberOfMatches == sentenceElements.size()) {
						UtteranceEvaluation ue = new UtteranceEvaluation();
						ue.setAgentGuid(agentGuid);
						ue.setIntentGuid(intentGuid);
						ue.setPhraseGuid(phraseGuid);
						ue.setEntityGuidsWithUtteranceValue(utteranceEntityValues);
						ue.setNumOfFragmentMatched(numberOfMatches);
						evaluationAl.add(ue);
					}
				}
			}
		}

		return evaluationAl;
	}

	private void utteranceExceptionCheck(String utterance) throws Exception {
		if (utterance == null || utterance.trim().equals("")) {
			throw new Exception("Utterance cannot be null or empty.");
		}
	}
}
