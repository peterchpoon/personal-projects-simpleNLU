package personal.project.simpleNLU;

import java.util.ArrayList;

public interface Recognizer {
	public ArrayList<UtteranceEvaluation> evaluate(String utterance) throws Exception;
}
