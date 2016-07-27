package fi.utu.ville.exercises.rapidnaming;

import edu.vserver.exercises.math.essentials.layout.MathExerciseView;
import edu.vserver.exercises.math.essentials.level.LevelMathDataWrapper;
import edu.vserver.math.AbstractMathTableSubmissionViewer;

public class RapidnamingSubmissionVisualizer extends AbstractMathTableSubmissionViewer<LevelMathDataWrapper<RapidnamingData>, RapidnamingSubmissionInfo, RapidnamingProblem> {

	private static final long serialVersionUID = 2187881034704842330L;

	@Override
	protected MathExerciseView<RapidnamingProblem> getExerView(LevelMathDataWrapper<RapidnamingData> exerData, RapidnamingSubmissionInfo submInfo) {
		return new RapidnamingView(exerData.getForLevel(submInfo.getDiffLevel()), getLocalizer());
	}

}