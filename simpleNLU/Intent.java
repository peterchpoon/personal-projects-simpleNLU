package personal.project.simpleNLU;

import java.util.ArrayList;
import java.util.HashMap;

public class Intent extends Component {
	private String name;
	private HashMap<String, Phrase> phrasesHm;

	private Intent() {
		super();
		setName("");
		this.phrasesHm = new HashMap<>();
	}

	private Intent(String name) {
		super();
		setName(name);
		this.phrasesHm = new HashMap<>();
	}

	private Intent(String name, String description) {
		super(description);
		setName(name);
		this.phrasesHm = new HashMap<>();
	}

	private Intent(String name, ArrayList<Phrase> phrases) {
		super();
		setName(name);
		this.phrasesHm = new HashMap<>();
		addPhrases(phrases);
	}

	public static Intent create() {
		return new Intent();
	}

	public static Intent create(String name) {
		return new Intent(name);
	}

	public static Intent create(String name, String description) {
		return new Intent(name, description);
	}

	public static Intent create(String name, ArrayList<Phrase> phrases) {
		if (phrases == null || phrases.size() == 0) {
			return create(name);
		}

		return new Intent(name, phrases);
	}

	public void setName(String name) {
		if (name == null) {
			this.name = "";
		} else {
			this.name = name;
		}
	}

	public String getName() {
		return name;
	}

	public Phrase getPhrase(String phraseGuid) {
		Phrase retVal = null;

		if (phraseGuid != null) {
			retVal = phrasesHm.get(phraseGuid);
		}

		return retVal;
	}

	public void addPhrase(Phrase phrase) {
		if (phrase != null) {
			phrasesHm.put(phrase.getGuid(), phrase);
		}
	}

	public void removePhrase(String phraseGuid) {
		if (phraseGuid != null) {
			phrasesHm.remove(phraseGuid);
		}
	}

	public ArrayList<Phrase> getPhrases() {
		ArrayList<Phrase> retVal = new ArrayList<>(phrasesHm.size());

		for (Phrase phrase : phrasesHm.values()) {
			retVal.add(phrase);
		}

		return retVal;
	}

	public ArrayList<String> getPhraseGuids() {
		ArrayList<String> retVal = new ArrayList<>(phrasesHm.size());

		for (Phrase phrase : phrasesHm.values()) {
			retVal.add(phrase.getGuid());
		}

		return retVal;
	}

	public void addPhrases(ArrayList<Phrase> phrases) {
		if (phrases != null) {
			for (Phrase phrase : phrases) {
				if (!phrasesHm.containsKey(phrase.getGuid())) {
					phrasesHm.put(phrase.getGuid(), phrase);
				}
			}
		}
	}

	public void removePhrases(ArrayList<Phrase> phrases) {
		if (phrases != null) {
			for (Phrase phrase : phrases) {
				phrasesHm.remove(phrase.getGuid());
			}
		}
	}
}
