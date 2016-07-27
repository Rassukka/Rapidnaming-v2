package fi.utu.ville.exercises.rapidnaming;

import java.util.ArrayList;

import fi.utu.ville.exercises.model.ExerciseData;

public class RapidnamingData implements ExerciseData {

	private static final long serialVersionUID = 7859129767103173914L;

	private int numberOfQuestions;
	private int timeShown;
	private RapidnamingMode rapidnamingMode;
	private Boolean isolla;

	private String[] words;
	private String[] sequence;

	private ArrayList<RapidnamingDatahelp> pictures;

	// sanoille
	public RapidnamingData(int questions, int timeShown, String[] words, boolean isolla, RapidnamingMode rapidnamingMode) {
		this.numberOfQuestions = questions;
		this.timeShown = timeShown;
		this.words = words;
		this.sequence = getSequence(words);
		this.isolla = isolla;
		this.rapidnamingMode = rapidnamingMode;
	}

	// kuville
	public RapidnamingData(int questions, int timeShown, RapidnamingMode rapidnamingMode, ArrayList<RapidnamingDatahelp> pictures) {
		this.numberOfQuestions = questions;
		this.timeShown = timeShown;
		this.rapidnamingMode = rapidnamingMode;
		this.pictures = pictures;
	}

	public int getPicturesSize() {
		return pictures.size();
	}

	public int getAmount() {
		return numberOfQuestions;
	}

	public boolean giveAnswersAlwaysAsNegative() {
		return false;
	}

	public int getTimeShown() {
		return timeShown;
	}

	public void setTimeShown(int timeShown) {
		this.timeShown = timeShown;
	}

	public Boolean getIsolla() {
		return isolla;
	}

	public void setIsolla(Boolean isolla) {
		this.isolla = isolla;
	}

	public String[] getWords() {
		return words;
	}

	public void setWords(String[] words) {
		this.words = words;
	}

	private String[] getSequence(String[] words) {
		int numero = words.length;
		for (int i = 0; i < numero; i++) {
			int random = i + (int) (Math.random() * (numero - i));
			String randomElement = words[random];
			words[random] = words[i];
			words[i] = randomElement;
		}
		return words;
	}

	public String[] getSequence() {
		return sequence;
	}

	public RapidnamingMode getMode() {
		return rapidnamingMode;
	}

	public void setMode(RapidnamingMode rapidnamingMode) {
		this.rapidnamingMode = rapidnamingMode;
	}

	public RapidnamingDatahelp getPictures(int index) {
		return pictures.get(index);
	}

}
