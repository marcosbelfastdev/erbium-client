package user.utils;

import base.components.core.Endpoint;
import base.components.core.RequestManager;
import base.components.core.base.scripts.RequestTrigger;

public class JwtTokenManager extends RequestTrigger {

    private JwtTokenGenerator jwtTokenGenerator;
    private Endpoint loginEndpoint;
    private int maxUses = 10;
    private int currentUses = 0;

    public JwtTokenManager setJwtTokenGenerator(int maxUses, JwtTokenGenerator jwtTokenGenerator, Endpoint loginEndpoint) {
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.maxUses = maxUses;
        return this;
    }

    @Override
    public Endpoint exec() {
        if (currentUses == maxUses) {
            loginEndpoint.set("jwtToken", jwtTokenGenerator.generateToken());
            loginEndpoint.submit();
            return ((RequestManager) requestManager()).parentEndpoint();
        }
        currentUses++;
        return ((RequestManager) requestManager()).parentEndpoint();
    }
}
