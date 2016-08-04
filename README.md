# Rapidnaming

###Kuvat:

lisää kuvia tiedostoon Rapidnaming-stub\src\main\webapp\WEB-INF\images ja nimeä ne järjestyksessä kuva0.png kuva1.png kuva2.png jne, jos kuvat ovat eri muodossa kun png se pitää vaihtaa myös RapidnamingEditor getImage() methodista ja RapidnamingDatahelp luokasta getPicture() methodista.

Kun kuvat ovat tiedostossa liitä niihin kysymykset ja vastaukset esimerkin mukaan RapidnamingEditor luokan pictures() methodiin. Ensimmäinen argumentti on kuvan numero esim kuva1.png:n numero on 1. toinen on kysymys joka kuvasta kysytään ja viimeinen on oikea vastaus. Samasta kuvasta voi olla monia kysymyksiä. Loput esim kuvien määrän laskemisen koodi tekee itse.


###Sanat:

Oletussanojen vaihtamiseen vaihda ne RapidnamingEditor- ja RapidnamingTabbedEditorista getDefaultWords() methodista.

    
###Ongelmat:

Jos tehtävän alkaessa ("Aloita tehtävä" nappulan kohdassa) painaa enter nappulaa, ohjelma suoraan sanottuna sekoaa eikä toimi oikein.

Ajoitus ei toimi, esim. 0001ms ja 999ms on sama asia, kuin myös 2001 ja 2999, tähän pitää tehdä erillinen, lokaali ratkaisu esim. javascriptin avulla.

Koodi on myöskin paikoittain melko sekaista ja todennäköisesti myös hidasta, muuttujien nimeäminenkään ei ole huolella nimetty paikoin :/

Myös lisää bugeja- ja parannuspaikkoja löytyy paljonkin varsinkin ulkonäön puolella.
