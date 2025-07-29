package user.utils;

public class JwtTokenGenerator {
    String jwtToken;
    public JwtTokenGenerator generateToken() {
        jwtToken = JwtTokenUtil.generateRandomJwtPreToken();
        return this;
    }
}
