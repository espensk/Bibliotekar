package com.jalla.model

import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonIgnore

data class Kategori(val kategori: String,
                    @JsonIgnore val bøker: HashMap<String, Bok> = HashMap()) {

    constructor(kategori: String, vararg bok: Bok) : this(kategori) {
        bok.forEach { addBok(it) }
    }
    fun kategoriNavn(): String {
        return kategori
    }

    @JsonGetter("bøker")
    fun bøker(): List<Bok> {
        return bøker.values.toList()
    }

    @JsonGetter("antall")
    fun antall(): Int {
        return bøker.size
    }

    /**
     * Legger til en bok i kategorien.
     * Om boken allerede eksisterer i kategorien, legges forfatter til på eksisterende bok.
     * Om samme forfatter prøver å legge til samme bok, kastes en exception.
     */
    fun addBok(bok: Bok) {
        val eksisterende = bøker.get(bok.navn)
        if (eksisterende != null) {
            if (eksisterende.forfattere.any { bok.forfattere.contains(it) }) {
                throw IllegalArgumentException("Boken ${bok.navn} av ${bok.forfattere} finnes allerede i kategorien $kategori")
            }
            eksisterende.addForfattere(bok.forfattere)
        } else {
            bøker[bok.navn] = bok
        }

    }

}