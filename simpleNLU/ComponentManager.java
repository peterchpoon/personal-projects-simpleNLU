package personal.project.simpleNLU;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class ComponentManager {
	public static enum FRAGMENT_TYPE {
		WORD, ENTITY
	};

	private HashMap<String, Agent> agentsHm;
	private HashMap<String, HashMap<String, Entity>> agentsEntitiesHm;
	private HashMap<String, HashMap<String, Word>> agentsWordsHm;
	private HashMap<String, HashMap<String, Intent>> agentsIntentsHm;
	private HashMap<String, HashMap<String, Phrase>> agentsPhrasesHm;

	public ComponentManager() {
		agentsHm = new HashMap<>();
		agentsEntitiesHm = new HashMap<>();
		agentsWordsHm = new HashMap<>();
		agentsIntentsHm = new HashMap<>();
		agentsPhrasesHm = new HashMap<>();
	}

	public String createAgent() {
		Agent agent = Agent.create();
		addAgentToHms(agent);
		return agent.getGuid();
	}

	public String createAgent(String name) {
		Agent agent = Agent.create(name);
		addAgentToHms(agent);
		return agent.getGuid();
	}

	public String createAgent(String name, String description) {
		Agent agent = Agent.create(name, description);

		addAgentToHms(agent);
		return agent.getGuid();
	}

	public ArrayList<String> createAgents(ArrayList<String> names) {
		ArrayList<String> agentGuids = new ArrayList<>();
		if (names != null) {
			for (String name : names) {
				agentGuids.add(createAgent(name));
			}
		}
		return agentGuids;
	}

	public ArrayList<String> createAgentsNameDescription(ArrayList<Pair<String, String>> namesDescriptions) {
		ArrayList<String> agentGuids = new ArrayList<>();
		if (namesDescriptions != null) {
			for (Pair<String, String> nameDescription : namesDescriptions) {
				agentGuids.add(createAgent(nameDescription.getKey(), nameDescription.getValue()));
			}
		}
		return agentGuids;
	}

	public void modifyAgentNameDescription(String agentGuid, String name, String description) throws Exception {
		modifyAgentName(agentGuid, name);
		modifyAgentDescription(agentGuid, description);
	}

	public void modifyAgentName(String guid, String name) throws Exception {
		agentExceptionCheck(guid, "agent name modification");
		Agent agent = getAgent(guid);

		agent.setName(name);
	}

	public void modifyAgentDescription(String guid, String description) throws Exception {
		agentExceptionCheck(guid, "agent description modification");
		Agent agent = getAgent(guid);

		agent.setDescription(description);
	}

	public boolean isAgentFound(String guid) {
		return guid != null && agentsHm.containsKey(guid);
	}

	private void addAgentToHms(Agent agent) {
		agentsHm.put(agent.getGuid(), agent);
		agentsEntitiesHm.put(agent.getGuid(), new HashMap<>());
		agentsWordsHm.put(agent.getGuid(), new HashMap<>());
		agentsIntentsHm.put(agent.getGuid(), new HashMap<>());
		agentsPhrasesHm.put(agent.getGuid(), new HashMap<>());
	}

	private void removeAgentFromHms(String guid) throws Exception {
		agentExceptionCheck(guid, "agent removal");

		agentsHm.remove(guid);
		agentsEntitiesHm.remove(guid);
		agentsWordsHm.remove(guid);
		agentsIntentsHm.remove(guid);
		agentsPhrasesHm.remove(guid);
	}

	private Agent getAgent(String guid) {
		Agent retVal = null;

		if (guid != null) {
			retVal = agentsHm.get(guid);
		}
		return retVal;
	}

	public ArrayList<String> getAgents() {
		ArrayList<String> agentGuids = new ArrayList<>(agentsHm.size());
		for (Agent agent : agentsHm.values()) {
			agentGuids.add(agent.getGuid());
		}
		return agentGuids;
	}

	public String getAgentName(String agentGuid) throws Exception {
		agentExceptionCheck(agentGuid, "agent name retrieval");

		Agent agent = agentsHm.get(agentGuid);

		if (agent == null) {
			throw new Exception(
					"Internal error: agent GUID: " + agentGuid + " was found null during agent name retrieval");
		} else {
			return agent.getName();
		}
	}

	public void removeAgent(String guid) throws Exception {
		removeAgentFromHms(guid);
	}

	public void removeAgents(ArrayList<String> agentGuids) throws Exception {
		if (agentGuids != null) {
			for (String agentGuid : agentGuids) {
				removeAgent(agentGuid);
			}
		}
	}

	public String createIntent(String agentGuid) throws Exception {
		agentExceptionCheck(agentGuid, "agent intent creation");

		Intent intent = Intent.create();
		agentsIntentsHm.get(agentGuid).put(intent.getGuid(), intent);
		return intent.getGuid();
	}

	public String createIntent(String agentGuid, String intentName) throws Exception {
		agentExceptionCheck(agentGuid, "agent intent creation");

		Intent intent = Intent.create(intentName);
		agentsIntentsHm.get(agentGuid).put(intent.getGuid(), intent);
		return intent.getGuid();
	}

	public String createIntent(String agentGuid, String intentName, String intentDescription) throws Exception {
		agentExceptionCheck(agentGuid, "agent intent creation");
		Intent intent = Intent.create(intentName, intentDescription);

		agentsIntentsHm.get(agentGuid).put(intent.getGuid(), intent);
		return intent.getGuid();
	}

	public ArrayList<String> createIntents(String agentGuid, ArrayList<String> intentNames) throws Exception {
		agentExceptionCheck(agentGuid, "agent intent creation");
		ArrayList<String> intentGuids = new ArrayList<>();
		if (intentNames != null) {
			for (String intentName : intentNames) {
				intentGuids.add(createIntent(agentGuid, intentName));
			}
		}
		return intentGuids;
	}

	public ArrayList<String> createIntentsNameDescription(String agentGuid,
			ArrayList<Pair<String, String>> intentNamesDescriptions) throws Exception {
		agentExceptionCheck(agentGuid, "agent intent creation");
		ArrayList<String> intentGuids = new ArrayList<>();
		if (intentNamesDescriptions != null) {
			for (Pair<String, String> intentNameDescription : intentNamesDescriptions) {
				intentGuids
						.add(createIntent(agentGuid, intentNameDescription.getKey(), intentNameDescription.getValue()));
			}
		}
		return intentGuids;
	}

	public void modifyIntentNameDescription(String agentGuid, String intentGuid, String name, String description)
			throws Exception {
		modifyIntentName(agentGuid, intentGuid, name);
		modifyIntentDescription(agentGuid, intentGuid, description);
	}

	public void modifyIntentName(String agentGuid, String intentGuid, String name) throws Exception {
		intentExceptionCheck(agentGuid, intentGuid, "intent name modification");
		HashMap<String, Intent> intentsHm = agentsIntentsHm.get(agentGuid);
		intentsHm.get(intentGuid).setName(name);
	}

	public void modifyIntentDescription(String agentGuid, String intentGuid, String description) throws Exception {
		intentExceptionCheck(agentGuid, intentGuid, "intent description modification");
		HashMap<String, Intent> intentsHm = agentsIntentsHm.get(agentGuid);
		intentsHm.get(intentGuid).setDescription(description);
	}

	public ArrayList<String> getIntents(String agentGuid) throws Exception {
		agentExceptionCheck(agentGuid, "agent intents retrieval");
		ArrayList<String> intentGuids = new ArrayList<>();

		HashMap<String, Intent> intentsHm = agentsIntentsHm.get(agentGuid);

		if (intentsHm == null) {
			throw new Exception("Internal error: intents of agent GUID: " + agentGuid
					+ " was found null during agent intent retrieval");
		} else {
			for (Entry<String, Intent> entry : intentsHm.entrySet()) {
				intentGuids.add(entry.getKey());
			}
		}

		return intentGuids;
	}

	public String getIntentName(String agentGuid, String intentGuid) throws Exception {
		intentExceptionCheck(agentGuid, intentGuid, "intent name retrieval");

		Intent intent = agentsIntentsHm.get(agentGuid).get(intentGuid);

		if (intent == null) {
			throw new Exception("Internal error: intent GUID: " + intentGuid + "  of agent GUID: " + agentGuid
					+ " was found null during intent name retrieval");
		} else {
			return intent.getName();
		}
	}

	private ArrayList<WordFragment> constructWordFragments(String agentGuid,
			ArrayList<Pair<FRAGMENT_TYPE, String>> sentence) throws Exception {

		ArrayList<WordFragment> wordFragments = new ArrayList<>(sentence.size());

		for (Pair<FRAGMENT_TYPE, String> token : sentence) {
			if (token == null) {
				throw new Exception("Word Fragment cannot be null in phrase.");
			}

			String guid = token.getValue();

			if (guid == null) {
				throw new Exception("Word/Entity GUID cannot be null.");
			}

			WordFragment wordFragment = null;

			if (token.getKey() == FRAGMENT_TYPE.WORD) {
				HashMap<String, Word> wordsHm = agentsWordsHm.get(agentGuid);
				if (wordsHm != null) {
					wordFragment = wordsHm.get(guid);

				}
			} else if (token.getKey() == FRAGMENT_TYPE.ENTITY) {
				HashMap<String, Entity> entitiesHm = agentsEntitiesHm.get(agentGuid);
				if (entitiesHm != null) {
					wordFragment = entitiesHm.get(guid);

				}
			}

			if (wordFragment == null) {
				throw new Exception("Word/Entity GUID: " + guid + " cannot be found in Agent.");
			} else {
				wordFragments.add(wordFragment);
			}
		}

		return wordFragments;
	}

	public void setIntentPhrase(String agentGuid, String intentGuid, String phraseGuid,
			ArrayList<Pair<FRAGMENT_TYPE, String>> sentence) throws Exception {
		phraseExceptionCheck(agentGuid, intentGuid, phraseGuid, "intent phrase modification");

		if (sentence != null) {
			HashMap<String, Intent> intentsHm = agentsIntentsHm.get(agentGuid);
			Intent intent = intentsHm.get(intentGuid);
			Phrase phrase = intent.getPhrase(phraseGuid);

			ArrayList<WordFragment> wordFragments = constructWordFragments(agentGuid, sentence);

			if (!wordFragments.isEmpty()) {
				phrase.setWordFragments(wordFragments);
			}
		}
	}

	public void addIntentPhrase(String agentGuid, String intentGuid, ArrayList<Pair<FRAGMENT_TYPE, String>> sentence)
			throws Exception {
		intentExceptionCheck(agentGuid, intentGuid, "intent phrase creation");

		if (sentence != null) {
			HashMap<String, Intent> intentsHm = agentsIntentsHm.get(agentGuid);
			Intent intent = intentsHm.get(intentGuid);

			ArrayList<WordFragment> wordFragments = constructWordFragments(agentGuid, sentence);

			if (!wordFragments.isEmpty()) {
				Phrase phrase = Phrase.create(wordFragments);
				intent.addPhrase(phrase);
				
				agentsPhrasesHm.get(agentGuid).put(phrase.getGuid(), phrase);
			}
		}
	}

	public void addIntentPhrases(String agentGuid, String intentGuid,
			ArrayList<ArrayList<Pair<FRAGMENT_TYPE, String>>> sentences) throws Exception {

		if (sentences != null) {
			for (ArrayList<Pair<FRAGMENT_TYPE, String>> sentence : sentences) {
				addIntentPhrase(agentGuid, intentGuid, sentence);
			}
		}
	}

	public void removeIntentPhrase(String agentGuid, String intentGuid, String phraseGuid) throws Exception {
		phraseExceptionCheck(agentGuid, intentGuid, phraseGuid, "intent phrase removal");
		HashMap<String, Intent> intentsHm = agentsIntentsHm.get(agentGuid);
		intentsHm.get(intentGuid).removePhrase(phraseGuid);
		
		HashMap<String, Phrase> phrasesHm = agentsPhrasesHm.get(agentGuid);
		phrasesHm.remove(phraseGuid);
	}

	public void removeIntentPhrases(String agentGuid, String intentGuid, ArrayList<String> phraseGuids)
			throws Exception {
		if (phraseGuids != null) {
			for (String phraseGuid : phraseGuids) {
				removeIntentPhrase(agentGuid, intentGuid, phraseGuid);
			}
		}
	}
	
	public ArrayList<String> getPhrases(String agentGuid) throws Exception {
		agentExceptionCheck(agentGuid, "agent phrases retrieval");
		ArrayList<String> phraseGuids;

		HashMap<String, Phrase> phrasesHm = agentsPhrasesHm.get(agentGuid);
		phraseGuids = new ArrayList<>(phrasesHm.size());

		for (Phrase phrase : phrasesHm.values()) {
			phraseGuids.add(phrase.getGuid());
		}

		return phraseGuids;
	}

	public ArrayList<String> getPhrases(String agentGuid, String intentGuid) throws Exception {
		intentExceptionCheck(agentGuid, intentGuid, "intent phrases retrieval");
		ArrayList<String> phraseGuids = new ArrayList<>();

		HashMap<String, Intent> intentsHm = agentsIntentsHm.get(agentGuid);

		if (intentsHm == null) {
			throw new Exception("Internal error: intents of agent GUID: " + agentGuid
					+ " was found null during agent intent retrieval");
		}

		Intent intent = intentsHm.get(intentGuid);
		if (intent == null) {
			throw new Exception("Internal error: intent GUID: " + intentGuid + "  of agent GUID: " + agentGuid
					+ " was found null during intent name retrieval");
		}

		phraseGuids = intent.getPhraseGuids();

		return phraseGuids;
	}

	public ArrayList<Pair<String, FRAGMENT_TYPE>> getPhraseSentenceElements(String agentGuid, String intentGuid,
			String phraseGuid) throws Exception {
		phraseExceptionCheck(agentGuid, intentGuid, phraseGuid, "phrase sentence retrieval");

		Phrase phrase = agentsIntentsHm.get(agentGuid).get(intentGuid).getPhrase(phraseGuid);

		if (phrase == null) {
			throw new Exception("Internal error: phrase GUID: " + phraseGuid + "  of intent GUID: " + intentGuid
					+ " was found null during phrase sentence retrieval");
		}

		ArrayList<WordFragment> wordFragments = phrase.getWordFragments();
		ArrayList<Pair<String, FRAGMENT_TYPE>> sentenceElements = new ArrayList<>(wordFragments.size());

		for (WordFragment wordFragment : wordFragments) {
			Pair<String, FRAGMENT_TYPE> sentenceElement = null;

			if (wordFragment instanceof Entity) {
				sentenceElement = new Pair<>(wordFragment.getGuid(), FRAGMENT_TYPE.ENTITY);
			} else if (wordFragment instanceof Word) {
				sentenceElement = new Pair<>(wordFragment.getGuid(), FRAGMENT_TYPE.WORD);
			}
			sentenceElements.add(sentenceElement);
		}

		return sentenceElements;
	}

	public ArrayList<Pair<String, FRAGMENT_TYPE>> getPhraseSentenceElements(String agentGuid, String phraseGuid) throws Exception {
		phraseExceptionCheck(agentGuid, phraseGuid, "phrase sentence retrieval");

		Phrase phrase = agentsPhrasesHm.get(agentGuid).get(phraseGuid);

		if (phrase == null) {
			throw new Exception("Internal error: phrase GUID: " + phraseGuid + "  of agent GUID: " + agentGuid
					+ " was found null during phrase sentence retrieval");
		}

		ArrayList<WordFragment> wordFragments = phrase.getWordFragments();
		ArrayList<Pair<String, FRAGMENT_TYPE>> sentenceElements = new ArrayList<>(wordFragments.size());

		for (WordFragment wordFragment : wordFragments) {
			Pair<String, FRAGMENT_TYPE> sentenceElement = null;

			if (wordFragment instanceof Entity) {
				sentenceElement = new Pair<>(wordFragment.getGuid(), FRAGMENT_TYPE.ENTITY);
			} else if (wordFragment instanceof Word) {
				sentenceElement = new Pair<>(wordFragment.getGuid(), FRAGMENT_TYPE.WORD);
			}
			sentenceElements.add(sentenceElement);
		}

		return sentenceElements;
	}

	public ArrayList<String> getPhraseSentenceEntities(String agentGuid, String phraseGuid) throws Exception {
		phraseExceptionCheck(agentGuid, phraseGuid, "phrase sentence entities retrieval");

		Phrase phrase = agentsPhrasesHm.get(agentGuid).get(phraseGuid);

		if (phrase == null) {
			throw new Exception("Internal error: phrase GUID: " + phraseGuid + "  of agent GUID: " + agentGuid
					+ " was found null during phrase sentence entities retrieval");
		}

		return phrase.getEntities();
	}

	public String createEntity(String agentGuid) throws Exception {
		agentExceptionCheck(agentGuid, "agent entity creation");

		Entity entity = Entity.create();
		agentsEntitiesHm.get(agentGuid).put(entity.getGuid(), entity);
		return entity.getGuid();
	}

	public String createEntity(String agentGuid, String entityName) throws Exception {
		agentExceptionCheck(agentGuid, "agent entity creation");

		Entity entity = Entity.create(entityName);
		agentsEntitiesHm.get(agentGuid).put(entity.getGuid(), entity);
		return entity.getGuid();
	}

	public String createEntity(String agentGuid, String entityName, String entityDescription) throws Exception {
		agentExceptionCheck(agentGuid, "agent entity creation");

		Entity entity = Entity.create(entityName, entityDescription);

		agentsEntitiesHm.get(agentGuid).put(entity.getGuid(), entity);
		return entity.getGuid();
	}

	public String createEntity(String agentGuid, String entityName, String entityDescription,
			ArrayList<String> synonyms) throws Exception {
		agentExceptionCheck(agentGuid, "agent entity creation");

		Entity entity = Entity.create(entityName, entityDescription, synonyms);

		agentsEntitiesHm.get(agentGuid).put(entity.getGuid(), entity);
		return entity.getGuid();
	}

	public ArrayList<String> createEntities(String agentGuid,
			ArrayList<Pair<String, Pair<String, ArrayList<String>>>> entitiesAttributes) throws Exception {
		agentExceptionCheck(agentGuid, "agent entity creation");
		ArrayList<String> entityGuids = new ArrayList<>();
		if (entitiesAttributes != null) {
			for (Pair<String, Pair<String, ArrayList<String>>> entityAttributes : entitiesAttributes) {
				if (entityAttributes != null) {
					String entityName = entityAttributes.getKey();
					Pair<String, ArrayList<String>> entityDescriptionSynonyms = entityAttributes.getValue();

					if (entityDescriptionSynonyms == null) {
						entityGuids.add(createEntity(agentGuid, entityName));
					} else {
						entityGuids.add(createEntity(agentGuid, entityName, entityDescriptionSynonyms.getKey(),
								entityDescriptionSynonyms.getValue()));
					}
				}
			}
		}
		return entityGuids;
	}

	public void modifyEntityNameDescription(String agentGuid, String entityGuid, String name, String description)
			throws Exception {
		modifyEntityName(agentGuid, entityGuid, name);
		modifyEntityDescription(agentGuid, entityGuid, description);
	}

	public void modifyEntityName(String agentGuid, String entityGuid, String name) throws Exception {
		entityExceptionCheck(agentGuid, entityGuid, "entity name modification");
		HashMap<String, Entity> entitiesHm = agentsEntitiesHm.get(agentGuid);
		entitiesHm.get(entityGuid).setName(name);
	}

	public void modifyEntityDescription(String agentGuid, String entityGuid, String description) throws Exception {
		entityExceptionCheck(agentGuid, entityGuid, "entity description modification");
		HashMap<String, Entity> entitiesHm = agentsEntitiesHm.get(agentGuid);
		entitiesHm.get(entityGuid).setDescription(description);
	}

	public void modifyEntitySynonym(String agentGuid, String entityGuid, String from, String to) throws Exception {
		entityExceptionCheck(agentGuid, entityGuid, "entity synonym modification");
		HashMap<String, Entity> entitiesHm = agentsEntitiesHm.get(agentGuid);
		entitiesHm.get(entityGuid).modifySynonym(from, to);
	}

	public void setEntitySynonyms(String agentGuid, String entityGuid, ArrayList<String> synonyms) throws Exception {
		entityExceptionCheck(agentGuid, entityGuid, "entity synonyms modification");
		HashMap<String, Entity> entitiesHm = agentsEntitiesHm.get(agentGuid);
		entitiesHm.get(entityGuid).setSynonyms(synonyms);
	}

	public void addEntitySynonyms(String agentGuid, String entityGuid, ArrayList<String> synonyms) throws Exception {
		entityExceptionCheck(agentGuid, entityGuid, "entity synonyms modification");
		HashMap<String, Entity> entitiesHm = agentsEntitiesHm.get(agentGuid);
		entitiesHm.get(entityGuid).addSynonyms(synonyms);
	}

	public void addEntitySynonym(String agentGuid, String entityGuid, String synonym) throws Exception {
		entityExceptionCheck(agentGuid, entityGuid, "entity synonyms modification");
		HashMap<String, Entity> entitiesHm = agentsEntitiesHm.get(agentGuid);
		entitiesHm.get(entityGuid).addSynonym(synonym);
	}

	public void removeEntitySynonyms(String agentGuid, String entityGuid, ArrayList<String> synonyms) throws Exception {
		entityExceptionCheck(agentGuid, entityGuid, "entity synonyms modification");
		HashMap<String, Entity> entitiesHm = agentsEntitiesHm.get(agentGuid);
		entitiesHm.get(entityGuid).removeSynonyms(synonyms);
	}

	public void removeEntitySynonym(String agentGuid, String entityGuid, String synonym) throws Exception {
		entityExceptionCheck(agentGuid, entityGuid, "entity synonyms modification");
		HashMap<String, Entity> entitiesHm = agentsEntitiesHm.get(agentGuid);
		entitiesHm.get(entityGuid).removeSynonym(synonym);
	}

	public ArrayList<String> getEntities(String agentGuid) throws Exception {
		agentExceptionCheck(agentGuid, "agent entities retrieval");
		ArrayList<String> entityGuids;

		HashMap<String, Entity> entitiesHm = agentsEntitiesHm.get(agentGuid);
		entityGuids = new ArrayList<>(entitiesHm.size());

		for (Entity entity : entitiesHm.values()) {
			entityGuids.add(entity.getGuid());
		}

		return entityGuids;
	}

	public String getEntityName(String agentGuid, String entityGuid) throws Exception {
		entityExceptionCheck(agentGuid, entityGuid, "entity name retrieval");

		Entity entity = agentsEntitiesHm.get(agentGuid).get(entityGuid);

		if (entity == null) {
			throw new Exception("Internal error: entity GUID: " + entityGuid + "  of agent GUID: " + agentGuid
					+ " was found null during entity name retrieval");
		} else {
			return entity.getName();
		}
	}

	public ArrayList<String> getEntitySynonyms(String agentGuid, String entityGuid) throws Exception {
		entityExceptionCheck(agentGuid, entityGuid, "entity synonyms retrieval");

		Entity entity = agentsEntitiesHm.get(agentGuid).get(entityGuid);

		if (entity == null) {
			throw new Exception("Internal error: entity GUID: " + entityGuid + "  of agent GUID: " + agentGuid
					+ " was found null during entity synonyms retrieval");
		} else {
			return entity.getSynonyms();
		}
	}

	/**
	 * good
	 */
	public ArrayList<String> removeEntityFromAgent(String agentGuid, String entityGuid) throws Exception {
		entityExceptionCheck(agentGuid, entityGuid, "agent entity removal");

		ArrayList<String> removedFromPhrases = new ArrayList<>();

		if (agentsIntentsHm.containsKey(agentGuid)) {
			HashMap<String, Intent> intentsHm = agentsIntentsHm.get(agentGuid);
			if (intentsHm != null) {
				for (Intent intent : intentsHm.values()) {
					removedFromPhrases.addAll(removeEntityFromIntent(intent, entityGuid));
				}
			}
		}

		HashMap<String, Entity> entitiesHm = agentsEntitiesHm.get(agentGuid);

		if (entitiesHm != null) {
			entitiesHm.remove(entityGuid);
		}

		return removedFromPhrases;
	}

	/**
	 * good
	 */
	private ArrayList<String> removeEntityFromIntent(Intent intent, String entityGuid) {
		ArrayList<String> removedFromPhrases = new ArrayList<>();
		if (intent != null) {
			removedFromPhrases = removeEntityFromPhrases(intent.getPhrases(), entityGuid);
		}
		return removedFromPhrases;
	}

	/**
	 * good
	 */
	public ArrayList<String> removeEntityFromIntent(String agentGuid, String intentGuid, String entityGuid)
			throws Exception {
		intentExceptionCheck(agentGuid, intentGuid, "intent entity removal");

		ArrayList<String> removedFromPhrases = new ArrayList<>();

		if (entityGuid != null) {
			HashMap<String, Intent> intentsHm = agentsIntentsHm.get(agentGuid);

			Intent intent = intentsHm.get(intentGuid);
			removedFromPhrases = removeEntityFromPhrases(intent.getPhrases(), entityGuid);
		}
		return removedFromPhrases;
	}

	/**
	 * good
	 */
	public ArrayList<String> removeEntityFromIntents(String agentGuid, ArrayList<String> intentGuids, String entityGuid)
			throws Exception {
		ArrayList<String> removedFromPhrases = new ArrayList<>();

		if (intentGuids != null) {
			for (String intentGuid : intentGuids) {
				removedFromPhrases.addAll(removeEntityFromIntent(agentGuid, intentGuid, entityGuid));
			}
		}
		return removedFromPhrases;
	}

	/**
	 * good
	 */
	private String removeEntityFromPhrase(Phrase phrase, String entityGuid) {
		String removedFromPhrase = null;

		while (phrase.phraseRemoveEntityGuid(entityGuid)) {
			removedFromPhrase = phrase.getGuid();
		}
		return removedFromPhrase;
	}

	/**
	 * good
	 */
	private ArrayList<String> removeEntityFromPhrases(ArrayList<Phrase> phrases, String entityGuid) {
		ArrayList<String> removedFromPhrases = new ArrayList<>();

		if (phrases != null) {
			for (Phrase phrase : phrases) {
				String removedFromPhrase = removeEntityFromPhrase(phrase, entityGuid);

				if (removedFromPhrase != null) {
					removedFromPhrases.add(removedFromPhrase);
				}
			}
		}
		return removedFromPhrases;
	}

	/**
	 * good
	 */
	public String removeEntityFromPhrase(String agentGuid, String intentGuid, String phraseGuid, String entityGuid)
			throws Exception {
		phraseExceptionCheck(agentGuid, intentGuid, phraseGuid, "phrase entity removal");

		String removedFromPhrase = null;
		if (entityGuid != null) {
			HashMap<String, Intent> intentsHm = agentsIntentsHm.get(agentGuid);
			Intent intent = intentsHm.get(intentGuid);
			removedFromPhrase = removeEntityFromPhrase(intent.getPhrase(phraseGuid), entityGuid);
		}
		return removedFromPhrase;
	}

	/**
	 * good
	 */
	public ArrayList<String> removeEntityFromPhrases(String agentGuid, String intentGuid, ArrayList<String> phraseGuids,
			String entityGuid) throws Exception {
		ArrayList<String> removedFromPhrases = new ArrayList<>();
		if (phraseGuids != null) {
			for (String phraseGuid : phraseGuids) {
				String removedFromPhrase = removeEntityFromPhrase(agentGuid, intentGuid, phraseGuid, entityGuid);

				if (removedFromPhrase != null) {
					removedFromPhrases.add(removedFromPhrase);
				}
			}
		}
		return removedFromPhrases;
	}

	public String createWord(String agentGuid) throws Exception {
		agentExceptionCheck(agentGuid, "agent word creation");

		Word word = Word.create();
		agentsWordsHm.get(agentGuid).put(word.getGuid(), word);
		return word.getGuid();
	}

	public String createWord(String agentGuid, String wordName) throws Exception {
		agentExceptionCheck(agentGuid, "agent word creation");

		Word word = Word.create(wordName);
		agentsWordsHm.get(agentGuid).put(word.getGuid(), word);
		return word.getGuid();
	}

	public String createWord(String agentGuid, String wordName, String wordDescription) throws Exception {
		agentExceptionCheck(agentGuid, "agent word creation");

		Word word = Word.create(wordName, wordDescription);

		agentsWordsHm.get(agentGuid).put(word.getGuid(), word);
		return word.getGuid();
	}

	public String createWord(String agentGuid, String wordName, String wordDescription, ArrayList<String> synonyms)
			throws Exception {
		agentExceptionCheck(agentGuid, "agent word creation");

		Word word = Word.create(wordName, wordDescription, synonyms);

		agentsWordsHm.get(agentGuid).put(word.getGuid(), word);
		return word.getGuid();
	}

	public ArrayList<String> createWords(String agentGuid,
			ArrayList<Pair<String, Pair<String, ArrayList<String>>>> wordsAttributes) throws Exception {
		agentExceptionCheck(agentGuid, "agent word creation");
		ArrayList<String> wordGuids = new ArrayList<>();
		if (wordsAttributes != null) {
			for (Pair<String, Pair<String, ArrayList<String>>> wordAttributes : wordsAttributes) {
				if (wordAttributes != null) {
					String wordName = wordAttributes.getKey();
					Pair<String, ArrayList<String>> wordDescriptionSynonyms = wordAttributes.getValue();

					if (wordDescriptionSynonyms == null) {
						wordGuids.add(createWord(agentGuid, wordName));
					} else {
						wordGuids.add(createWord(agentGuid, wordName, wordDescriptionSynonyms.getKey(),
								wordDescriptionSynonyms.getValue()));
					}
				}
			}
		}
		return wordGuids;
	}

	public void modifyWordNameDescription(String agentGuid, String wordGuid, String name, String description)
			throws Exception {
		modifyWordName(agentGuid, wordGuid, name);
		modifyWordDescription(agentGuid, wordGuid, description);
	}

	public void modifyWordName(String agentGuid, String wordGuid, String name) throws Exception {
		wordExceptionCheck(agentGuid, wordGuid, "word name modification");
		HashMap<String, Word> wordsHm = agentsWordsHm.get(agentGuid);
		wordsHm.get(wordGuid).setName(name);
	}

	public void modifyWordDescription(String agentGuid, String wordGuid, String description) throws Exception {
		wordExceptionCheck(agentGuid, wordGuid, "word description modification");
		HashMap<String, Word> wordsHm = agentsWordsHm.get(agentGuid);
		wordsHm.get(wordGuid).setDescription(description);
	}

	public void modifyWordSynonym(String agentGuid, String wordGuid, String from, String to) throws Exception {
		wordExceptionCheck(agentGuid, wordGuid, "word synonym modification");
		HashMap<String, Word> wordsHm = agentsWordsHm.get(agentGuid);
		wordsHm.get(wordGuid).modifySynonym(from, to);
	}

	public void setWordSynonyms(String agentGuid, String wordGuid, ArrayList<String> synonyms) throws Exception {
		wordExceptionCheck(agentGuid, wordGuid, "word synonyms modification");
		HashMap<String, Word> wordsHm = agentsWordsHm.get(agentGuid);
		wordsHm.get(wordGuid).setSynonyms(synonyms);
	}

	public void addWordSynonyms(String agentGuid, String wordGuid, ArrayList<String> synonyms) throws Exception {
		wordExceptionCheck(agentGuid, wordGuid, "word synonyms modification");
		HashMap<String, Word> wordsHm = agentsWordsHm.get(agentGuid);
		wordsHm.get(wordGuid).addSynonyms(synonyms);
	}

	public void addWordSynonym(String agentGuid, String wordGuid, String synonym) throws Exception {
		wordExceptionCheck(agentGuid, wordGuid, "word synonyms modification");
		HashMap<String, Word> wordsHm = agentsWordsHm.get(agentGuid);
		wordsHm.get(wordGuid).addSynonym(synonym);
	}

	public void removeWordSynonyms(String agentGuid, String wordGuid, ArrayList<String> synonyms) throws Exception {
		wordExceptionCheck(agentGuid, wordGuid, "word synonyms modification");
		HashMap<String, Word> wordsHm = agentsWordsHm.get(agentGuid);
		wordsHm.get(wordGuid).removeSynonyms(synonyms);
	}

	public void removeWordSynonym(String agentGuid, String wordGuid, String synonym) throws Exception {
		wordExceptionCheck(agentGuid, wordGuid, "word synonyms modification");
		HashMap<String, Word> wordsHm = agentsWordsHm.get(agentGuid);
		wordsHm.get(wordGuid).removeSynonym(synonym);
	}

	public ArrayList<String> getWords(String agentGuid) throws Exception {
		agentExceptionCheck(agentGuid, "agent words retrieval");
		ArrayList<String> wordGuids;

		HashMap<String, Word> wordsHm = agentsWordsHm.get(agentGuid);
		wordGuids = new ArrayList<>(wordsHm.size());

		for (Word word : wordsHm.values()) {
			wordGuids.add(word.getGuid());
		}

		return wordGuids;
	}

	public String getWordName(String agentGuid, String wordGuid) throws Exception {
		wordExceptionCheck(agentGuid, wordGuid, "word name retrieval");

		Word word = agentsWordsHm.get(agentGuid).get(wordGuid);

		if (word == null) {
			throw new Exception("Internal error: word GUID: " + wordGuid + "  of agent GUID: " + agentGuid
					+ " was found null during word name retrieval");
		} else {
			return word.getName();
		}
	}

	public ArrayList<String> getWordSynonyms(String agentGuid, String wordGuid) throws Exception {
		wordExceptionCheck(agentGuid, wordGuid, "word synonyms retrieval");

		Word word = agentsWordsHm.get(agentGuid).get(wordGuid);

		if (word == null) {
			throw new Exception("Internal error: word GUID: " + wordGuid + "  of agent GUID: " + agentGuid
					+ " was found null during word synonyms retrieval");
		} else {
			return word.getSynonyms();
		}
	}

	/**
	 * good
	 */
	public ArrayList<String> removeWordFromAgent(String agentGuid, String wordGuid) throws Exception {
		wordExceptionCheck(agentGuid, wordGuid, "agent word removal");

		ArrayList<String> removedFromPhrases = new ArrayList<>();

		if (agentsIntentsHm.containsKey(agentGuid)) {
			HashMap<String, Intent> intentsHm = agentsIntentsHm.get(agentGuid);

			if (intentsHm != null) {
				for (Intent intent : intentsHm.values()) {
					removedFromPhrases.addAll(removeWordFromIntent(intent, wordGuid));
				}
			}
		}

		HashMap<String, Word> wordsHm = agentsWordsHm.get(agentGuid);

		if (wordsHm != null) {
			wordsHm.remove(wordGuid);
		}

		return removedFromPhrases;
	}

	/**
	 * good
	 */
	private ArrayList<String> removeWordFromIntent(Intent intent, String wordGuid) {
		ArrayList<String> removedFromPhrases = new ArrayList<>();
		if (intent != null) {
			removedFromPhrases = removeWordFromPhrases(intent.getPhrases(), wordGuid);
		}
		return removedFromPhrases;
	}

	/**
	 * good
	 */
	public ArrayList<String> removeWordFromIntent(String agentGuid, String intentGuid, String wordGuid)
			throws Exception {
		intentExceptionCheck(agentGuid, intentGuid, "intent word removal");

		ArrayList<String> removedFromPhrases = new ArrayList<>();

		if (wordGuid != null) {
			HashMap<String, Intent> intentsHm = agentsIntentsHm.get(agentGuid);

			Intent intent = intentsHm.get(intentGuid);
			removedFromPhrases = removeWordFromPhrases(intent.getPhrases(), wordGuid);
		}
		return removedFromPhrases;
	}

	/**
	 * good
	 */
	public ArrayList<String> removeWordFromIntents(String agentGuid, ArrayList<String> intentGuids, String wordGuid)
			throws Exception {
		ArrayList<String> removedFromPhrases = new ArrayList<>();

		if (intentGuids != null) {
			for (String intentGuid : intentGuids) {
				removedFromPhrases.addAll(removeWordFromIntent(agentGuid, intentGuid, wordGuid));
			}
		}
		return removedFromPhrases;
	}

	/**
	 * good
	 */
	private String removeWordFromPhrase(Phrase phrase, String wordGuid) {
		String removedFromPhrase = null;

		while (phrase.phraseRemoveWordGuid(wordGuid)) {
			removedFromPhrase = phrase.getGuid();
		}
		return removedFromPhrase;
	}

	/**
	 * good
	 */
	private ArrayList<String> removeWordFromPhrases(ArrayList<Phrase> phrases, String wordGuid) {
		ArrayList<String> removedFromPhrases = new ArrayList<>();

		if (phrases != null) {
			for (Phrase phrase : phrases) {
				String removedFromPhrase = removeWordFromPhrase(phrase, wordGuid);

				if (removedFromPhrase != null) {
					removedFromPhrases.add(removedFromPhrase);
				}
			}
		}
		return removedFromPhrases;
	}

	/**
	 * good
	 */
	public String removeWordFromPhrase(String agentGuid, String intentGuid, String phraseGuid, String wordGuid)
			throws Exception {
		phraseExceptionCheck(agentGuid, intentGuid, phraseGuid, "phrase word removal");

		String removedFromPhrase = null;
		if (wordGuid != null) {
			HashMap<String, Intent> intentsHm = agentsIntentsHm.get(agentGuid);
			Intent intent = intentsHm.get(intentGuid);
			removedFromPhrase = removeWordFromPhrase(intent.getPhrase(phraseGuid), wordGuid);
		}
		return removedFromPhrase;
	}

	/**
	 * good
	 */
	public ArrayList<String> removeWordFromPhrases(String agentGuid, String intentGuid, ArrayList<String> phraseGuids,
			String wordGuid) throws Exception {
		ArrayList<String> removedFromPhrases = new ArrayList<>();
		if (phraseGuids != null) {
			for (String phraseGuid : phraseGuids) {
				String removedFromPhrase = removeWordFromPhrase(agentGuid, intentGuid, phraseGuid, wordGuid);

				if (removedFromPhrase != null) {
					removedFromPhrases.add(removedFromPhrase);
				}
			}
		}
		return removedFromPhrases;
	}

	/**
	 * good
	 */
	public ArrayList<String> removeWordsFromPhrase(String agentGuid, String intentGuid, String phraseGuid,
			ArrayList<String> wordGuids) throws Exception {
		ArrayList<String> removedFromPhrases = new ArrayList<>();
		if (wordGuids != null) {
			for (String wordGuid : wordGuids) {
				String removedFromPhrase = removeWordFromPhrase(agentGuid, intentGuid, phraseGuid, wordGuid);

				if (removedFromPhrase != null) {
					removedFromPhrases.add(removedFromPhrase);
				}
			}
		}
		return removedFromPhrases;
	}

	private void agentExceptionCheck(String agentGuid, String errMessage) throws Exception {
		if (!isAgentFound(agentGuid)) {
			throw new Exception("'Agent' does not exist for '" + errMessage + "'.");
		}
	}

	private void entityExceptionCheck(String agentGuid, String entityGuid, String errMessage) throws Exception {
		agentExceptionCheck(agentGuid, errMessage);
		HashMap<String, Entity> entitiesHm = agentsEntitiesHm.get(agentGuid);

		if (entityGuid == null || !entitiesHm.containsKey(entityGuid)) {
			throw new Exception("'Entity' does not exist for '" + errMessage + "'.");
		}
	}

	private void wordExceptionCheck(String agentGuid, String wordGuid, String errMessage) throws Exception {
		agentExceptionCheck(agentGuid, errMessage);
		HashMap<String, Word> wordsHm = agentsWordsHm.get(agentGuid);

		if (wordGuid == null || !wordsHm.containsKey(wordGuid)) {
			throw new Exception("'Word' does not exist for '" + errMessage + "'.");
		}
	}

	private void intentExceptionCheck(String agentGuid, String intentGuid, String errMessage) throws Exception {
		agentExceptionCheck(agentGuid, errMessage);
		HashMap<String, Intent> intentsHm = agentsIntentsHm.get(agentGuid);

		if (intentGuid == null || !intentsHm.containsKey(intentGuid)) {
			throw new Exception("'Intent' does not exist for '" + errMessage + "'.");
		}
	}

	private void phraseExceptionCheck(String agentGuid, String phraseGuid, String errMessage) throws Exception {
		agentExceptionCheck(agentGuid, errMessage);
		HashMap<String, Phrase> phrasesHm = agentsPhrasesHm.get(agentGuid);

		if (phraseGuid == null || !phrasesHm.containsKey(phraseGuid)) {
			throw new Exception("'Phrase' does not exist for '" + errMessage + "'.");
		}
	}

	private void phraseExceptionCheck(String agentGuid, String intentGuid, String phraseGuid, String errMessage)
			throws Exception {
		intentExceptionCheck(agentGuid, intentGuid, errMessage);
		HashMap<String, Intent> intentsHm = agentsIntentsHm.get(agentGuid);
		Intent intent = intentsHm.get(intentGuid);

		if (phraseGuid == null || intent.getPhrase(phraseGuid) == null) {
			throw new Exception("'Phrase' with GUID: " + phraseGuid + " does not exist for " + errMessage + ".");
		}
	}
}
