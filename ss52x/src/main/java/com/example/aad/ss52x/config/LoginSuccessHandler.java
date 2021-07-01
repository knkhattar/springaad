package com.example.aad.ss52x.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import net.minidev.json.JSONArray;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		JSONArray roles = (JSONArray) ((OidcUser) authentication.getPrincipal()).getIdToken().getClaims().get("roles");
		boolean hasValidRole = roles != null ? roles.contains("role2") : false;

		if (hasValidRole) {
			super.onAuthenticationSuccess(request, response, authentication);
			System.out.println("hasRole1::" + hasValidRole);
		} else {
			System.out.println("hasRole2::" + hasValidRole);
			HttpSession session = request.getSession(false);
			System.out.println("session::" + session);
			SecurityContextHolder.clearContext();
			if (session != null) {
				session.invalidate();
			}
			response.sendRedirect("/error.jsp");
		}
	}

}
