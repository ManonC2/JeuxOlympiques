package org.resources.Repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.assets.DBManager;
import org.resources.Models.Discipline;
public class DisciplineRepository {
	
	Connection connection = DBManager.getInstance().getConnection();
	
	
	public void add(Discipline discipline) {
	    String insertQuery = "insert into Discipline (nom, paralympique) VALUES ( '" + discipline.getNom() + "', "+ discipline.isParalympique() +")";
	    try {
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void update(Discipline discipline) {
	    String insertQuery = "update Discipline set nom = '" + discipline.getNom() + "', paralympique = "+ discipline.isParalympique() +" where id = " + discipline.getId();
	    try {
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public void delete(int id) {
		String insertQuery = "delete from Discipline where id = " + id;
		try {
	    	connection.createStatement().execute(insertQuery);	
	    	EpreuveRepository epreuveRepository = new EpreuveRepository();
	    	epreuveRepository.deleteFromDiscipline(id);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public Discipline findById(int id) {
		
		Statement statement;
		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery("select * from Discipline where id = " + id);
			
			while(rs.next()) {
				String nom = rs.getString("nom");
				boolean paralympique = (Integer.parseInt(rs.getString("paralympique")) == 1) ? true : false;
				return new Discipline(id, nom, paralympique);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Discipline> findAll() {
		
		List<Discipline> liste = new ArrayList<Discipline>();
		
		Statement statement;
		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery("select * from Discipline");
			
			while(rs.next()) {
				int id = Integer.parseInt(rs.getString("id"));
				String nom = rs.getString("nom");
				boolean paralympique = (Integer.parseInt(rs.getString("paralympique")) == 1) ? true : false;
	
				
				liste.add(new Discipline(id, nom, paralympique));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;
		
	}
	
}
