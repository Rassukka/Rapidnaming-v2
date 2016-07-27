package fi.utu.ville.exercises.rapidnaming;

import com.vaadin.ui.VerticalLayout;

import edu.vserver.exercises.math.essentials.level.DiffLevel;
import edu.vserver.exercises.math.essentials.level.LevelMathDataWrapper;
import edu.vserver.exercises.math.essentials.level.LevelMathExecutorWrapper;
import edu.vserver.math.MathEditorHelp;
import edu.vserver.math.MathTabbedEditor;
import edu.vserver.math.MathTabbedEditorWrap;
import fi.utu.ville.standardutils.Localizer;
import fi.utu.ville.standardutils.UIConstants;

public class RapidnamingTabbedEditor extends MathTabbedEditor<RapidnamingData> {

	private static final long serialVersionUID = 2841920972594896399L;
	private RapidnamingEditor easyEditor;
	private RapidnamingEditor normalEditor;
	private RapidnamingEditor hardEditor;
	private Localizer localizer;
	private static String help;

	public RapidnamingTabbedEditor() {

	}

	@Override
	protected VerticalLayout getHelper() {

		String left = localizer.getUIText(UIConstants.HELP_CALCROW_LEFT);
		String center = localizer.getUIText(UIConstants.HELP_CALCROW_CENTER);
		String right = localizer.getUIText(UIConstants.HELP_CALCROW_RIGHT);

		return new MathEditorHelp(localizer.getUIText(UIConstants.CALC_ROW), help, left, center, right);
	}

	@Override
	protected MathTabbedEditorWrap<RapidnamingData> getEasyEditor() {
		return easyEditor;
	}

	@Override
	protected MathTabbedEditorWrap<RapidnamingData> getNormalEditor() {
		return normalEditor;
	}

	@Override
	protected MathTabbedEditorWrap<RapidnamingData> getHardEditor() {
		return hardEditor;
	}

	@Override
	protected LevelMathExecutorWrapper<RapidnamingData, RapidnamingSubmissionInfo> getExecutor() {
		return new LevelMathExecutorWrapper<RapidnamingData, RapidnamingSubmissionInfo>(new RapidnamingExecutor());
	}

	@Override
	protected void typeInitialize(LevelMathDataWrapper<RapidnamingData> oldData) {
		this.localizer = getLocalizer();

		if (oldData == null) {

			RapidnamingData easy = new RapidnamingData(5, 1500, getDefaultWords(), false, RapidnamingMode.WORDS);
			RapidnamingData normal = new RapidnamingData(10, 1500, getDefaultWords(), false, RapidnamingMode.WORDS);
			RapidnamingData hard = new RapidnamingData(15, 1500, getDefaultWords(), false, RapidnamingMode.WORDS);

			this.easyEditor = new RapidnamingEditor(easy, getLocalizer(), new VerticalLayout(), RapidnamingMode.WORDS);
			this.normalEditor = new RapidnamingEditor(normal, getLocalizer(), new VerticalLayout(), RapidnamingMode.WORDS);
			this.hardEditor = new RapidnamingEditor(hard, getLocalizer(), new VerticalLayout(), RapidnamingMode.WORDS);
		} else {
			this.easyEditor = new RapidnamingEditor(oldData.getForLevel(DiffLevel.EASY), getLocalizer(), new VerticalLayout(), RapidnamingMode.WORDS);
			this.normalEditor = new RapidnamingEditor(oldData.getForLevel(DiffLevel.NORMAL), getLocalizer(), new VerticalLayout(), RapidnamingMode.WORDS);
			this.hardEditor = new RapidnamingEditor(oldData.getForLevel(DiffLevel.HARD), getLocalizer(), new VerticalLayout(), RapidnamingMode.WORDS);
		}

		help = localizer.getUIText(UIConstants.HELP_CALCROW);
		drawEditor();

	}

	@Override
	public void doLayout() {
		drawEditor();

	}

	@Override
	public boolean isOkToExit() {
		return true;
	}

	@Override
	public String getViewName() {
		return "RapidnamingEditor";
	}

	private String[] getDefaultWords() {
		String[] words = new String[] { "pulpetti" + "kynä" + "kirja" + "penaali" + "tietokone" + "valkotaulu" + "tuoli" + "opettaja" + "oppilas" + "liitutaulu" + "pyyhekumi" + "pensseli" + "taideteokset" + "sakset" + "kynälaatikko" + "liitu" + "permanenttitussi" + "hiiri" + "näppäimistö" + "oppitunti" };
		return words;
	}

}
