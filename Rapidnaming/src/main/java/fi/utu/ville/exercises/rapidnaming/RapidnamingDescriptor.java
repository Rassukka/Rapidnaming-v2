package fi.utu.ville.exercises.rapidnaming;

import com.vaadin.server.Resource;

import edu.vserver.exercises.math.essentials.level.LevelMathDataWrapper;
import edu.vserver.exercises.math.essentials.level.LevelMathExecutorWrapper;
import edu.vserver.exercises.math.essentials.level.LevelMathPersistenceWrapper;
import fi.utu.ville.exercises.model.Editor;
import fi.utu.ville.exercises.model.Executor;
import fi.utu.ville.exercises.model.ExerciseTypeDescriptor;
import fi.utu.ville.exercises.model.PersistenceHandler;
import fi.utu.ville.exercises.model.SubmissionStatisticsGiver;
import fi.utu.ville.exercises.model.SubmissionVisualizer;
import fi.utu.ville.standardutils.Localizer;
import fi.utu.ville.standardutils.StandardIcon;
import fi.utu.ville.standardutils.StandardIcon.IconVariant;

public class RapidnamingDescriptor implements ExerciseTypeDescriptor<LevelMathDataWrapper<RapidnamingData>, RapidnamingSubmissionInfo> {

	private static final long serialVersionUID = -2550301084361603798L;
	public static RapidnamingDescriptor INSTANCE = new RapidnamingDescriptor();

	@Override
	public PersistenceHandler<LevelMathDataWrapper<RapidnamingData>, RapidnamingSubmissionInfo> newExerciseXML() {
		return new LevelMathPersistenceWrapper<RapidnamingData, RapidnamingSubmissionInfo>(RapidnamingData.class, getSubDataClass());
	}

	@Override
	public Executor<LevelMathDataWrapper<RapidnamingData>, RapidnamingSubmissionInfo> newExerciseExecutor() {
		return new LevelMathExecutorWrapper<RapidnamingData, RapidnamingSubmissionInfo>(new RapidnamingExecutor());
	}

	@Override
	public Editor<LevelMathDataWrapper<RapidnamingData>> newExerciseEditor() {
		return new RapidnamingTabbedEditor();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<LevelMathDataWrapper<RapidnamingData>> getTypeDataClass() {
		return (Class<LevelMathDataWrapper<RapidnamingData>>) (Class<?>) LevelMathDataWrapper.class;
	}

	@Override
	public Class<RapidnamingSubmissionInfo> getSubDataClass() {
		return RapidnamingSubmissionInfo.class;
	}

	@Override
	public SubmissionStatisticsGiver<LevelMathDataWrapper<RapidnamingData>, RapidnamingSubmissionInfo> newStatisticsGiver() {
		return new RapidnamingStatisticsGiver();
	}

	@Override
	public SubmissionVisualizer<LevelMathDataWrapper<RapidnamingData>, RapidnamingSubmissionInfo> newSubmissionVisualizer() {
		return new RapidnamingSubmissionVisualizer();
	}

	@Override
	public String getTypeName(Localizer localizer) {
		return "Rapidnaming";
	}

	@Override
	public String getTypeDescription(Localizer localizer) {
		return "A currently generic exercise built on the ViLLE stub.";
	}

	/**
	 * These type icons are for legacy support and shown in very few places. Feel free to make icons of your own. Do bear in mind that these icons are shown in very few places anymore.
	 *
	 * @return An image for this exercise type.
	 */
	@Override
	public Resource getSmallTypeIcon() {
		return null;
	}

	@Override
	public Resource getMediumTypeIcon() {
		return null;
	}

	@Override
	public Resource getLargeTypeIcon() {
		return null;
	}

	@Override
	public boolean isManuallyGraded() {
		return false;
	}

	/**
	 * This icon is shown in the exercise lists everywhere.
	 */
	@Override
	public String getHTMLIcon() {
		return StandardIcon.RawIcon.dribbble.variant(IconVariant.ORANGE);
	}

}
