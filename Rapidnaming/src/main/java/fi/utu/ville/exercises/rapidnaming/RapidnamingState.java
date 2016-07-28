package fi.utu.ville.exercises.rapidnaming;

import java.util.Random;

import edu.vserver.exercises.math.essentials.layout.AbstractMathState;
import fi.utu.ville.standardutils.Localizer;

public class RapidnamingState extends AbstractMathState<RapidnamingData, RapidnamingProblem> {

	private static final long serialVersionUID = -8617477584787810586L;

	private RapidnamingData data;

	private int count = 0;

	public RapidnamingState(RapidnamingData data, Localizer localizer) {
		super(data, localizer);
	}

	@Override
	protected int loadDataAndGetAmount(RapidnamingData data) {
		this.data = data;
		return data.getAmount();
	}

	@Override
	protected RapidnamingProblem createProblem() {

		if (data.getMode() == RapidnamingMode.WORDS) {

			if (data.getSequence().length < count) {
				count = 0;
			}

			String answer = "";

			if (data.getIsolla() == true) {
				answer = data.getSequence()[count];
			} else if (data.getIsolla() == false) {
				answer = data.getSequence()[count].toLowerCase();
			}
			count++;

			RapidnamingProblem problem1 = new RapidnamingProblem(answer, null);
			RapidnamingAnswer correctAnswer = new RapidnamingAnswer(answer);
			problem1.setCorrectAnswer(correctAnswer);
			return problem1;

		} else if (data.getMode() == RapidnamingMode.PICTURES) {

			Random r = new Random();
			int kys = r.nextInt(data.getPicturesSize());

			RapidnamingDatahelp help = data.getPictures(kys);

			String answer = help.getAnswer();

			RapidnamingProblem problem = new RapidnamingProblem(answer, help);
			RapidnamingAnswer correctAnswer = new RapidnamingAnswer(answer);
			problem.setCorrectAnswer(correctAnswer);

			return problem;

		} else {
			return null;
		}

	}

}
