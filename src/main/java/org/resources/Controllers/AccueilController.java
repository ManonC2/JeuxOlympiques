package org.resources.Controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.resources.Models.CategorieEpreuve;
import org.resources.Repositories.CategorieEpreuveRepository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/accueil")
public class AccueilController {
	private CategorieEpreuveRepository categorieEpreuveRepository = new CategorieEpreuveRepository();
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/hello")
	public String hello() {
		return "Hello World";
	}
	
	@GET
	@Path("/setBDD")
	public String setBDD() {
		
		
		categorieEpreuveRepository.createAndPopulate();
		
		return "ok";
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/categoriesEpreuves")
	public String getBooks() {
		List<CategorieEpreuve> liste = categorieEpreuveRepository.findAll();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		String json = gson.toJson(liste);
		
		return json;
	}
}
