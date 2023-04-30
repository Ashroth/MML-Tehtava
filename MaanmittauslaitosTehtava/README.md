# Laskutusohjelma
Java tehtävä

## Rakenne.PNG
Kuvailee datan rakenteita.

## Tehtava.java
Varsinainen ohjelma. Kerää tuotetiedot.

### toinenAlgoritmi
"Avaa" asiakas- ja laskutustapahtumaluettelot. Käy läpi jokaisen asiakkaan ja jatkaa jos asiakas vastaa laskutustapahtuman asiakasta tai asiakas kuuluu maksulliseen ryhmään. Muodostetaan lasku. Kerätään laskutettavia tapahtumia laskuun niin kauan kuin niiden asiakas on sama. Asiakkaan muuttuessa tulostetaan lasku ja luetaan seuraava asiakas.

### hidasAlgoritmi
Vaihtoehtoinen algoritmi "hidasAlgoritmi" toimii vastaavasti, mutta kaikki tapahtumat käydään läpi jokaisen asiakkaan kohdalla. Algoritmi on hitaampi, mutta asiakkaiden ja laskutustapahtumien ei tarvitse olla hyvin järjesteltyjä

### yksiAlgoritmi
Suunnilleen sama kuin toinenAlgorimit, mutta yrittää lukea tiedostosta. Teknisiä vaikeuksia

## Oliot

### Asiakas.java
Sisältää asiakkaan perustiedot yhdessä oliossa.

### Tuotteet.java
Tuote - Hinta pari, nämä kerätään listaan

### LaskutusTapahtuma.java
Tuote - Hinta - KPL - Yhteensä.

### Lasku.java
Asiakas - LaskutusTapahtumat. Kerätään tapahtumat listaan
