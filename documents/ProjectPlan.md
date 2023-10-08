# Projektisuunnitelma
## OTP1 R4 TurboSyncTRON
---
## Projektin tavoitteet (Goal)
### Tavoitteet henkilöille
Opitaan ohjelmistoprojektin suunnittelun ja ketterän toteutuksen periaatteet.  
Suunnitellaan ja toteutetaan ohjelmisto kodin IoT-laitteiden hallintaan.  
### Tavoitteet ohjelmistolle
Ohjelmistotuote koostuu lokaalista käyttöliittymästä ja bisneslogiikasta sekä ulkoisesta tietokannasta ja IoT-laitesimulaattorista.  
Ohjelmiston tulee olla helppokäyttöinen ja luotettava. Käyttöliittymä on asennettavissa PC-tietokoneille.  
Ohjelmisto ja sen palvelininfrastruktuuri on käytettävissä Suomessa.    
IoT-laitesimulaattori tuottaa tietokantaan relevanttia ja uskottavaa dataa.  
Data on tarkasteltavissa käyttöliittymän kautta.  

---
## Projektin laajuus (Scope)
### Käyttöliittymä
- Graafinen käyttöliittymä
- Pääsynhallinta käyttäjätunnuksella ja salasanalla
    - Mahdollisuus luoda useita käyttäjiä
    - Salasanan tietoturvallinen käsittely
    - Käyttäjän tietojen ja käyttäjän laitteiden tietojen esittäminen ainoastaan onnistuneen autentikaation jälkeen
- Mahdollisuus tehdä tarvittavat syötteet IoT-laitehallinnan toteuttamiseksi
- Näkymähierarkia joka jaottelee ohjelmiston päätoiminnallisuuksiin
    - Käyttäjän luonti
    - Sisäänkirjautuminen
    - Päänäkymä josta suoraviivainen navigointi muihin näkymiin
    - Käyttäjän laitteet: Listaus käyttäjän lisäämistä laitteista
    - Laitteen lisääminen: Näkymä jossa käyttäjä valitsee lisättävän laitteen tyypin, nimeää laitteen ja asettaa sen asetukset sekä halutessaan tarkemman tekstikuvauksen.
    - Lempilaitteet: Käyttäjän omista laitteistaan valitsema lista tärkeimpiä tai useimmin käytettyjä laitteita. Helpottamaan ohejelmiston rutiinikäyttöä ja laitteiden ohjaamista.
    - Laitedata: Esittää valitun laitteen tuottamaa dataa graafisesti
### Tietokanta
- MariaDB Metropolian PHPmyAdmin-palvelussa
- Tietokantaan tallennetaan:
    - Käyttäjien autenkointiin tarvittava tieto
    - Käyttäjien lisäämien IoT-laitteiden tiedot
    - IoT-laitteiden (IoT-laitesimulaattori) tuottama data
### IoT-laitesimulaattori
- Hakee ajoittain tietokannasta laitteiden tiedot
- Tuottaa laitetietojen perusteella uskottavaa laitedataa
- Tallentaa tuotetun datan tietokantaan

---
## Sidosryhmävaatimukset
### Asiakas
- PO (Opettaja)
- Asiakas on hyväksynyt määritellyn tavoitteen ja laajuuden
### Loppukäyttäjät
- Loppukäyttäjät ovat henkilöitä, jotka haluavat kokeilla ilmaista IoT-laitehallintasovellusta
- Käyttäjätutkimuksen tulosten perusteella on laadittu lista vaatimuksista
## Toiminnalliset vaatimukset
- Käyttöliittymä
    - Käytettävissä normaaleilla PC-syötelaitteilla (hiiri ja näppäimistö)
- Ojelmiston toiminnot
    - Käyttäjän tunnistaminen
    - Käyttäjädatan yksityisyys
    - Ohjelmiston asetusten hallitseminen (user preferences)
    - IoT-laitteiden lisääminen (provisiointi)
      - Laitetyypin valinta
      - Laitteen nimeäminen
      - Laitteen asetusten asettaminen
      - Laitekuvauksen asettaminen
    - IoT-laitteiden hallinta: Laitteiden toiminnan ohjaaminen
    - IoT-datan tarkastelu: Laitedatan selkeä graafinen esittäminen
- Tietokanta
    - Tallentaa käyttäjätiedot, laitetiedot ja laitedatan
    - Palauttaa käyttäjätiedot, laitetiedot ja laitedatan
## Ei-toiminnalliset vaatimukset
- Käyttöliittymä
    - Intuitiivinen: Käytettävissä ilman koulutusta
    - Modulaarinen: Näkymien lisääminen jatkokehityksessä suoraviivaista
- Ohjelmisto
    - Tietoturvallinen: Käyttäjätiedot ja käyttäjän omistamat tiedot ovat saatavilla vain autentikaation kautta
    - Luotettava: Korkea saatavuusaste
    - Arkkitehtuuri: Modulaarinen ja muokattava rakenne ylläpidon ja jatkokehityksen fasilitoimiseksi
- Tietokanta
    - Tietoturvallinen: Käyttäjätiedot ja käyttäjän omistamat tiedot ovat saatavilla vain autentikaation kautta
    - Luotettava: Korkea saatavuusaste
    - Suorituskyky: Riittävä suorituskyky arvioidulle käyttäjä- ja laitemäärälle
    - Siirrettävyys: Standardi relaatiotietokanta, joka on tarvittaessa siirrettävissä eri palveluntarjoajan palvelimelle

---
## Resursointi
- Budjetti
    - Verta, hikeä ja kyyneleitä
- Henkilöstö
    - Kaikki saatavilla olevat ohjelmistokehittäjät: Niko Ala-aho, Ilkka Karvinen, Onni Pajula, Otto Palssa
- Laitteisto ja laitteistoinfrastruktuuri
    - Tietokoneet kehittäjille
    - SQL-tietokantapalvelin joka on saavutettavissa internetin välityksellä
    - Laitesimulaattoripalvelin joka kykenee kommunikoimaan tietokantapalvelimen kanssa
- Ohjelmistotyökalut
    - IntelliJ IDEA
    - MariaDB / PHPmyAdmin
    - Java
    - JavaFX
    - Maven
    - Jenkins
    - Docker
    - Trello
    - Github
    - Discord
    - draw.io

---
## Riskiarvio
- Potentiaaliset riskit projektin onnistumiselle:
    - Kehittäjien osaamisvaje voi aiheuttaa aikataulujen venymistä tai suunnitelman osan osoittaumista toteuttamiskelvottomaksi aikataulun puitteissa
    - Sairastapaukset voivat vaarantaa aikataulun
    - Projektin vaatimusten muuttuminen kehittäjistä riippumattomista syistä voi vaarantaa aikataulun
    - Force Majeure

---
## Kehitystiimi
Niko Ala-aho, Ilkka Karvinen, Onni Pajula, Otto Palssa

---
## Kommunikaatiosuunnitelma
Projekti toteutetaan Scrum-meneltelmällä.  
Kehitystiimi kokoontuu Sprint-kokouksiin viikottain.  
Kehitystiimi kommunikoi aktiivisesti Discord-viestintäsovelluksen avulla.  
Asiakas seuraa projektia Sprint-kokousten yhteydessä.  

---
## Sääntelyvaatimukset
Tuote ei ole kaupallinen, eikä täten ole sääntelyn piirissä.  
Projekti toteutetaan noudattaen tietoturvan ja ohjelmistokehityksen parhaita käytäntöjä.








