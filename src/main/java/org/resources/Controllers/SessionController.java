package org.resources.Controllers;

import java.io.IOException;


import java.io.StringWriter;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.resources.Models.CategorieSite;
import org.resources.Models.Epreuve;
import org.resources.Models.Session;
import org.resources.Models.SessionConnexion;
import org.resources.Models.Site;
import org.resources.Models.TypeSession;
import org.resources.Repositories.EpreuveRepository;
import org.resources.Repositories.SessionRepository;
import org.resources.Repositories.SiteRepository;
import org.resources.Repositories.TypeSessionRepository;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Stateless
@Path("/sessions")
public class SessionController {
	SessionRepository sessionRepository = new SessionRepository();
	SiteRepository siteRepository = new SiteRepository();
	TypeSessionRepository typeSessionRepository = new TypeSessionRepository();
	EpreuveRepository epreuveRepository = new EpreuveRepository();
	
	@Asynchronous
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/")
	public String hello(@CookieParam("sessionId") String sessionId) throws IOException {
		PebbleEngine engine = new PebbleEngine.Builder().build();
		PebbleTemplate compiledTemplate = engine.getTemplate("WEB-INF/views/sessions/session.html");
		
		List<Session> listeSessions = sessionRepository.findAll();
		

		Map<String, Object> context = new HashMap<>();
		context.put("sessions", listeSessions);
		StringWriter writer = new StringWriter();

		if(SessionConnexion.getUtilisateur(sessionId) != null){
			context.put("RoleCookie",SessionConnexion.getUtilisateur(sessionId).getRole().getId());
			context.put("Connecter", true);
		}
		else {
			context.put("Connecter", false);
		}
		compiledTemplate.evaluate(writer, context);


		String output = writer.toString();
		
		return output;
	}
	
	@Asynchronous
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/newSession")
	public String newSession() throws IOException {
		PebbleEngine engine = new PebbleEngine.Builder().build();
		PebbleTemplate compiledTemplate = engine.getTemplate("WEB-INF/views/sessions/newSession.html");
		
		List<Site> listeSites = siteRepository.findAll();
		List<TypeSession> listeTypeSession = typeSessionRepository.findAll();
		List<Epreuve> listeEpreuves = epreuveRepository.findAll();
		

		Map<String, Object> context = new HashMap<>();
		context.put("sites", listeSites);
		context.put("typeSessions", listeTypeSession);
		context.put("epreuves", listeEpreuves);
		
		StringWriter writer = new StringWriter();

		compiledTemplate.evaluate(writer, context);


		String output = writer.toString();
		
		return output;
	}
	
	@Asynchronous
	@POST
	@Path("/addSession")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response enregistrer(@FormParam("code") String code, @FormParam("date") String date,
			@FormParam("heureDebut") String heureDebut, @FormParam("heureFin") String heureFin,@FormParam("description") String description, @FormParam("site") int siteId, @FormParam("type") int typeSessionId, @FormParam("epreuve") int epreuveId) {

		
        String regex = "^[A-Z]{3}\\d{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(code);

        if (matcher.matches()) {
    		Date stringHeureDebut;
    		Date stringHeureFin;
    		Date stringDate;
    		try {
    			stringHeureDebut = (new SimpleDateFormat("yyyy-dd-MM HH:mm")).parse(date + " " + heureDebut);
                stringHeureFin = (new SimpleDateFormat("yyyy-dd-MM HH:mm")).parse(date + " " + heureFin);

    			stringDate = (new SimpleDateFormat("yyyy-dd-MM")).parse(date);
    			Site site = siteRepository.findById(siteId);
    			TypeSession typeSession = typeSessionRepository.findById(typeSessionId);
    			Epreuve epreuve = epreuveRepository.findById(epreuveId);
    			
    			Session session = new Session(code, stringDate, stringHeureDebut, stringHeureFin, description, site, typeSession, epreuve);

    			sessionRepository.add(session);

    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}        
        }
		return Response.seeOther(URI.create("http://localhost:8080/JeuxOlympique/web/sessions")).build();
	}
	
	@Asynchronous
	@GET
	@Path("/updateSession")
	@Consumes(MediaType.TEXT_PLAIN)
	public String updateSession(@QueryParam("id") String id) {
		
		Session session = sessionRepository.findById(Integer.parseInt(id));
		
		PebbleEngine engine = new PebbleEngine.Builder().build();
		PebbleTemplate compiledTemplate = engine.getTemplate("WEB-INF/views/sessions/newSession.html");
		
		List<Site> listeSites = siteRepository.findAll();
		List<TypeSession> listeTypeSession = typeSessionRepository.findAll();
		List<Epreuve> listeEpreuves = epreuveRepository.findAll();
		
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	    

		Map<String, Object> context = new HashMap<>();


	    context.put("date", dateFormat.format(session.getDate()));
	    context.put("heureDebut", timeFormat.format(session.getHeureDebut()));
	    context.put("heureFin", timeFormat.format(session.getHeureFin()));
		
		context.put("sites", listeSites);
		context.put("typeSessions", listeTypeSession);
		context.put("epreuves", listeEpreuves);
		context.put("session", session);
		
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
	@Path("/addUpdatedSession")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addUpdateSession(@FormParam("code") String code, @FormParam("date") String date,
			@FormParam("heureDebut") String heureDebut, @FormParam("heureFin") String heureFin,@FormParam("description") String description, @FormParam("site") int siteId, @FormParam("type") int typeSessionId, @FormParam("epreuve") int epreuveId, @QueryParam("id") String id) {

        String regex = "^[A-Z]{3}\\d{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(code);
		
		if(matcher.matches()) {
			Date stringHeureDebut;
			Date stringHeureFin;
			Date stringDate;
			try {
				stringHeureDebut = (new SimpleDateFormat("yyyy-dd-MM HH:mm")).parse(date + " " + heureDebut);
	            stringHeureFin = (new SimpleDateFormat("yyyy-dd-MM HH:mm")).parse(date + " " + heureFin);
				stringDate = (new SimpleDateFormat("yyyy-dd-MM")).parse(date);
				Site site = siteRepository.findById(siteId);
				TypeSession typeSession = typeSessionRepository.findById(typeSessionId);
				Epreuve epreuve = epreuveRepository.findById(epreuveId);
				
				Session session = new Session(Integer.parseInt(id), code, stringDate, stringHeureDebut, stringHeureFin, description, site, typeSession, epreuve);

				sessionRepository.update(session);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		return Response.seeOther(URI.create("http://localhost:8080/JeuxOlympique/web/sessions")).build();
	}
	
	@Asynchronous
	@GET
	@Path("/delete")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response deleteSession(@QueryParam("id") String id) {
		
		sessionRepository.delete(Integer.parseInt(id));
		
		return Response.seeOther(URI.create("http://localhost:8080/JeuxOlympique/web/sessions")).build();
	}
}
