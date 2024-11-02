package jwt;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Log4j2
public class KeyManager {
    private static final String DIRECTORY = "src/main/resources/keys/";
    private static final String ENV_FILE_NAME = "secure_key";
    private static final String SECRET_KEY_ENV_VAR = "SECRET_KEY";

    public static void main(String[] args) throws IOException {
        generateSecretKey();
        byte[] secret = getSecretKeyBytes();
    }

    public static void generateSecretKey() throws IOException {
        byte[] keyBytes = new byte[32];
        new java.security.SecureRandom().nextBytes(keyBytes);
        String base64Key = Base64.getEncoder().encodeToString(keyBytes);
        Path envFile = Paths.get(ENV_FILE_NAME);
        Files.writeString(envFile, SECRET_KEY_ENV_VAR + "=" + base64Key);
    }

    public static byte[] getSecretKeyBytes() throws IOException {
        String base64Key = readSecretKeyFromEnv();
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        return keyBytes;
    }

    private static String readSecretKeyFromEnv() throws IOException {
        Path envFilePath = Paths.get(ENV_FILE_NAME);
        log.info(envFilePath.toString());
        try {
            String envContent = new String(Files.readAllBytes(envFilePath), "UTF-8");
            log.info(envContent);
            String[] envVars = envContent.split("\n");

            for (String envVar : envVars) {
                String[] parts = envVar.split("=");
                if (parts.length == 2 && parts[0].trim().equals(SECRET_KEY_ENV_VAR)) {
                    return parts[1].trim();
                }
            }
            throw new RuntimeException("Secret key not found in " + ENV_FILE_NAME);
        } catch (IOException e) {
            throw new IOException("Error reading file: " + ENV_FILE_NAME, e);
        }
    }
}