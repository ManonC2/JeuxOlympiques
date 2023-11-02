package org.resources.Controllers;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

import java.util.Map;

import org.resources.Repositories.CategorieEpreuveRepository;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/accueil")
public class AccueilController {
	private CategorieEpreuveRepository categorieEpreuveRepository = new CategorieEpreuveRepository();
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/")
	public String hello() throws IOException {
		PebbleEngine engine = new PebbleEngine.Builder().build();
		PebbleTemplate compiledTemplate = engine.getTemplate("WEB-INF/views/accueil.html");

		Map<String, Object> context = new HashMap<>();
		context.put("name", "Mitchell");

		StringWriter writer = new StringWriter();

		compiledTemplate.evaluate(writer, context);


		String output = writer.toString();
		
		return output;
	}
}
