package dao;

import models.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionDao extends DataAccessObject{

    public static void createSession(Session session) {
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO sessions (id, userid, expiresat) VALUES (?, ?, ?)");
        ) {
            final int sessionIndex = 1;
            final int userIdIndex = 2;
            final int expiresatIndex = 3;
            stmt.setString(sessionIndex, session.getSessionId());
            stmt.setInt(userIdIndex, session.getUserId());
            stmt.setTimestamp(expiresatIndex, session.getSessionTime());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Session getSession(String sessionId) {
        Session session = null;
        try (Connection connection = getConnection();
                PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM sessions WHERE id = ?")
        ) {
            final int sessionIdIndex = 1;
            stmt.setString(sessionIdIndex, sessionId);
            stmt.executeUpdate();
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                session = new Session(
                        rs.getString("id"),
                        rs.getInt("userid"),
                        rs.getTimestamp("expiresat")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return session;
    }

    public static void removeSession(String sessionId) {
        try (Connection connection = getConnection();
                PreparedStatement stmt = connection.prepareStatement(
                "DELETE FROM sessions WHERE id = ?");
        ) {
            final int sessionIdIndex = 1;
            stmt.setString(sessionIdIndex, sessionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
