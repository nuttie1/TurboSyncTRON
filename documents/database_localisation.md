# Tietokanta lokalisaatio strategia

**Turbosync TRON**

## Johdanto

Tässä raportissa kuvataan suunnitelmamme tietokannan lokalisoinnista käyttäen kloonitietokanta strategiaa. Tämä strategia on valittu sen joustavuuden, suorituskyvyn ja ylläpidettävyyden vuoksi.

## Suunnitelma

### Vaihe 1: Taulujen Luominen

Aloitamme luomalla erillisen kieli tietokanta taulun jokaiselle kielelle, jota aiomme tukea ohjelmassamme. Kielet ovat suomi, englanti, ruotsi ja kiina (yksinkertaistettu). Nämä taulut sisältävät fraasit kyseisen taulun kielen mukaan.

### Vaihe 2: Tietokantojen Räätälöinti

Seuraavaksi räätälöimme jokaisen tietokannan tarpeidemme mukaan. Tämä tarkoittaa, että voimme mukauttaa tietokannan rakennetta, tietotyyppejä ja indeksejä kunkin kieliversion tarpeiden mukaan.

### Vaihe 3: Tietojen Siirto

Joudumme siirtämään kirjoitetun tekstin tietokantaan ohjelmasta. Tämä työ luultavasti tehdään käsin, jokaiselle kielelle. On mahdollista tehdä omat skriptit, joilla työ voitaisi automatisoida, mutta tämä voi olla liian resurssi kallista.

### Vaihe 5: Ohjelman muuttaminen

Tässä vaiheessa myös muuttaisimme ohjelmaamme käyttämään kyseisiä tauluja. Joudumme tekemään kyselyn joka hakee tietokannasta tarvittavan kielen sanastot, joita ohjelma käyttää. Oletettavasti tässä vaiheessa tulisi eniten ongelmia.

### Vaihe 6: Testaus

Kun tiedot on siirretty, testaamme ja validoimme tietokannat varmistaaksemme, että ne toimivat oikein. Tämä sisältää tietojen tarkistamisen, suorituskyvyn testauksen ja tietoturvan varmistamisen.

### Vaihe 7: Ylläpito

Lopuksi, kun tietokannat ovat toiminnassa, huolehdimme niiden ylläpidosta. Tämä sisältää tietojen päivityksen, tietokantojen synkronoinnin ja mahdollisten ongelmien ratkaisemisen.
