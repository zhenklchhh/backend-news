package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Session {
    private String sessionId;
    private int userId;
    private Timestamp sessionTime;
}
