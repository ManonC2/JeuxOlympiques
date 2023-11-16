package org.resources.Models;

public class Utilisateur {
	private int id;
	
	private String password;
	
	private String email;
	
	private String nom;
	
	private String prenom;
	
	private RoleUtilisateur role;
	
	public Utilisateur(String password, RoleUtilisateur role) {
		super();
		this.password = password;
		this.role = role;
	}

	public Utilisateur(int id, String email, String nom, String prenom, RoleUtilisateur role) {
		super();
		this.id = id;
		this.email = email;
		this.nom = nom;
		this.prenom = prenom;
		this.role = role;
	}
		
	public Utilisateur(String password, String email, String nom, String prenom, RoleUtilisateur role) {
		super();
		this.password = password;
		this.email = email;
		this.nom = nom;
		this.prenom = prenom;
		this.role = role;
	}	

	public Utilisateur(int id, String password, RoleUtilisateur role) {
		super();
		this.id = id;
		this.password = password;
		this.role = role;
	}


	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public RoleUtilisateur getRole() {
		return role;
	}

	public void setRole(RoleUtilisateur role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
}
