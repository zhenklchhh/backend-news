package controllers;

import com.google.gson.Gson;
import com.nimbusds.jose.JOSEException;
import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jwt.JWTGenerator;
import lombok.extern.log4j.Log4j2;
import models.LoginData;
import models.User;

import java.io.BufferedReader;
import java.io.IOException;

@Log4j2
@WebServlet(urlPatterns = {"/login"})
public class AuthentificationController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String jsonBody = sb.toString();
        LoginData loginData = new Gson().fromJson(jsonBody, LoginData.class);
        log.info("Request to log in: {}", loginData);
        User loginUser = UserDao.getUser(loginData.getEmail());
        if (loginUser != null) {
            String jwtToken = null;
            try {
                jwtToken = JWTGenerator.signJWT(loginUser.getId());
                resp.setContentType("application/json");
                resp.getWriter().write("{\"token\": \"" + jwtToken + "\"}");
            } catch (JOSEException e) {
                log.error(e.getMessage());
            }
            resp.setContentType("application/json");
            resp.getWriter().write("{\"token\": \"" + jwtToken + "\"}");
            resp.setStatus(HttpServletResponse.SC_OK);
        }else{
            log.error("User not found");
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
