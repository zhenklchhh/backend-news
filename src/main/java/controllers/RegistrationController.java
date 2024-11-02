package controllers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dao.UserDao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jwt.JWTGenerator;
import lombok.extern.log4j.Log4j2;
import models.User;
import validators.PasswordValidator;
import validators.UsernameValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Base64;

@Log4j2
@WebServlet(urlPatterns = {"/registration"})
public class RegistrationController extends HttpServlet {

    @Override
    public void init() {
        byte[] keyBytes = new byte[32];
        String base64Key = Base64.getEncoder().encodeToString(keyBytes);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            BufferedReader reader = req.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            String jsonBody = sb.toString();
            User newUser = new Gson().fromJson(jsonBody, User.class);

            if(!PasswordValidator.isValidPassword(newUser.getPassword())){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                log.error("Password in unsafe! Use big, small letters and digits in your passoword");
                return;
            }
            if(!UsernameValidator.isValidUsername(newUser.getName())){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                log.error("Invalid username");
                return;
            }
            newUser = UserDao.createUser(newUser);
            String jwtToken = JWTGenerator.signJWT(newUser.getId());
            resp.setContentType("application/json");
            resp.getWriter().write("{\"token\": \"" + jwtToken + "\"}");
            log.info(jwtToken);
            log.info("Registratate successfully");
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (IOException | JsonSyntaxException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            log.error(e.getMessage());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log.error(e.getMessage());
        }
    }

}
