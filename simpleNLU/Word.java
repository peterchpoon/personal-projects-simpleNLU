package personal.project.simpleNLU;

import java.util.ArrayList;

public class Word extends WordFragment {

	private Word() {
		super();
	}

	private Word(String name) {
		super(name);
	}

	private Word(String name, String description) {
		super(name, description);
	}

	private Word(String name, ArrayList<String> synonyms) {
		super(name, synonyms);
	}

	private Word(String name, String description, ArrayList<String> synonyms) {
		super(name, description, synonyms);
	}

	public static Word create() {
		return new Word();
	}

	public static Word create(String name) {
		return new Word(name);
	}

	public static Word create(String name, String description) {
		return new Word(name, description);
	}

	public static Word create(String name, ArrayList<String> synonyms) {
		if (synonyms == null || synonyms.size() == 0) {
			return create(name);
		}

		return new Word(name, synonyms);
	}

	public static Word create(String name, String description, ArrayList<String> synonyms) {
		if (synonyms == null || synonyms.size() == 0) {
			return create(name);
		}

		return new Word(name, description, synonyms);
	}
}
