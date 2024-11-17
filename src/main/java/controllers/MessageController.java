package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sockets.ChatWebSocketServer;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/message"})
public class MessageController extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        JsonNode jsonNode = objectMapper.readTree(req.getReader());
        String message = jsonNode.get("message").asText();


        try {
            ChatWebSocketServer.sendGroupMessage(message);
            out.print("{\"status\": \"success\"}");
        } catch (IOException e) {
            out.print("{\"status\": \"error\", \"message\": \"" + e.getMessage() + "\"}");
        }
    }
}