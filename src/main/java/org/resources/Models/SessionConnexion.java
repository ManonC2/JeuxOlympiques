package org.resources.Models;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionConnexion {
    private static Map<String, Utilisateur> sessions = new HashMap<>();

    public static String connecterUtilisateur(Utilisateur utilisateur) {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, utilisateur);
        return sessionId;
    }

    public static Utilisateur getUtilisateur(String sessionId) {
    	Utilisateur utilisateur = sessions.get(sessionId);
        return utilisateur;
    }

    public static void deconnecterUtilisateur(String sessionId) {
        sessions.remove(sessionId);
    }
}
