package admin.common.scripts.responses;

import br.com.erbium.core.Endpoint;
import br.com.erbium.core.LogType;
import br.com.erbium.core.base.scripts.ResponseScript;
import lombok.NonNull;
import lombok.Setter;

public class CheckStatusCode extends ResponseScript implements Runnable {

    int code;
    @Setter
    Endpoint endpoint;

    public CheckStatusCode() {

    }

    private void log(String message) {
        responseManager().out().log(LogType.SUCESS, message);
    }

    public CheckStatusCode(@NonNull Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public boolean isStatusCode(@NonNull Integer expectedStatusCode) {
        boolean is = expectedStatusCode == getStatusCode();
        if (is) {
            log("Endpoint " + endpoint.name() + " returned expected status code  " + getStatusCode());
        } else {
            log("Endpoint " + endpoint.name() + " returned unexpected code " + getStatusCode());
        }
        return is;
    }

    public boolean isStatusCodeInRange(@NonNull Integer min, @NonNull Integer max) {
        boolean is = min <= getStatusCode() && getStatusCode() <= max;
        if (is) {
            log("Endpoint " + endpoint.name() + " returned code in expected range:  " + getStatusCode());
        } else {
            log("Endpoint " + endpoint.name() + " returned code out of expected range:  " + getStatusCode());
        }
        return is;
    }

    public boolean isStatusCodeAnyOf(@NonNull Integer... expectedStatusCodes) {
        for (Integer expectedStatusCode : expectedStatusCodes) {
            if (expectedStatusCode == getStatusCode()) {
                log("Endpoint " + endpoint.name() + " returned expected status code  " + getStatusCode());
                return true;
            }
        }
        log("Endpoint " + endpoint.name() + " returned unexpected code " + getStatusCode());
        return false;
    }

    public int getStatusCode() {
        int code = responseManager().getLastResponse().code();
        return code;
    }


    @Override
    public void run() {

    }

    @Override
    public CheckStatusCode exec() {
        return this;
    }
}
