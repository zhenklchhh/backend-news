import jwt.KeyManager;

import java.io.IOException;

public class Example {
    public static void main(String[] args) throws IOException {
        byte[] secret = KeyManager.getSecretKeyBytes();
    }
}
