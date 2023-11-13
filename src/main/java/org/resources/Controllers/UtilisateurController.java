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
import org.resources.Models.RoleUtilisateur;
import org.resources.Models.Session;
import org.resources.Models.Site;
import org.resources.Models.TypeSession;
import org.resources.Models.Utilisateur;
import org.resources.Repositories.CategorieEpreuveRepository;
import org.resources.Repositories.DisciplineRepository;
import org.resources.Repositories.EpreuveRepository;
import org.resources.Repositories.RoleUtilisateurRepository;
import org.resources.Repositories.UtilisateurRepository;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UtilisateurController {
	UtilisateurRepository utilisateurRepository = new UtilisateurRepository();
	RoleUtilisateurRepository roleUtilisateurRepository = new RoleUtilisateurRepository();
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/")
	public String hello() throws IOException {
		PebbleEngine engine = new PebbleEngine.Builder().build();
		PebbleTemplate compiledTemplate = engine.getTemplate("WEB-INF/views/users/users.html");
		
		List<Utilisateur> listeUtilisateurs = utilisateurRepository.findAll();
		List<RoleUtilisateur> listeRolesUtilisateurs = roleUtilisateurRepository.findAll();

		Map<String, Object> context = new HashMap<>();
		context.put("users", listeUtilisateurs);
		context.put("rolesUtilisateurs", listeRolesUtilisateurs);
		StringWriter writer = new StringWriter();

		compiledTemplate.evaluate(writer, context);


		String output = writer.toString();
		
		return output;
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/connexion")
	public String connexion() throws IOException {
		PebbleEngine engine = new PebbleEngine.Builder().build();
		PebbleTemplate compiledTemplate = engine.getTemplate("WEB-INF/views/users/connexion.html");
		
		Map<String, Object> context = new HashMap<>();
		
		StringWriter writer = new StringWriter();

		compiledTemplate.evaluate(writer, context);


		String output = writer.toString();
		
		return output;
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/newUser")
	public String newUser() throws IOException {
		PebbleEngine engine = new PebbleEngine.Builder().build();
		PebbleTemplate compiledTemplate = engine.getTemplate("WEB-INF/views/users/newUser.html");
		
		List<RoleUtilisateur> listeRolesUtilisateur = roleUtilisateurRepository.findAll();
		
		Map<String, Object> context = new HashMap<>();
		context.put("roles", listeRolesUtilisateur);
		
		StringWriter writer = new StringWriter();

		compiledTemplate.evaluate(writer, context);


		String output = writer.toString();
		
		return output;
	}
	
	@POST
	@Path("/addUser")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response enregistrerUsers(@FormParam("password") String password, @FormParam("email") String email,
			@FormParam("nom") String nom, @FormParam("prenom") String prenom, @FormParam("role") int roleId) {

		
		RoleUtilisateur roleUser = roleUtilisateurRepository.findById(roleId);

		Utilisateur user= new Utilisateur(password, email, nom, prenom, roleUser);

		utilisateurRepository.add(user);

		return Response.seeOther(URI.create("http://localhost:8080/JeuxOlympique/web/users")).build();

	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/deconnexion")
	public String deconnexion() throws IOException {
		
		return "ok";
	}
}
