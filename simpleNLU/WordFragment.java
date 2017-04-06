package personal.project.simpleNLU;

import java.util.ArrayList;
import java.util.HashMap;

public class WordFragment extends Component {
	private String name;
	private HashMap<String, String> synonymsHm;

	public WordFragment() {
		super();
		setName("");
		synonymsHm = new HashMap<>();
	}

	public WordFragment(String name) {
		super();
		setName(name);
		synonymsHm = new HashMap<>();
	}

	public WordFragment(String name, String description) {
		super();
		setDescription(description);
		synonymsHm = new HashMap<>();
	}

	public WordFragment(String name, ArrayList<String> synonyms) {
		super();
		setName(name);
		setSynonyms(synonyms);
	}

	public WordFragment(String name, String description, ArrayList<String> synonyms) {
		super();
		setName(name);
		setDescription(description);
		setSynonyms(synonyms);
	}

	public void setName(String name) {
		if (name == null) {
			name = "";
		} else {
			this.name = name;
		}
	}

	public String getName() {
		return name;
	}

	public boolean containsSynonym(String synonym) {
		return synonymsHm.containsKey(synonym);
	}

	public void modifySynonym(String from, String to) {
		if (from != null && to != null && synonymsHm.containsKey(from)) {
			synonymsHm.remove(from);
			synonymsHm.put(to, to);
		}
	}

	public void addSynonym(String synonym) {
		if (synonym != null && !synonymsHm.containsKey(synonym)) {
			synonymsHm.put(synonym, synonym);
		}
	}

	public void addSynonyms(ArrayList<String> synonyms) {
		if (synonyms != null) {
			for (String synonym : synonyms) {
				if (!synonymsHm.containsKey(synonym)) {
					synonymsHm.put(synonym, synonym);
				}
			}
		}
	}

	public void removeSynonyms(ArrayList<String> synonyms) {
		if (synonyms != null) {
			for (String synonym : synonyms) {
				synonymsHm.remove(synonym);
			}
		}
	}

	public void removeSynonym(String synonym) {
		if (synonym != null) {
			synonymsHm.remove(synonym);
		}
	}

	public ArrayList<String> getSynonyms() {
		ArrayList<String> retVal = new ArrayList<>(synonymsHm.size());
		for (String synonym : synonymsHm.values()) {
			retVal.add(synonym);
		}
		return retVal;
	}

	public void setSynonyms(ArrayList<String> synonyms) {
		synonymsHm = new HashMap<>();
		addSynonyms(synonyms);
	}
}
