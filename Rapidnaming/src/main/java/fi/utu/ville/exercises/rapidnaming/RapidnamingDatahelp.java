package fi.utu.ville.exercises.rapidnaming;

import java.io.File;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Image;

public class RapidnamingDatahelp {

	private int pictureNumber;
	private String guestion;
	private String answer;

	public RapidnamingDatahelp(int pictureNumber, String guestion, String answer) {
		this.pictureNumber = pictureNumber;
		this.guestion = guestion;
		this.answer = answer;
	}

	public String getGuestion() {
		return guestion;
	}

	public void setGuestion(String guestion) {
		this.guestion = guestion;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getPictureNumber() {
		return pictureNumber;
	}

	public void setPictureNumber(int pictureNumber) {
		this.pictureNumber = pictureNumber;
	}

	public Image getPicture() {
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		return new Image("", new FileResource(new File(basepath + "/WEB-INF/images/kuva" + pictureNumber + ".png")));
	}

	public String getImageUrd() {
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		return basepath + "/WEB-INF/images/kuva" + pictureNumber + ".png";
	}

}
