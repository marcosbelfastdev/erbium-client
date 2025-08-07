package user.utils;

public class JwtTokenGenerator {
    String jwtToken;
    public String generateToken() {
        jwtToken = JwtTokenUtil.generateRandomJwtPreToken();
        return jwtToken;
    }
}
