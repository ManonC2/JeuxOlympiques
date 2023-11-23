package org.resources.Repositories;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.assets.DBManager;
import org.resources.Models.Discipline;

import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;

@Stateless
public class DisciplineRepository {
	
	Connection connection = DBManager.getInstance().getConnection();
	
	@Asynchronous
	public void add(Discipline discipline) {
	    String insertQuery = "insert into Discipline (nom, paralympique) VALUES ( '" + discipline.getNom() + "', "+ discipline.isParalympique() +")";
	    try {
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	@Asynchronous
	public void update(Discipline discipline) {
	    String insertQuery = "update Discipline set nom = '" + discipline.getNom() + "', paralympique = "+ discipline.isParalympique() +" where id = " + discipline.getId();
	    try {
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Asynchronous
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
	
	@Asynchronous
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
	
	@Asynchronous
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
	
	@Asynchronous
	public List<Discipline> findFiveLongestDisciplines() {
		
		List<Discipline> liste = new ArrayList<Discipline>();
		
		Statement statement;
		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery("SELECT d.id, SUM(TIMESTAMPDIFF(MINUTE, s.heureDebut, s.heureFin)) AS temps_total_epreuves FROM Session s JOIN Epreuve e ON s.epreuve_id = e.id JOIN Discipline d ON e.discipline_id = d.id GROUP BY e.discipline_id, d.nom ORDER BY temps_total_epreuves DESC LIMIT 5;");
			
			while(rs.next()) {
				int id = Integer.parseInt(rs.getString("id"));				
				liste.add(findById(id));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;	
	}
	
	@Asynchronous
	public List<Discipline> findDisciplineOnLongestDistance() {
		
		List<Discipline> liste = new ArrayList<Discipline>();
		
		Statement statement;
		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery("SELECT DISTINCT d.id, d.nom FROM Discipline d JOIN Epreuve e ON d.id = e.discipline_id JOIN Session s ON e.id = s.epreuve_id WHERE s.date < CURRENT_DATE OR (s.date = CURRENT_DATE AND s.heureFin < CURRENT_TIME) AND d.id IN (1, 5) ORDER BY d.nom DESC;");
			
			while(rs.next()) {
				int id = Integer.parseInt(rs.getString("id"));				
				liste.add(findById(id));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;	
	}
	
	
}
