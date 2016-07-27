package fi.utu.ville.exercises.rapidnaming;

import java.io.File;
import java.util.ArrayList;

import org.vaadin.hene.expandingtextarea.ExpandingTextArea;
import org.vaadin.hene.expandingtextarea.ExpandingTextArea.RowsChangeEvent;
import org.vaadin.hene.expandingtextarea.ExpandingTextArea.RowsChangeListener;

import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import edu.vserver.math.MathTabbedEditorWrap;
import eu.michaelvogt.vaadin.attribute.Attribute;
import fi.utu.ville.standardutils.Localizer;
import fi.utu.ville.standardutils.ui.IntegerField;

public class RapidnamingEditor implements MathTabbedEditorWrap<RapidnamingData> {

	private static final long serialVersionUID = 1L;

	private OptionGroup valinta;

	// How many questions are shown to the user. Mathlayout can show max 20.
	private IntegerField numberOfExercises;

	private IntegerField timeShown;

	private ExpandingTextArea words;

	private CheckBox isolla;

	final RapidnamingData oldData;

	private VerticalLayout view;

	private Table table;

	private RapidnamingMode rapidnamingMode;

	private final Localizer localizer;

	public RapidnamingEditor(RapidnamingData oldData, Localizer localizer, VerticalLayout view, RapidnamingMode rapidnamingMode) {
		this.localizer = localizer;
		this.oldData = oldData;
		this.view = new VerticalLayout();
		this.rapidnamingMode = rapidnamingMode;
	}

	@Override
	public VerticalLayout drawSettings() {

		valinta = new OptionGroup("Näytetäänkö oppilaalle sanoja vai kuvia?");
		valinta.addItems("Sanat", "Kuvat");
		valinta.select("Sanat");

		view.addComponent(valinta);
		view.setMargin(true);
		view.setSpacing(true);

		numberOfExercises = new IntegerField("Kysymysten määrä: (max 20)", 20);
		numberOfExercises.setValue(5);
		numberOfExercises.setWidth("40px");

		timeShown = new IntegerField("Aika joka kysymykset näytetään: (millisekunneissa)", 10);
		timeShown.setValue(1500);
		timeShown.setWidth("40px");

		isolla = new CheckBox();
		isolla.setCaption("Otetaanko isot kirjaimet huomioon?");

		words = new ExpandingTextArea("Sanat joita kysytään: (allekkain, pienellä!)");
		words.setValue(getDefaultWords());
		words.setImmediate(true);

		Attribute attribute = new Attribute("spellcheck", "false");
		attribute.extend(words);

		final Label sanoja = new Label("" + words.getRows());
		sanoja.setCaption("Sanoja listassa:");
		view.addComponents(numberOfExercises, timeShown, isolla, words, sanoja);
		words.addRowsChangeListener(new RowsChangeListener() {
			public void rowsChange(RowsChangeEvent event) {
				sanoja.setValue("" + (event.getRows() - 1));
			}
		});

		table = new Table();
		table.setCaption("Valitse tehtävässä esiintyvät kuvat tästä:");

		table.addContainerProperty("Kuva", Image.class, null);
		table.addContainerProperty("Valitse kuvat", CheckBox.class, null);

		for (int i = 0; i < numberOfImages() + 1; i++) {
			Object id = table.addItem();
			table.getContainerProperty(id, "Kuva").setValue(getImage(i));
			table.getContainerProperty(id, "Valitse kuvat").setValue(new CheckBox());
		}
		table.setPageLength(3);

		valinta.addValueChangeListener(event -> {
			if (valinta.getValue().equals("Sanat")) {
				rapidnamingMode = RapidnamingMode.WORDS;
				view.removeAllComponents();
				view.addComponents(valinta, numberOfExercises, timeShown, isolla, words, sanoja);
			} else if (valinta.getValue().equals("Kuvat")) {
				rapidnamingMode = RapidnamingMode.PICTURES;
				view.removeAllComponents();
				view.addComponents(valinta, numberOfExercises, timeShown, table);
				isolla.clear();
			}

		});

		return view;

	}

	@SuppressWarnings("deprecation")
	@Override
	public RapidnamingData getCurrData() {

		// jos yli 20, korjaa automaattisesti, sama ajalle?
		int number = 0;
		if (numberOfExercises.getInteger() > 20) {
			number = 20;
		} else {
			number = numberOfExercises.getInteger();
		}

		switch (rapidnamingMode) {
		case WORDS:
			return new RapidnamingData(number, timeShown.getInteger(), words.getValue().split("\n"), isolla.getValue(), RapidnamingMode.WORDS);
		case PICTURES:

			ArrayList<Object> selectedImages = new ArrayList<>();

			for (Object id : table.getItemIds()) {
				CheckBox checkBox = (CheckBox) table.getContainerProperty(id, "Valitse kuvat").getValue();

				if (checkBox.booleanValue()) {
					selectedImages.add(id);
				}
			}
			ArrayList<Integer> imageNumbers = new ArrayList<>();
			for (Object object : selectedImages) {
				if (object instanceof Integer) {
					imageNumbers.add((Integer) object);
				}
			}

			ArrayList<RapidnamingDatahelp> lista = pictures(imageNumbers);

			return new RapidnamingData(number, timeShown.getInteger(), RapidnamingMode.PICTURES, lista);
		default:
			System.out.println("Virhe editorissa!");
			return null;
		}

	}

	@Override
	public Layout drawEditorLayout() {
		return drawSettings();
	}

	@Override
	public Boolean validateData() {
		return true;
	}

	@Override
	public String setTitleText() {
		return localizer.getUIText("Rapidnaming");
	}

	private String getDefaultWords() {
		String changed = "pulpetti\n" + "kynä\n" + "kirja\n" + "penaali\n" + "tietokone\n" + "valkotaulu\n" + "tuoli\n" + "opettaja\n" + "oppilas\n" + "liitutaulu\n" + "pyyhekumi\n" + "pensseli\n" + "taideteokset\n" + "sakset\n" + "kynälaatikko\n" + "liitu\n" + "permanenttitussi\n" + "hiiri\n" + "näppäimistö\n" + "oppitunti";
		return changed;
	}

	private ArrayList<RapidnamingDatahelp> pictures(ArrayList<Integer> imageNumbers) {

		ArrayList<RapidnamingDatahelp> lista = new ArrayList<>();
		/*
		 * - lisää kuvia tiedostoon Rapidnaming\Rapidnaming-stub\src\main\webapp\WEB-INF\images ja
		 * nimeä ne järjestyksessä kuva0.png kuva1.png kuva2.png jne, jos kuvat ovat eri muodossa
		 * kun png se pitää vaihtaa myös RapidnamingEditor getImage() methodista ja
		 * RapidnamingDatahelp luokasta getPicture() methodista. Kun kuvat ovat tiedostossa liitä
		 * niihin kysymykset ja vastaukset esimerkin mukaan RapidnamingEditor luokan pictures()
		 * methodiin. Ensimmäinen argumentti on kuvan numero esim kuva1.png:n numero on 1. toinen on
		 * kysymys joka kuvasta kysytään ja viimeinen on oikea vastaus. Samasta kuvasta voi olla
		 * monia kysymyksiä. Loput esim kuvien määrän laskemisen koodi tekee itse.
		 */
		lista.add(new RapidnamingDatahelp(0, "Kuinka monta punaista palloa?", "4"));
		lista.add(new RapidnamingDatahelp(0, "Kuinka monta vihreää palloa?", "3"));
		lista.add(new RapidnamingDatahelp(1, "Kuinka monta punaista palloa?", "3"));
		lista.add(new RapidnamingDatahelp(1, "Kuinka monta vihreää palloa?", "2"));
		lista.add(new RapidnamingDatahelp(2, "Kuinka monta punaista palloa?", "3"));
		lista.add(new RapidnamingDatahelp(2, "Kuinka monta vihreää palloa?", "5"));
		lista.add(new RapidnamingDatahelp(3, "Kuinka monta punaista palloa?", "6"));
		lista.add(new RapidnamingDatahelp(3, "Kuinka monta vihreää palloa?", "4"));
		lista.add(new RapidnamingDatahelp(4, "Kuinka monta punaista palloa?", "4"));
		lista.add(new RapidnamingDatahelp(4, "Kuinka monta vihreää palloa?", "6"));
		lista.add(new RapidnamingDatahelp(5, "Mikä eläin on kuvassa?", "kissa"));

		if (imageNumbers.isEmpty()) {
			return lista;

		} else {

			ArrayList<RapidnamingDatahelp> lista2 = new ArrayList<>();

			for (RapidnamingDatahelp y : lista) {
				for (Integer x : imageNumbers) {
					if (y.getPictureNumber() == x) {
						lista2.add(y);
					}
				}
			}

			// palauttaa koko listan jos vain 1 kuva on valittuna, jos valitsee vain yhden kuvan
			// niin tehtävä ei toimi :/
			if (lista2.size() <= 1) {
				return lista;
			} else {
				return lista2;
			}
		}
	}

	private Image getImage(int pictureNumber) {
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		Image image = new Image("", new FileResource(new File(basepath + "/WEB-INF/images/kuva" + pictureNumber + ".png")));
		image.setWidth(160, Unit.PIXELS);
		image.setHeight(100, Unit.PIXELS);
		return image;
	}

	private int numberOfImages() {
		int number = 0;
		ArrayList<RapidnamingDatahelp> lista = pictures(new ArrayList<Integer>());
		for (RapidnamingDatahelp y : lista) {
			if (number < y.getPictureNumber()) {
				number = y.getPictureNumber();
			}
		}
		return number;
	}

}
