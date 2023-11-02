package org.resources.Repositories;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.assets.DBManager;
import org.resources.Models.CategorieEpreuve;
import org.resources.Models.Epreuve;
import org.resources.Models.Session;
import org.resources.Models.Site;
import org.resources.Models.TypeSession;

public class SessionRepository {
Connection connection = DBManager.getInstance().getConnection();

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
				Time heureDebut = new Time(sdf.parse(rs.getString("heureDebut")).getTime());
				Time heureFin = new Time(sdf.parse(rs.getString("heureFin")).getTime());
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
	
	public Session findById(int id) {
		Statement statement;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from Session where id = " + id);
			
			while(rs.next()) {
				String code = rs.getString("code");
				Date date = sdf.parse(rs.getString("date"));
				Time heureDebut = new Time(sdf.parse(rs.getString("heureDebut")).getTime());
				Time heureFin = new Time(sdf.parse(rs.getString("heureFin")).getTime());
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
