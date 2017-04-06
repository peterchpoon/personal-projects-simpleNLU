package personal.project.simpleNLU;

public class Synonym{
	private String synonym;

	private Synonym(){
		setSynonym("");
	}
	
	private Synonym(String synonym){
		setSynonym(synonym);
	}

	public static Synonym create(){
		return new Synonym();
	}
	
	public static Synonym create(String synonym){
		return new Synonym(synonym);
	}

	public String getSynonym() {
		return synonym;
	}
	
	public void setSynonym(String synonym){
		if(synonym == null){
			synonym = "";
		}else{
			this.synonym = synonym;
		}
	}	
}
