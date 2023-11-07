package org.resources.Controllers;

import java.io.IOException;
import java.io.StringWriter;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.resources.Models.Epreuve;
import org.resources.Models.Session;
import org.resources.Models.Site;
import org.resources.Models.TypeSession;
import org.resources.Repositories.EpreuveRepository;
import org.resources.Repositories.SessionRepository;
import org.resources.Repositories.SiteRepository;
import org.resources.Repositories.TypeSessionRepository;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/sessions")
public class SessionController {
	SessionRepository sessionRepository = new SessionRepository();
	SiteRepository siteRepository = new SiteRepository();
	TypeSessionRepository typeSessionRepository = new TypeSessionRepository();
	EpreuveRepository epreuveRepository = new EpreuveRepository();
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/")
	public String hello() throws IOException {
		PebbleEngine engine = new PebbleEngine.Builder().build();
		PebbleTemplate compiledTemplate = engine.getTemplate("WEB-INF/views/sessions/session.html");
		
		List<Session> listeSessions = sessionRepository.findAll();
		

		Map<String, Object> context = new HashMap<>();
		context.put("sessions", listeSessions);
		StringWriter writer = new StringWriter();

		compiledTemplate.evaluate(writer, context);


		String output = writer.toString();
		
		return output;
	}
	
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
}
