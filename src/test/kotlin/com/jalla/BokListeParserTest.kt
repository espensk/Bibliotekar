package com.jalla

import com.jalla.model.Bok
import com.jalla.model.Kategori
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.InputStreamReader

class BokListeParserTest {

    companion object {
        fun parser(filnavn: String): BokListeParser {
            val str = BokListeParserTest::class.java.getResourceAsStream(filnavn)
                ?: throw IllegalArgumentException("Fant ikke filen $filnavn")
            return BokListeParser(InputStreamReader(str))
        }
    }

    @Test
    fun `test en bok` () {
        val parser = parser("/enbok.csv")

        val kategorier = parser.parseFile()

        assertEquals(1, kategorier.size)
        assertEquals(1, kategorier[0].bøker.size)
        val bok = kategorier[0].bøker()[0]
        assertEquals("Espen", bok.forfattere()[0])
        assertEquals("Hvordan bli millionær", bok.navn)
        assertEquals("Selvhjelp", kategorier[0].kategoriNavn())
    }

    @Test
    fun `test flere bøker og trim` () {
        val parser = parser("/treboker.csv")

        val kategorier = parser.parseFile()

        assertThat(kategorier)
            .hasSameElementsAs(listOf(
            Kategori("Selvhjelp", Bok("Hvordan bli millionær", mutableListOf("Espen"))),
            Kategori("Memoarer", Bok("Mitt liv", mutableListOf("Gauder"))),
            Kategori("Poesi", Bok("Solnedgang på Rasja", mutableListOf("Roy Harry")))
        ))
    }

    @Test
    fun `test flere bøker med samme forfatter` () {
        val parser = parser("/sammeforfattere.csv")

        val kategorier = parser.parseFile()

        assertThat(kategorier)
            .hasSameElementsAs(listOf(
                Kategori("Selvhjelp", Bok("Hvordan bli millionær", mutableListOf("Espen"))),
                Kategori("Memoarer", Bok("Mitt liv", mutableListOf("Gauder"))),
                Kategori("Krim", Bok("Rasja raidet", mutableListOf("Espen")))
        ))
    }

    @Test
    fun `test flere bøker i samme kategori` () {
        val parser = parser("/sammekategori.csv")

        val kategorier = parser.parseFile()

        assertThat(kategorier)
            .hasSameElementsAs(listOf(
                Kategori("Selvhjelp",
                    Bok("Hvordan bli millionær", mutableListOf("Espen")),
                    Bok("Hvordan bli milliardær", mutableListOf("Espen"))),
                Kategori("Memoarer",
                    Bok("Mitt liv", mutableListOf("Gauder")),
                    Bok("Berra meg hem", mutableListOf("Berran"))
                )))
    }

    @Test
    fun `test bøker med flere forfattere`() {
        val parser = parser("/flereforfattere.csv")

        val kategorier = parser.parseFile()

        assertThat(kategorier)
            .hasSameElementsAs(listOf(
                Kategori("Memoarer",
                    Bok("Man må va två", mutableListOf("Berran", "Gauder")),
                Bok("Resor", mutableListOf("Ole Bramserud", "Stig Helmer")))
            ))
    }

    @Test
    fun `test feil ved flere kategorier på samme bok` () {
        val parser = parser("/bokmedflerekategorier.csv")

        assertThrows<IllegalArgumentException> { parser.parseFile() }
    }

    @Test
    fun `test feil ved gjentatt forfatter for samme bok` () {
        val parser = parser("/duplikat.csv")

        assertThrows<IllegalArgumentException> { parser.parseFile() }
    }
}