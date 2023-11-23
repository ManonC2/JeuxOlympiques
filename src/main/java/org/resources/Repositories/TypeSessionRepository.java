package org.resources.Repositories;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.assets.DBManager;
import org.resources.Models.TypeSession;

import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;

@Stateless
public class TypeSessionRepository {
	Connection connection = DBManager.getInstance().getConnection();
	
	@Asynchronous
	public void add(TypeSession typeSession) {
	    String insertQuery = "insert into TypeSession (nom) VALUES ( '" + typeSession.getNom() + "')";
	    try {
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Asynchronous
	public void update(TypeSession typeSession) {
	    String insertQuery = "update TypeSession set nom = '" + typeSession.getNom() + "' where id = " + typeSession.getId();
	    try {
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Asynchronous
	public void delete(int id) {
		String insertQuery = "delete from TypeSession where id = " + id;
		try {
	    	connection.createStatement().execute(insertQuery);
	    	SessionRepository sessionRepository = new SessionRepository();
	    	sessionRepository.deleteFromTypeSession(id);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	@Asynchronous
	public List<TypeSession> findAll() {
		
		List<TypeSession> liste = new ArrayList<TypeSession>();
		
		Statement statement;
		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery("select * from TypeSession");
			
			while(rs.next()) {
				int id = Integer.parseInt(rs.getString("id"));
				String nom = rs.getString("nom");
	
				
				liste.add(new TypeSession(id, nom));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;
		
	}
	
	@Asynchronous
	public TypeSession findById(int id) {
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from TypeSession where id = " + id);
			
			while(rs.next()) {
				String nom = rs.getString("nom");
				return new TypeSession(id, nom);
			}
		}
		catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
