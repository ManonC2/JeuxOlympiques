package org.resources.Controllers;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.resources.Models.CategorieEpreuve;
import org.resources.Models.Discipline;
import org.resources.Models.Epreuve;
import org.resources.Repositories.CategorieEpreuveRepository;
import org.resources.Repositories.DisciplineRepository;
import org.resources.Repositories.EpreuveRepository;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import jakarta.ejb.Stateless;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Stateless
@Path("/epreuves")
public class EpreuveController {
	EpreuveRepository epreuveRepository = new EpreuveRepository();
	DisciplineRepository disciplineRepository = new DisciplineRepository();
	CategorieEpreuveRepository categorieEpreuveRepository = new CategorieEpreuveRepository();
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/")
	public String hello() throws IOException {
		PebbleEngine engine = new PebbleEngine.Builder().build();
		PebbleTemplate compiledTemplate = engine.getTemplate("WEB-INF/views/epreuves/epreuves.html");
		
		List<Epreuve> listEpreuves = epreuveRepository.findAll();

		Map<String, Object> context = new HashMap<>();
		context.put("epreuves", listEpreuves);
		StringWriter writer = new StringWriter();

		compiledTemplate.evaluate(writer, context);


		String output = writer.toString();
		
		return output;
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/newEpreuve")
	public String newEpreuve() throws IOException {
		PebbleEngine engine = new PebbleEngine.Builder().build();
		PebbleTemplate compiledTemplate = engine.getTemplate("WEB-INF/views/epreuves/newEpreuve.html");
		
		List<Epreuve> listeEpreuve = epreuveRepository.findAll();
		List<Discipline> listeDiscipline = disciplineRepository.findAll();
		List<CategorieEpreuve> listeCategoriesEpreuves = categorieEpreuveRepository.findAll();

		Map<String, Object> context = new HashMap<>();
		context.put("epreuves", listeEpreuve);
		context.put("disciplines", listeDiscipline);
		context.put("categoriesEpreuves", listeCategoriesEpreuves);
		
		StringWriter writer = new StringWriter();

		compiledTemplate.evaluate(writer, context);


		String output = writer.toString();
		
		return output;
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/newDiscipline")
	public String newDiscipline() throws IOException {
		PebbleEngine engine = new PebbleEngine.Builder().build();
		PebbleTemplate compiledTemplate = engine.getTemplate("WEB-INF/views/epreuves/newDiscipline.html");
		
		

		Map<String, Object> context = new HashMap<>();
		
		StringWriter writer = new StringWriter();

		compiledTemplate.evaluate(writer, context);


		String output = writer.toString();
		
		return output;
	}
	
	@POST
	@Path("/addEpreuve")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response enregistrerEpreuve(@FormParam("nom") String nom, @FormParam("discipline") int disciplineId,
			@FormParam("categorie") int categorieEpreuveId) {

		CategorieEpreuve catEpreuve = categorieEpreuveRepository.findById(categorieEpreuveId);
		Discipline discipline = disciplineRepository.findById(disciplineId);

		Epreuve epreuve = new Epreuve(nom, discipline, catEpreuve);

		epreuveRepository.add(epreuve);

		return Response.seeOther(URI.create("http://localhost:8080/JeuxOlympique/web/epreuves")).build();

	}
	
	@POST
	@Path("/addDiscipline")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response enregistrerDiscipline(@FormParam("nom") String nom, @FormParam("paralympique") boolean paralympique) {

		Discipline discipline = new Discipline(nom, paralympique);

		disciplineRepository.add(discipline);

		return Response.seeOther(URI.create("http://localhost:8080/JeuxOlympique/web/epreuves")).build();

	}
	
	@GET
	@Path("/delete")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response delete(@QueryParam("id") String id) {
		epreuveRepository.delete(Integer.parseInt(id));
		
		return Response.seeOther(URI.create("http://localhost:8080/JeuxOlympique/web/epreuves")).build();
	}
}
