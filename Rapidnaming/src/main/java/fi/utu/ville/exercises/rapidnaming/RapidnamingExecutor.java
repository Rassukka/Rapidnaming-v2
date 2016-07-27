package fi.utu.ville.exercises.rapidnaming;

import java.util.ArrayList;

import edu.vserver.exercises.math.essentials.layout.AbstractMathExecutor;
import edu.vserver.misconception.MisconceptionTypeData;
import fi.utu.ville.standardutils.Localizer;

public class RapidnamingExecutor extends AbstractMathExecutor<RapidnamingData, RapidnamingSubmissionInfo, RapidnamingProblem> {

	private static final long serialVersionUID = -6563707901988219539L;
	private RapidnamingState exState;
	private RapidnamingView view;

	@Override
	protected RapidnamingState getMathState() {
		return exState;
	}

	@Override
	protected RapidnamingView getMathView() {
		return view;
	}

	@Override
	protected RapidnamingSubmissionInfo newSubmissionInfo() {
		return new RapidnamingSubmissionInfo("USER ANSWER FROM EXECUTOR");
	}

	@Override
	protected void initStateAndView(RapidnamingData data, Localizer localizer) {
		view = new RapidnamingView(data, localizer);

		exState = new RapidnamingState(data, localizer);
	}

	@Override
	protected ArrayList<MisconceptionTypeData> getMisconceptionTypeData(RapidnamingProblem problem) {
		return null;
	}

}
