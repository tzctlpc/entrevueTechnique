package com.alex.entrevueTechnique;

import com.github.tsohr.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@SpringBootApplication
@RestController
public class EntrevueTechniqueApplication {

	//region Méthodes Publiques

	public static void main(String[] args) {
		SpringApplication.run(EntrevueTechniqueApplication.class, args);
	}

	@GetMapping("/obtenirCaracteres")
	public String obtenirCaracteres(@RequestParam(value = "chaine", defaultValue = "") String chaine) {
		JSONObject reponse = new JSONObject();
		String resultat;

		if (chaine.isEmpty()) {
			resultat = Constantes.chaineVide;
		}
		else if (chaine.length() < 31) {
			resultat = chaine.chars().mapToObj(x -> Character.toString((char) x)).distinct().sorted().collect(Collectors.joining());
		}
		else {
			resultat = Constantes.limiteCaracteresDepasee;
		}

		reponse.put("content", resultat);

		return reponse.toString();
	}

	@GetMapping("/ecrireNumero")
	public String ecrireNumero(@RequestParam(value = "numero", defaultValue = "-1") int numero) {
		JSONObject reponse = new JSONObject();
		String texte = convertirNumero(numero);
		String resultat = texte.substring(0, 1).toUpperCase().concat(texte.substring(1));

		reponse.put("content", resultat);

		return reponse.toString();
	}

	//endregion

	//region Méthodes Privées

	private static String convertirUnites(int numero) {
		String resultat = "%s";
		int position = numero%10;

		return String.format(resultat, Constantes.listeUnites[position]);
	}

	private static String convertirDizaines(int numero) {
		String resultat = "%s";
		int position = (numero%100)/10;

		return String.format(resultat, Constantes.listeDizaines[position]);
	}

	private static String convertirCentaines(int numero) {
		String resultat = "%s ".concat(Constantes.texteCentaines);
		int position = (numero%1000)/100;

		return String.format(resultat, Constantes.listeUnites[position]);
	}

	private static String convertirMoinsMille(int numero) {
		String resultat;

		if (numero == 0) {
			resultat = Constantes.texteZero;
		}
		else if (numero < 20) {
			resultat = convertirUnites(numero);
		}
		else if (numero < 100) {
			String unites = convertirUnites(numero);
			String dizaines = convertirDizaines(numero);

			resultat = String.format("%s%s", dizaines, unites);
		}
		else {
			String unites = convertirUnites(numero);
			String dizaines = convertirDizaines(numero);
			String centaines = convertirCentaines(numero);

			resultat = String.format("%s%s%s", centaines, dizaines, unites);
		}

		return resultat;
	}

	private static String convertirMilliers(int numero) {
		String resultat = "%s ".concat(Constantes.texteMille);
		String milliers = convertirMoinsMille(numero/1000);

		return String.format(resultat, milliers);
	}

	private static String convertirNumero(int numero) {
		String resultat;

		if (numero < 0) {
			resultat = Constantes.numeroVide;
		}
		else if (numero < 1000) {
			resultat = convertirMoinsMille(numero);
		}
		else if (numero < 1000000) {
			String milliers = convertirMilliers(numero);
			String restant = convertirMoinsMille(numero);

			resultat = String.format("%s,%s", milliers, restant);
		}
		else {
			resultat = Constantes.limiteNumeroDepasee;
		}

		return resultat.trim();
	}

	//endregion
}
