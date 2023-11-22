package org.resources.Controllers;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.resources.Models.CategorieSite;
import org.resources.Models.Site;
import org.resources.Repositories.CategorieSiteRepository;
import org.resources.Repositories.SiteRepository;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Stateless
@Path("/sites")
public class SiteController {

	SiteRepository siteRepository = new SiteRepository();
	CategorieSiteRepository categorieSiteRepository = new CategorieSiteRepository();

	@Asynchronous
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

	@Asynchronous
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

	@Asynchronous
	@POST
	@Path("/addSite")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response enregistrer(@FormParam("nom") String nom, @FormParam("ville") String ville,
			@FormParam("categorieSite") int categorieSiteId) {

		CategorieSite categorieSite = categorieSiteRepository.findById(categorieSiteId);

		Site site = new Site(categorieSiteId,nom, ville, categorieSite);

		siteRepository.add(site);

		return Response.seeOther(URI.create("http://localhost:8080/JeuxOlympique/web/sites")).build();

	}
	
	@Asynchronous
	@GET
	@Path("/updateSite")
	@Consumes(MediaType.TEXT_PLAIN)
	public String updateSite(@QueryParam("id") String id) {
		
		Site site = siteRepository.findById(Integer.parseInt(id));
		
		PebbleEngine engine = new PebbleEngine.Builder().build();
		PebbleTemplate compiledTemplate = engine.getTemplate("WEB-INF/views/sites/newSite.html");

		List<CategorieSite> listeCategoriesSites = categorieSiteRepository.findAll();

		Map<String, Object> context = new HashMap<>();
		context.put("categoriesSites", listeCategoriesSites);
		context.put("site", site);

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
	
	@Asynchronous
	@POST
	@Path("/addUpdatedSite")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addUpdateSite(@FormParam("nom") String nom, @FormParam("ville") String ville,
			@FormParam("categorieSite") int categorieSiteId, @QueryParam("id") String id) {

		CategorieSite categorieSite = categorieSiteRepository.findById(categorieSiteId);

		Site site = new Site(Integer.parseInt(id), nom , ville, categorieSite);

		siteRepository.update(site);
		
		return Response.seeOther(URI.create("http://localhost:8080/JeuxOlympique/web/sites")).build();

	}
	
	@Asynchronous
	@GET
	@Path("/delete")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response delete(@QueryParam("id") String id) {
		
		siteRepository.delete(Integer.parseInt(id));
		
		return Response.seeOther(URI.create("http://localhost:8080/JeuxOlympique/web/sites")).build();
	}
}
