package admin.common.scripts.responses;

import br.com.erbium.core.base.scripts.ResponseScript;
import lombok.NonNull;

public class CheckStatusCode extends ResponseScript implements Runnable {

    public boolean isStatusCode(@NonNull Integer expectedStatusCode) {
        return expectedStatusCode == getStatusCode();
    }

    public boolean isStatusCodeInRange(@NonNull Integer min, @NonNull Integer max) {
        return min <= getStatusCode() && getStatusCode() <= max;
    }

    public boolean isStatusCodeAnyOf(@NonNull Integer... expectedStatusCodes) {
        for (Integer expectedStatusCode : expectedStatusCodes) {
            if (expectedStatusCode == getStatusCode()) {
                return true;
            }
        }
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
