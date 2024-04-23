package com.jalla

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.FileInputStream
import java.io.InputStreamReader

@SpringBootApplication
class BibliotekarApplication: CommandLineRunner {

	override fun run(vararg args: String?) {
		val kategorier = BokListeParser(InputStreamReader(FileInputStream(args[0]!!))).parseFile()
		// TODO Serialize to json! Json.encodeToString(kategorier)
		println(kategorier)
	}

}

fun main(args: Array<String>) {
	runApplication<BibliotekarApplication>(*args)
}
