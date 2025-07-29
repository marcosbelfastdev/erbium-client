package user.utils;

import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class JwtTokenUtil {

    public static String generateRandomJwtPreToken() {
        String header = base64UrlEncode("{\"alg\":\"HS256\",\"typ\":\"JWT\"}");
        String payload = base64UrlEncode("{\"sub\":\"" + UUID.randomUUID() + "\",\"iat\":" + System.currentTimeMillis() / 1000 + "}");
        String signature = randomBase64UrlString(32);

        return String.format("%s.%s.%s", header, payload, signature);
    }

    private static String base64UrlEncode(String str) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(str.getBytes());
    }

    private static String randomBase64UrlString(int byteLength) {
        byte[] randomBytes = new byte[byteLength];
        ThreadLocalRandom.current().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}
