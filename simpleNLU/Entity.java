package personal.project.simpleNLU;

import java.util.ArrayList;

public class Entity extends WordFragment {

	private Entity() {
		super();
	}

	private Entity(String name) {
		super(name);
	}

	private Entity(String name, String description) {
		super(name, description);
	}

	private Entity(String name, ArrayList<String> synonyms) {
		super(name, synonyms);
	}

	private Entity(String name, String description, ArrayList<String> synonyms) {
		super(name, description, synonyms);
	}

	public static Entity create() {
		return new Entity();
	}

	public static Entity create(String name) {
		return new Entity(name);
	}

	public static Entity create(String name, String description) {
		return new Entity(name, description);
	}

	public static Entity create(String name, ArrayList<String> synonyms) {
		if (synonyms == null || synonyms.size() == 0) {
			return create(name);
		}

		return new Entity(name, synonyms);
	}

	public static Entity create(String name, String description, ArrayList<String> synonyms) {
		if (synonyms == null || synonyms.size() == 0) {
			return create(name);
		}

		return new Entity(name, description, synonyms);
	}
}
