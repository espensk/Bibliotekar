# Bibliotekar

Leser en CSV stil fil med bøker strukturert med linjer av
Forfatternavn|boknavn|kategori

sorterter dem i en trestruktur med lister av kategorier og bøker med forfattere, og returnerer det som JSON
`[
    {
        "kategori" : <kategorinavn>
        "antall": <antall bøker i kategori>
        "bøker": [
            {
            "navn": <boknavn>
            "forfattere": [<liste av forfatternavn>]
            },
            ...
        ]
    },
    ...
]`


## Implementasjon
Applikasjonsrammeverket er autogenert med spring, kotlin og gradle på https://start.spring.io/
Den bruker spring boot command line runner, fordi det var krevd i oppgaven. 
Kan argumenteres at det er å overkill for en så enkel oppgave.

Selv om output skal være JSON lister, bruker jeg mer maps internt, for å få enkel sortering og unngå duplikater.

### Inkluderte rammeverk
- csv parsing: Valgte å lese filen manuelt og splitte linjene på | tegnet. 
  Hadde oppgaven spesifisert csv eller krevd et mer avansert filformat, ville jeg brukt en csv parser fex Apache Commons CSV
- JSON serialisering: Prøvde meg med kotlinx.serialization, men det nektere å serialisere beregnede verdier. 
  Jeg ønsker ikke å duplisere en verdi for kategori.antall (som er beregnet fra bøker.size) i klassen, så jeg bytter til Jackson, også standard i spring.
  Da får jeg en smak av 'dependency hell', fordi siste jackson ikke støttes av siste spring. 
  Må nedgradere jackson til 2.15.4 som jeg finner via gradle dependencies. Ble påminnet hvorfor jeg ikke har brukt spring hittil.
- logging: logback

### Datastrukturer
Denne oppgaven kunne vært litt naivt løst med en Map<String, Map<String, List<String>>> som mapper kategori -> boknavn -> forfatter
Dette ville krevd manuell JSON serialisering, som jeg ikke har lyst til å begynne med.
Lager heller en liten modellstruktur med klasser for bøker og kategorier, og bygger opp denne strukturen under parsing av CSV filen

Kategori(kategorinavn, bøker)
    Bok(boknavn, forfattere)

Jeg velger å bruke Maps for å holde bøker i en kategori, for å unngå duplikater og ha raske søk. 
Det medfører at Kategori må bruke noen Json annotasjoner for å serialisere til riktig format.  

## Kjøring
For å starte applikasjonen, kjør `./gradlew bootRun <csv-fil>` i prosjektmappen

## Pass språkbruken
Siden oppgaven var på norsk og krever norske navn i JSON, har jeg valgt å bruke norsk i koden også.
Dette medfører dessverre en litt knotete blanding av norsk og engelsk i koden siden standard variable og metodenavn er på engelsk.

## Anger
At jeg startet med norske navn på klasser, verdier og metoder i modellen.
Syntes det hørtes litt moro ut å teste det siden json formatet krever dette, men det ble forferdelig å lese 
blandingen av norsk og engelsk via standardnavnene.  
Jeg burde heller valgt Json annotasjoner for å styre disse.