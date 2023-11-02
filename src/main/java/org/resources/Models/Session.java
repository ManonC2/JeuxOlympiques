package org.resources.Models;

import java.sql.Time;
import java.util.Date;

public class Session {
	private int id;

	private String code;
	
	private Date date;
	
	private Time heureDebut;
	
	private Time heureFin;
	
	private String description;
	
	private Site site;
	
	private TypeSession typeSession;
	
	private Epreuve epreuve;
	
	public Session(String code, Date date, Time heureDebut, Time heureFin, String description, Site site,
			TypeSession typeSession, Epreuve epreuve) {
		super();
		this.code = code;
		this.date = date;
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
		this.description = description;
		this.site = site;
		this.typeSession = typeSession;
		this.epreuve = epreuve;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getHeureDebut() {
		return heureDebut;
	}

	public void setHeureDebut(Time heureDebut) {
		this.heureDebut = heureDebut;
	}

	public Time getHeureFin() {
		return heureFin;
	}

	public void setHeureFin(Time heureFin) {
		this.heureFin = heureFin;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public TypeSession getTypeSession() {
		return typeSession;
	}

	public void setTypeSession(TypeSession typeSession) {
		this.typeSession = typeSession;
	}

	public Epreuve getEpreuve() {
		return epreuve;
	}

	public void setEpreuve(Epreuve epreuve) {
		this.epreuve = epreuve;
	}
}
