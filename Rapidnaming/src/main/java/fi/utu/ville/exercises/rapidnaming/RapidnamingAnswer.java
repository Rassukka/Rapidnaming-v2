package fi.utu.ville.exercises.rapidnaming;

import edu.vserver.exercises.math.essentials.layout.AbstractMathAnswer;

public class RapidnamingAnswer extends AbstractMathAnswer {

	private static final long serialVersionUID = 1L;

	private final String answer;

	public RapidnamingAnswer(String answer) {
		this.answer = answer;
	}

	public String getAnswer() {

		return answer;
	}

}