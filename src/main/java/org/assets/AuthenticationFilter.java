package org.assets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.resources.Models.Utilisateur;

import com.mitchellbosecke.pebble.extension.Filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/web/*")
public class AuthenticationFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
       /* HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (isValidUser(username, password)) {
            HttpSession session = httpRequest.getSession();
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setUsername(username);
            utilisateur.setRole("ROLE_USER");
            session.setAttribute("utilisateur", utilisateur);
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect("/login?error");
        }*/
    }

	@Override
	public List<String> getArgumentNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object apply(Object arg0, Map<String, Object> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

    // ... autres méthodes de l'interface Filter (init, destroy)
}
