package personal.project.simpleNLU;

public class Agent extends Component {
	private String name;

	private Agent() {
		super();
		setName("");
	}

	private Agent(String name) {
		super();
		setName(name);
	}

	private Agent(String name, String description) {
		super(description);
		setName(name);
	}

	public static Agent create() {
		return new Agent();
	}

	public static Agent create(String name) {
		return new Agent(name);
	}

	public static Agent create(String name, String description) {
		return new Agent(name, description);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null) {
			this.name = "";
		} else {
			this.name = name;
		}
	}
}
