package org.resources.Repositories;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.assets.DBManager;
import org.resources.Models.Session;

import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;

@Stateless
public class SessionRepository {
	Connection connection = DBManager.getInstance().getConnection();

	@Asynchronous
	public void add(Session session) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
	    String insertQuery = "insert into Session (code, date, heureDebut, heureFin, description, site_id, typeSession_id, epreuve_id) VALUES ('"+session.getCode()+"', '"+dateFormat.format(session.getDate())+"', '"+timeFormat.format(session.getHeureDebut())+"', '"+timeFormat.format(session.getHeureFin())+"', '"+session.getDescription()+"', "+session.getSite().getId()+", "+session.getTypeSession().getId()+", "+session.getEpreuve().getId()+")";
	    try {
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	@Asynchronous
	public void update(Session session) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
	    String insertQuery = "update Session set code = '"+session.getCode()+"', date = '"+dateFormat.format(session.getDate())+"', heureDebut = '"+timeFormat.format(session.getHeureDebut())+"', heureFin = '"+timeFormat.format(session.getHeureFin())+"', description = '"+session.getDescription()+"', site_id = "+session.getSite().getId()+", typeSession_id = "+session.getTypeSession().getId()+", epreuve_id = "+session.getEpreuve().getId()+" WHERE id = " + session.getId();
	    try {
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	@Asynchronous
	public void delete(int id) {
		String insertQuery = "delete from Session where id = " + id;
		try {
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	@Asynchronous
	public void deleteFromTypeSession(int typeSession_id) {
		String insertQuery = "delete from Session where typeSession_id = " + typeSession_id;
		try {
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	@Asynchronous
	public void deleteFromEpreuve(int epreuve_id) {
		String insertQuery = "delete from Session where epreuve_id = " + epreuve_id;
		try {
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	@Asynchronous
	public void deleteFromSite(int site_id) {
		String insertQuery = "delete from Session where site_id = " + site_id;
		try {
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	@Asynchronous
	public List<Session> findAll() {
		
		List<Session> liste = new ArrayList<Session>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Statement statement;
		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery("select * from Session");
			
			while(rs.next()) {
				int id = Integer.parseInt(rs.getString("id"));
				String code = rs.getString("code");
				Date date = sdf.parse(rs.getString("date"));
				Date heureDebut = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("heureDebut"));
				Date heureFin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("heureFin"));
				String description = rs.getString("description");
				
				int siteId = Integer.parseInt(rs.getString("site_id"));
				int typeSessionId = Integer.parseInt(rs.getString("typeSession_id"));
				int epreuveId = Integer.parseInt(rs.getString("epreuve_id"));
				
				SiteRepository siteRepo = new SiteRepository();
				TypeSessionRepository typeSessionRepo = new TypeSessionRepository();
				EpreuveRepository epreuveRepo = new EpreuveRepository();
				
				liste.add(new Session(id, code, date, heureDebut, heureFin, description, siteRepo.findById(siteId), typeSessionRepo.findById(typeSessionId), epreuveRepo.findById(epreuveId)));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(ParseException p) {
			p.printStackTrace();
		}
		return liste;
		
	}
	
	@Asynchronous
	public Session findById(int id) {
		Statement statement;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from Session where id = " + id);
			
			while(rs.next()) {
				String code = rs.getString("code");
				Date date = sdf.parse(rs.getString("date"));
				Date heureDebut = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("heureDebut"));
				Date heureFin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("heureFin"));
				String description = rs.getString("description");
				
				int siteId = Integer.parseInt(rs.getString("site_id"));
				int typeSessionId = Integer.parseInt(rs.getString("typeSession_id"));
				int epreuveId = Integer.parseInt(rs.getString("epreuve_id"));
				
				SiteRepository siteRepo = new SiteRepository();
				TypeSessionRepository typeSessionRepo = new TypeSessionRepository();
				EpreuveRepository epreuveRepo = new EpreuveRepository();
				
				return new Session(id, code, date, heureDebut, heureFin, description, siteRepo.findById(siteId), typeSessionRepo.findById(typeSessionId), epreuveRepo.findById(epreuveId));
			}
		}
		catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(ParseException p) {
			p.printStackTrace();
		}
		return null;
	}
}
