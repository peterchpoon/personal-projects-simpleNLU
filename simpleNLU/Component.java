package personal.project.simpleNLU;

import java.util.UUID;

public class Component {
	private String guid;
	private String description;
	
	public Component(){
		setGuid();
		setDescription("");
	}
	
	public Component(String description){
		setGuid();
		setDescription(description);
	}
	
	public String getGuid(){
		return guid;
	}
	
	private void setGuid(){
		guid = UUID.randomUUID().toString();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if(description == null){
			description = "";
		}else{
			this.description = description;
		}		
	}
}
