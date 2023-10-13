package org.assets;

import java.sql.Connection;

public class Constantes {
	public static Connection CONNEXION_BDD = DBManager.getInstance().getConnection();
}
