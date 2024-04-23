package com.jalla

import com.jalla.model.Bok
import com.jalla.model.Kategori
import java.io.BufferedReader
import java.io.InputStreamReader

class BokListeParser(val inputStreamReader: InputStreamReader) {

    fun parseFile(): List<Kategori> {
        val reader = BufferedReader(inputStreamReader)
        val kategorier = HashMap<String, Kategori>()
        reader.use {
            do {
                val linje = reader.readLine()
                if (linje != null) {
                    val rad = linje.split('|')
                    if(rad.size != 3) throw IllegalArgumentException("Feil antall elementer i raden: $linje")

                    val forfatterNavn = rad[0].trim()
                    val bokNavn = rad[1].trim()
                    val kategoriNavn = rad[2].trim()

                    val kategori = kategorier.getOrPut(kategoriNavn) { Kategori(kategoriNavn) }
                    // Sjekk om boken allerede fins på en annen kategori
                    if(kategorier.filter { it.key != kategoriNavn }
                        .values.any { it.bøker().any { bok -> bok.navn == bokNavn } }) {
                        throw IllegalArgumentException("Boken $bokNavn finnes allerede i en annen kategori")
                    }
                    kategori.addBok(Bok(bokNavn, mutableListOf(forfatterNavn)))
                }
            } while (linje != null)

            return kategorier.values.toList()
        }
    }
}