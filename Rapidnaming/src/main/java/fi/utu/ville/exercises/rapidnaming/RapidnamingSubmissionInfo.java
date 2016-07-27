package fi.utu.ville.exercises.rapidnaming;

import edu.vserver.exercises.math.AbstractMathSubmissionInfo;

public class RapidnamingSubmissionInfo extends AbstractMathSubmissionInfo<RapidnamingProblem> {

	private static final long serialVersionUID = 8702870727095225372L;

	private final String answer;

	public RapidnamingSubmissionInfo(String answer) {
		this.answer = answer;
	}

	public String getAnswer() {
		return answer;
	}

}
