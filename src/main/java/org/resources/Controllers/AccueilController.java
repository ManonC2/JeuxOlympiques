package org.resources.Controllers;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.resources.Models.CategorieEpreuve;
import org.resources.Models.Discipline;
import org.resources.Models.Epreuve;
import org.resources.Models.Site;
import org.resources.Repositories.DisciplineRepository;
import org.resources.Repositories.EpreuveRepository;
import org.resources.Repositories.SiteRepository;
import org.resources.Models.SessionConnexion;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Stateless
@Path("/accueil")
public class AccueilController {

	EpreuveRepository epreuveRepository = new EpreuveRepository();
	SiteRepository siteRepository = new SiteRepository();
	DisciplineRepository disciplineRepository = new DisciplineRepository();
	
	@Asynchronous
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/")
	public String hello(@CookieParam("sessionId") String sessionId) throws IOException {
		PebbleEngine engine = new PebbleEngine.Builder().build();
		PebbleTemplate compiledTemplate = engine.getTemplate("WEB-INF/views/accueil.html");

		
		
		Map<String, Object> context = new HashMap<>();
		context.put("name", "Mitchell");

		
		StringWriter writer = new StringWriter();

		if(SessionConnexion.getUtilisateur(sessionId) != null){
			context.put("NomCookie",SessionConnexion.getUtilisateur(sessionId).getNom());
			context.put("RoleCookie",SessionConnexion.getUtilisateur(sessionId).getRole().getId());
			context.put("Connecter", true);
		}
		else {
			context.put("NomCookie","Anonyme");
			context.put("Connecter", false);
		}
		compiledTemplate.evaluate(writer, context);

		
		String output = writer.toString();
		
		return output;
	}
	
	@Asynchronous
	@GET
	@Path("/statistiques")
	@Consumes(MediaType.TEXT_PLAIN)
	public String updateEpreuve(@QueryParam("id") String id) {
		List<Epreuve> listeEpreuve;
		List<Site> listeSite;
		List<Discipline> listeDiscipline;
		
		
		PebbleEngine engine = new PebbleEngine.Builder().build();
		PebbleTemplate compiledTemplate = engine.getTemplate("WEB-INF/views/accueil.html");
		

		Map<String, Object> context = new HashMap<>();

		if(Integer.parseInt(id) == 1) {
			listeSite = siteRepository.findSitesWithLotOfSessions();
			context.put("sites", listeSite);
		}
		if(Integer.parseInt(id) == 2) {
			listeDiscipline = disciplineRepository.findFiveLongestDisciplines();
			context.put("disciplines", listeDiscipline);
		}
		if(Integer.parseInt(id) == 3) {
			listeDiscipline = disciplineRepository.findDisciplineOnLongestDistance();
			context.put("disciplines", listeDiscipline);
		}
		if(Integer.parseInt(id) == 4) {
			listeEpreuve = epreuveRepository.findEpreuveWeight();
			context.put("epreuves", listeEpreuve);
		}
		if(Integer.parseInt(id) == 5) {
			listeEpreuve = epreuveRepository.findEpreuveIndividual();
			context.put("epreuves", listeEpreuve);
		}
		if(Integer.parseInt(id) == 6) {
			listeEpreuve = epreuveRepository.findEpreuveGroup();
			context.put("epreuves", listeEpreuve);
		}

		
		StringWriter writer = new StringWriter();

		try {
			compiledTemplate.evaluate(writer, context);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String output = writer.toString();
		
		return output;

	}
	
}
