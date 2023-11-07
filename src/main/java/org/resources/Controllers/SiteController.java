package org.resources.Controllers;

import java.io.IOException;
import java.io.StringWriter;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.resources.Models.CategorieSite;
import org.resources.Models.Session;
import org.resources.Models.Site;
import org.resources.Repositories.CategorieSiteRepository;
import org.resources.Repositories.SiteRepository;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/sites")
public class SiteController {
	
	SiteRepository siteRepository = new SiteRepository();
	CategorieSiteRepository categorieSiteRepository = new CategorieSiteRepository();
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/")
	public String hello() throws IOException {
		PebbleEngine engine = new PebbleEngine.Builder().build();
		PebbleTemplate compiledTemplate = engine.getTemplate("WEB-INF/views/sites/sites.html");
		
		List<Site> listeSites = siteRepository.findAll();

		Map<String, Object> context = new HashMap<>();
		context.put("sites", listeSites);

		StringWriter writer = new StringWriter();

		compiledTemplate.evaluate(writer, context);


		String output = writer.toString();
		
		return output;
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/newSite")
	public String newSession() throws IOException {
		PebbleEngine engine = new PebbleEngine.Builder().build();
		PebbleTemplate compiledTemplate = engine.getTemplate("WEB-INF/views/sites/newSite.html");
		
		List<CategorieSite> listeCategoriesSites = categorieSiteRepository.findAll();
		
		Map<String, Object> context = new HashMap<>();
		context.put("categoriesSites", listeCategoriesSites);

		StringWriter writer = new StringWriter();

		compiledTemplate.evaluate(writer, context);


		String output = writer.toString();
		
		return output;
	}
}

