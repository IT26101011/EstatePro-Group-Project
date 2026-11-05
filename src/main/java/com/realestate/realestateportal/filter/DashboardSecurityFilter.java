package com.realestate.realestateportal.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class DashboardSecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        // Protect Client Dashboard
        if (path.equals("/client-dashboard.html")) {

            HttpSession session = request.getSession(false);

            if (session == null ||
                    !"CLIENT".equals(session.getAttribute("userRole"))) {

                response.sendRedirect("/login.html");
                return;
            }
        }

        // Protect Agent Dashboard
        if (path.equals("/agent-dashboard.html")) {

            HttpSession session = request.getSession(false);

            if (session == null ||
                    !"AGENT".equals(session.getAttribute("userRole"))) {

                response.sendRedirect("/login.html");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
