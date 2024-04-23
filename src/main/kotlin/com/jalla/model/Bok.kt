package com.jalla.model

data class Bok(val navn: String, val forfattere: MutableList<String>) {
    fun navn(): String {
        return navn
    }

    fun forfattere(): List<String> {
        return forfattere
    }

    fun addForfattere(nyeforfattere: List<String>) {
        forfattere.addAll(nyeforfattere)
    }

    override fun equals(other: Any?): Boolean {
        // Overriding for order independent comparison
        if (this === other) return true
        if (other !is Bok) return false

        return navn == other.navn && forfattere.containsAll(other.forfattere)
    }

}