package fi.utu.ville.exercises.rapidnaming;

import java.util.Calendar;

import org.vaadin.kim.countdownclock.CountdownClock;
import org.vaadin.kim.countdownclock.CountdownClock.EndEventListener;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import edu.vserver.exercises.math.essentials.layout.AbstractMathAnswer;
import edu.vserver.exercises.math.essentials.layout.MathExerciseView;
import edu.vserver.exercises.math.essentials.layout.MathLayoutController;
import fi.utu.ville.standardutils.Localizer;
import fi.utu.ville.standardutils.ui.CleanTextField;

public class RapidnamingView extends VerticalLayout implements MathExerciseView<RapidnamingProblem> {

	private static final long serialVersionUID = 4938331703711987006L;

	private final RapidnamingData data;
	private final Localizer localizer;

	private CleanTextField userAnswer;

	private Label correct;
	private Label huom;

	public RapidnamingView(RapidnamingData data, Localizer localizer) {
		this.data = data;
		this.localizer = localizer;

	}

	@Override
	public void drawProblem(RapidnamingProblem problem) {
		this.clearFields();
		this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

		/*
		 * Jos tässävaiheessa painaa enteriä niin koko tehtävä menee sekaisin, en saanut laitettua
		 * "button" elementtiin shortcuttia, enkä sitä pois tehtävän seuraava kysymys painikkeesta.
		 */
		if (data.getMode() == RapidnamingMode.WORDS) {

			Button button = new Button("Aloita tehtävä");
			button.addClickListener(e -> {
				this.removeComponent(button);
				CountdownClock clock = new CountdownClock();
				Calendar c = Calendar.getInstance();
				c.add(Calendar.SECOND, 4);
				clock.setDate(c.getTime());
				clock.setFormat("<span style='font: bold 25px Arial; margin: 10px'>" + "Sana näytetään %s sekunnin kuluttua." + "</span>");
				clock.addEndEventListener(new EndEventListener() {
					public void countDownEnded(CountdownClock clock) {
						clearFields();
						showWord(problem);

					}
				});

				this.addComponent(clock);
				margins();
			});

			this.addComponents(button);
			margins();

		} else if (data.getMode() == RapidnamingMode.PICTURES) {

			Button button = new Button("Aloita tehtävä");
			button.addClickListener(e -> {
				this.removeComponent(button);
				CountdownClock clock = new CountdownClock();
				Calendar c = Calendar.getInstance();
				c.add(Calendar.SECOND, 4);
				clock.setDate(c.getTime());
				clock.setFormat("<span style='font: bold 25px Arial; margin: 10px'>" + problem.getHelp().getGuestion() + "<br/> Kuva näytetään %s sekunnin kuluttua." + "</span>");
				clock.addEndEventListener(new EndEventListener() {

					public void countDownEnded(CountdownClock clock) {
						clearFields();
						showImage(problem);

					}

				});

				this.addComponent(clock);
				margins();
			});

			this.addComponents(button);
			margins();
		}
	}

	public void showWord(RapidnamingProblem problem) {

		// Ajoitus ei toimi, esim. 0001ms ja 999ms on sama asia kuin myös 2001 ja 2999, tähän pitää
		// tehdä erilainen ratkaisu esim javascriptin avulla.

		CountdownClock clock = new CountdownClock();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MILLISECOND, data.getTimeShown());
		clock.setDate(c.getTime());

		String answer = getSolution(problem);
		String capitalized = answer.substring(0, 1).toUpperCase() + answer.substring(1);
		clock.setFormat("<span style='font: bold 25px Arial; margin: 10px'>" + capitalized + "</span>");

		clock.addEndEventListener(new EndEventListener() {
			public void countDownEnded(CountdownClock clock) {
				clearFields();
				wordGuestion(problem);
			}
		});

		this.addComponent(clock);
		margins();
	}

	public void wordGuestion(RapidnamingProblem problem) {
		userAnswer = new CleanTextField(localizer.getUIText(RapidnamingUiConstants.ANSWER));
		userAnswer.focus();
		userAnswer.setCaption("Mikä sana oli?");

		this.addComponents(userAnswer);
		margins();
	}

	public void showImage(RapidnamingProblem problem) {

		// Ajoitus ei toimi, esim. 0001ms ja 999ms on sama asia kuin myös 2001 ja 2999, tähän pitää
		// tehdä erilainen ratkaisu esim javascriptin avulla.

		CountdownClock clock = new CountdownClock();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MILLISECOND, data.getTimeShown());
		clock.setDate(c.getTime());

		this.addComponent(problem.getHelp().getPicture());
		clock.setFormat("");

		clock.addEndEventListener(new EndEventListener() {
			public void countDownEnded(CountdownClock clock) {
				clearFields();
				imageGuestion(problem);
			}
		});

		this.addComponent(clock);
		margins();
	}

	public void imageGuestion(RapidnamingProblem problem) {
		userAnswer = new CleanTextField(localizer.getUIText(RapidnamingUiConstants.ANSWER));
		userAnswer.focus();
		userAnswer.setCaption(problem.getHelp().getGuestion());

		this.addComponents(userAnswer);
		margins();
	}

	@Override
	public void showSolution(RapidnamingProblem problem) {

		String answer = getSolution(problem);
		String capitalized = answer.substring(0, 1).toUpperCase() + answer.substring(1);

		userAnswer.setEnabled(false);
		userAnswer.addStyleName("Rapidnaming-disabled");

		if (data.getMode() == RapidnamingMode.WORDS) {
			if (data.getIsolla() == true) {
				if (userAnswer.getValue().equals(answer)) {
					Label oikein = new Label("Oikein!");
					oikein.addStyleName("oikein");
					this.addComponent(oikein);
				} else {
					Label vaarin = new Label("Väärin!");
					vaarin.addStyleName("vaarin");
					this.addComponent(vaarin);
					correct = new Label("Oikea vastaus oli: " + capitalized);
					huom = new Label("Huomaa isot kirjaimet!");
					correct.addStyleName("correctAnswer");
					huom.addStyleName("correctAnswer");
					if (answer.toLowerCase().equals(userAnswer.getValue())) {
						this.addComponents(huom, correct);
					} else {
						this.addComponent(correct);
					}
				}
			} else if (data.getIsolla() == false) {
				if (userAnswer.getValue().toLowerCase().equals(answer)) {
					Label oikein = new Label("Oikein!");
					oikein.addStyleName("oikein");
					this.addComponent(oikein);
				} else {
					Label vaarin = new Label("Väärin!");
					vaarin.addStyleName("vaarin");
					this.addComponent(vaarin);
					correct = new Label("Oikea vastaus oli: " + capitalized);
					huom = new Label("Huomaa isot kirjaimet!");
					correct.addStyleName("correctAnswer");
					huom.addStyleName("correctAnswer");
					this.addComponent(correct);
				}
			}
		} else if (data.getMode() == RapidnamingMode.PICTURES) {
			if (userAnswer.getValue().toLowerCase().equals(answer)) {
				Label oikein = new Label("Oikein!");
				oikein.addStyleName("oikein");
				this.addComponent(oikein);
			} else {
				Label vaarin = new Label("Väärin!");
				vaarin.addStyleName("vaarin");
				this.addComponent(vaarin);
				correct = new Label("Oikea vastaus oli: " + capitalized);
				correct.addStyleName("correctAnswer");
				this.addComponent(correct);
			}
		}
	}

	public String getSolution(RapidnamingProblem problem) {
		String solution = problem.getCorrectAnswer();
		return solution;
	}

	@Override
	public AbstractMathAnswer getAnswer() {

		if (data.getMode() == RapidnamingMode.WORDS) {
			if (data.getIsolla() == true) {
				String answer = userAnswer.getValue().trim();
				return new RapidnamingAnswer(answer);
			} else if (data.getIsolla() == false) {
				String answer1 = userAnswer.getValue().toLowerCase().trim();
				return new RapidnamingAnswer(answer1);
			} else {
				return null;
			}
		} else if (data.getMode() == RapidnamingMode.PICTURES) {
			String answer = userAnswer.getValue().toLowerCase().trim();
			return new RapidnamingAnswer(answer);
		} else {
			return null;
		}
	}

	@Override
	public void lockControls() {
		// This is called when there are no more questions left
	}

	@Override
	public void clearFields() {
		this.removeAllComponents();

	}

	@Override
	public void setLayoutController(MathLayoutController cont) {
		// MathLayoutController gives access to the check button
	}

	private void margins() {
		this.setMargin(true);
		this.setSpacing(true);
	}

}
