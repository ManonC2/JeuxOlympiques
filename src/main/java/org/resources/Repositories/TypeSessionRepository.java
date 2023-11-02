package org.resources.Repositories;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.assets.DBManager;
import org.resources.Models.CategorieEpreuve;
import org.resources.Models.Site;
import org.resources.Models.TypeSession;

public class TypeSessionRepository {
Connection connection = DBManager.getInstance().getConnection();
	
	public void add(TypeSession typeSession) {
	    String insertQuery = "insert into TypeSession (nom) VALUES ( '" + typeSession.getNom() + "')";
	    try {
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

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
