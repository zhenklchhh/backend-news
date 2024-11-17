package jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.util.Date;

public class AuthorizationVerifier {
    private static final JWSVerifier jwsVerifier = new MACVerifier(KeyManager.decodeToBytes(SecretKey.SECRET_KEY));
    public static boolean verify(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                SignedJWT jwtToken = SignedJWT.parse(token);
                // Проверка подписи
                if (!jwtToken.verify(jwsVerifier)) {
                    return false; // Подпись неверна
                }
                // Извлечение информации о времени истечения
                JWTClaimsSet claimsSet = (JWTClaimsSet) jwtToken.getJWTClaimsSet();
                Date expirationTime = claimsSet.getExpirationTime();

                // Проверка даты истечения
                if (expirationTime == null || expirationTime.before(new Date())) {
                    return false; // Токен просрочен
                }
                return true; // Токен действителен
            } catch (Exception e) {
                throw new RuntimeException("Invalid JWT token");
            }
        } else {
            throw new RuntimeException("Missing Authorization header");
        }
    }
}
