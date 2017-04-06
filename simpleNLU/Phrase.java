package personal.project.simpleNLU;

import java.util.ArrayList;

public class Phrase extends Component {
	private ArrayList<WordFragment> wordFragments;

	private Phrase() {
		super();
		this.wordFragments = new ArrayList<>();
	}

	private Phrase(ArrayList<WordFragment> wordFragments) {
		super();
		this.wordFragments = new ArrayList<>();
		setWordFragments(wordFragments);
	}

	public static Phrase create() {
		return new Phrase();
	}

	public static Phrase create(ArrayList<WordFragment> wordFragments) {
		if (wordFragments == null || wordFragments.size() == 0) {
			return create();
		}

		return new Phrase(wordFragments);
	}

	public void setWordFragments(ArrayList<WordFragment> wordFragments) {
		if (wordFragments != null) {
			this.wordFragments = new ArrayList<>(wordFragments.size());
			for (WordFragment wordFragment : wordFragments) {
				if (wordFragment != null) {
					this.wordFragments.add(wordFragment);
				}
			}
		}
	}

	public ArrayList<WordFragment> getWordFragments() {
		ArrayList<WordFragment> retVal = new ArrayList<>(wordFragments.size());
		for (WordFragment wordFragment : wordFragments) {
			retVal.add(wordFragment);
		}
		return retVal;
	}

	public ArrayList<String> getEntities() {
		ArrayList<String> retVal = new ArrayList<>();
		for (WordFragment wordFragment : wordFragments) {
			if (wordFragment instanceof Entity) {
				retVal.add(wordFragment.getGuid());
			}
		}
		return retVal;
	}

	public boolean phraseContainsEntityName(String name) {
		if (name != null) {
			for (WordFragment wordFragment : wordFragments) {
				if (wordFragment instanceof Entity && ((Entity) wordFragment).getName().equals(name)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean phraseContainsEntityGuid(String guid) {
		if (guid != null) {
			for (WordFragment wordFragment : wordFragments) {
				if (wordFragment instanceof Entity && ((Entity) wordFragment).getGuid().equals(guid)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean phraseContainsEntitySynonym(String synonym) {
		if (synonym != null) {
			for (WordFragment wordFragment : wordFragments) {
				if (wordFragment instanceof Entity) {
					if (containsSynonym(((Entity) wordFragment).getSynonyms(), synonym)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean phraseContainsWordSynonym(String synonym) {
		if (synonym != null) {
			for (WordFragment wordFragment : wordFragments) {
				if (wordFragment instanceof Word) {
					if (containsSynonym(((Word) wordFragment).getSynonyms(), synonym)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean phraseContainsWordGuid(String guid) {
		if (guid != null) {
			for (WordFragment wordFragment : wordFragments) {
				if (wordFragment instanceof Word && ((Word) wordFragment).getGuid().equals(guid)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean phraseContainsEitherWordOrEntitySynonym(String synonym) {
		if (synonym != null) {
			for (WordFragment wordFragment : wordFragments) {
				if (wordFragment instanceof Word) {
					if (containsSynonym(((Word) wordFragment).getSynonyms(), synonym)) {
						return true;
					}
				} else if (wordFragment instanceof Entity) {
					if (containsSynonym(((Entity) wordFragment).getSynonyms(), synonym)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean containsSynonym(ArrayList<String> synonyms, String synonym) {
		for (String s : synonyms) {
			if (s.equals(synonym)) {
				return true;
			}
		}
		return false;
	}

	public boolean phraseRemoveEntityGuid(String guid) {
		if (guid != null) {
			for (int i = 0; i < wordFragments.size(); i++) {
				WordFragment wordFragment = wordFragments.get(i);
				if (wordFragment instanceof Entity && ((Entity) wordFragment).getGuid().equals(guid)) {
					wordFragments.remove(i);
					return true;
				}
			}
		}
		return false;
	}

	public boolean phraseRemoveWordGuid(String guid) {
		if (guid != null) {
			for (int i = 0; i < wordFragments.size(); i++) {
				WordFragment wordFragment = wordFragments.get(i);
				if (wordFragment instanceof Entity && ((Word) wordFragment).getGuid().equals(guid)) {
					wordFragments.remove(i);
					return true;
				}
			}
		}
		return false;
	}
}
