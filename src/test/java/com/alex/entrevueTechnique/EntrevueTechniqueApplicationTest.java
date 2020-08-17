package com.alex.entrevueTechnique;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EntrevueTechniqueApplication.class)
public class EntrevueTechniqueApplicationTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void contextLoads() {
	}

	@Test
	public void obtenirChaine_sansParametre() throws Exception {
		String adresse = "/obtenirCaracteres";
		String resultatAttendu = Constantes.chaineVide;

		ResultActions reponse = this.mockMvc.perform(get(adresse)).andDo(print());

		reponse.andExpect(status().isOk()).andExpect(content().string(containsString(resultatAttendu)));
	}

	@Test
	public void obtenirChaine_testVide() throws Exception {
		String chaine = "";
		String adresse = String.format("/obtenirCaracteres?chaine=%s", chaine);
		String resultatAttendu = Constantes.chaineVide;

		ResultActions reponse = this.mockMvc.perform(get(adresse)).andDo(print());

		reponse.andExpect(status().isOk()).andExpect(content().string(containsString(resultatAttendu)));
	}

	@Test
	public void obtenirChaine_testPositive() throws Exception {
		String chaine = "qwertyuiopasdfghjklzxcvbnmqwer";
		String adresse = String.format("/obtenirCaracteres?chaine=%s", chaine);
		String resultatAttendu = "abcdefghijklmnopqrstuvwxyz";

		ResultActions reponse = this.mockMvc.perform(get(adresse)).andDo(print());

		reponse.andExpect(status().isOk()).andExpect(content().string(containsString(resultatAttendu)));
	}

	@Test
	public void obtenirChaine_testNegatif() throws Exception {
		String chaine = "asdfghjklzxcvbnmqwerasdfghjklzxcvbnmqwerasdfghjklzxcvbnmqwer";
		String adresse = String.format("/obtenirCaracteres?chaine=%s", chaine);
		String resultatAttendu = Constantes.limiteCaracteresDepasee;

		ResultActions resultat = this.mockMvc.perform(get(adresse)).andDo(print());

		resultat.andExpect(status().isOk()).andExpect(content().string(containsString(resultatAttendu)));
	}

	@Test
	public void ecrireNumero_sansParametre() throws Exception {
		String adresse = "/ecrireNumero";
		String resultatAttendu = Constantes.numeroVide;

		ResultActions resultat = this.mockMvc.perform(get(adresse)).andDo(print());

		resultat.andExpect(status().isOk()).andExpect(content().string(containsString(resultatAttendu)));
	}

	@Test
	public void ecrireNumero_mineur() throws Exception {
		String numero = "-1";
		String adresse = String.format("/ecrireNumero?numero=%s", numero);
		String resultatAttendu = Constantes.numeroVide;

		ResultActions resultat = this.mockMvc.perform(get(adresse)).andDo(print());

		resultat.andExpect(status().isOk()).andExpect(content().string(containsString(resultatAttendu)));
	}

	@Test
	public void ecrireNumero_zero() throws Exception {
		String numero = "0";
		String adresse = String.format("/ecrireNumero?numero=%s", numero);
		String resultatAttendu = "Zero";

		ResultActions resultat = this.mockMvc.perform(get(adresse)).andDo(print());

		resultat.andExpect(status().isOk()).andExpect(content().string(containsString(resultatAttendu)));
	}

	@Test
	public void ecrireNumero_numeroExistant() throws Exception {
		String numero = "9192";
		String adresse = String.format("/ecrireNumero?numero=%s", numero);
		String resultatAttendu = "Nine thousand, one hundred ninety two";

		ResultActions resultat = this.mockMvc.perform(get(adresse)).andDo(print());

		resultat.andExpect(status().isOk()).andExpect(content().string(containsString(resultatAttendu)));
	}

	@Test
	public void ecrireNumero_numeroInvalide() throws Exception {
		String numero = "91929192";
		String adresse = String.format("/ecrireNumero?numero=%s", numero);
		String resultatAttendu = Constantes.limiteNumeroDepasee;

		ResultActions resultat = this.mockMvc.perform(get(adresse)).andDo(print());

		resultat.andExpect(status().isOk()).andExpect(content().string(containsString(resultatAttendu)));
	}
}
