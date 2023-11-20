package org.resources.Controllers;

import java.io.IOException;

import java.io.StringWriter;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.resources.Models.CategorieEpreuve;
import org.resources.Models.CategorieSite;
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

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Hasher;
import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;


@Stateless
@Path("/users")
public class UtilisateurController {
	UtilisateurRepository utilisateurRepository = new UtilisateurRepository();
	RoleUtilisateurRepository roleUtilisateurRepository = new RoleUtilisateurRepository();
	
	@Asynchronous
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
	
	@Asynchronous
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

	@Asynchronous
	@POST
	@Produces(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/connexion")
	public Response connexion(@FormParam("password") String password, @FormParam("email") String email, @Context HttpServletRequest request, @Context UriInfo uriInfo) {

	    try {
	        UtilisateurRepository utilisateurRepository2 = new UtilisateurRepository();
	        Utilisateur user = utilisateurRepository2.findByEmailPassword(email, password);
	        if (user != null) {
	            HttpSession sessionUser = request.getSession(true);

	            sessionUser.setAttribute("userId", user.getId());
	            sessionUser.setAttribute("userEmail", user.getEmail());
	            sessionUser.setAttribute("userNom", user.getNom());
	            sessionUser.setAttribute("userPrenom", user.getPrenom());

	            sessionUser.setAttribute("userRole", user.getRole().getId()); 
	            
	            return Response.seeOther(uriInfo.getBaseUri().resolve("/JeuxOlympique/web/accueil")).build();
	        } else {
	            System.out.println("Authentication Fail");
	            return Response.seeOther(uriInfo.getBaseUri().resolve("/JeuxOlympique/web/connexion")).build();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return Response.serverError().build();
	    }
	}


	
	@Asynchronous
	@POST
	@Path("/addUser")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response enregistrerUsers(@FormParam("password") String password, @FormParam("email") String email,
			@FormParam("nom") String nom, @FormParam("prenom") String prenom, @FormParam("role") int roleId) {

		
		RoleUtilisateur roleUser = roleUtilisateurRepository.findById(roleId);
		
	    Hasher hasher = BCrypt.withDefaults();
	    String hashedPassword = hasher.hashToString(12, password.toCharArray());

	    
	    
		Utilisateur user= new Utilisateur(hashedPassword, email, nom, prenom, roleUser);

		utilisateurRepository.add(user);

		return Response.seeOther(URI.create("http://localhost:8080/JeuxOlympique/web/users")).build();

	}
	
	@Asynchronous
	@GET
	@Path("/update")
	@Consumes(MediaType.TEXT_PLAIN)
	public String update(@QueryParam("id") String id) {
		
		Utilisateur user = utilisateurRepository.findById(Integer.parseInt(id));
		
		PebbleEngine engine = new PebbleEngine.Builder().build();
		PebbleTemplate compiledTemplate = engine.getTemplate("WEB-INF/views/users/newUser.html");
		
		List<RoleUtilisateur> listeRolesUtilisateur = roleUtilisateurRepository.findAll();
		
		Map<String, Object> context = new HashMap<>();
		context.put("roles", listeRolesUtilisateur);
		context.put("user", user);
		
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
	@GET
	@Path("/formConnexion")
	@Consumes(MediaType.TEXT_PLAIN)
	public String update() {
				
		PebbleEngine engine = new PebbleEngine.Builder().build();
		PebbleTemplate compiledTemplate = engine.getTemplate("WEB-INF/views/users/connexion.html");
		
		List<RoleUtilisateur> listeRolesUtilisateur = roleUtilisateurRepository.findAll();
		
		Map<String, Object> context = new HashMap<>();
		context.put("roles", listeRolesUtilisateur);
	
		
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
	@Path("/addUpdatedUser")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addUpdatedUser(@FormParam("password") String password, @FormParam("email") String email,
			@FormParam("nom") String nom, @FormParam("prenom") String prenom, @FormParam("role") int roleId, @QueryParam("id") String id) {

		RoleUtilisateur roleUser = roleUtilisateurRepository.findById(roleId);
	    
		Utilisateur user= new Utilisateur(Integer.parseInt(id), email, nom, prenom, roleUser);

		utilisateurRepository.update(user);

		return Response.seeOther(URI.create("http://localhost:8080/JeuxOlympique/web/users")).build();

	}
	
	@Asynchronous
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/deconnexion")
	public String deconnexion() throws IOException {
		
		return "ok";
	}
	
	@Asynchronous
	@GET
	@Path("/delete")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response delete(@QueryParam("id") String id) {
		
		utilisateurRepository.delete(Integer.parseInt(id));
		
		return Response.seeOther(URI.create("http://localhost:8080/JeuxOlympique/web/users")).build();
	}
}
