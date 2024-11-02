package jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;

public class AuthorizationVerifier {
    private static final JWSVerifier jwsVerifier = new MACVerifier(KeyManager.decodeToBytes(SecretKey.SECRET_KEY));
    public static boolean verify(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                SignedJWT jwtToken = SignedJWT.parse(token);
                return jwtToken.verify(jwsVerifier);
            } catch (Exception e) {
                throw new RuntimeException("Invalid JWT token");
            }
        } else {
            throw new RuntimeException("Missing Authorization header");
        }
    }
}
