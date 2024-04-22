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

### Inkluderte rammeverk
- csv reader

### Datastrukturer
Denne oppgaven kunne vært naivt løst med en Map<String, Map<String, List<String>>> som mapper kategori -> boknavn -> forfatter
Dette ville krevd manuell JSON serialisering, som jeg ikke har lyst til å begynne med.
Lager heller en liten modellstruktur med klasser for bøker og kategorier, og bygger opp denne strukturen under parsing av CSV filen

Kategori(kategorinavn, bøker)
    Bok(boknavn, forfattere)

## Kjøring
For å starte applikasjonen, kjør `./gradlew bootRun <csv-fil>` i prosjektmappen
