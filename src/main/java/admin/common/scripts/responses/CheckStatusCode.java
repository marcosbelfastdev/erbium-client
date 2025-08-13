package admin.common.scripts.responses;

import br.com.erbium.core.base.scripts.ResponseScript;
import lombok.NonNull;

public class CheckStatusCode extends ResponseScript implements Runnable {

    Integer expectedStatusCode;
    Integer actualStatusCode;

    
    public boolean isStatusCode(@NonNull Integer expectedStatusCode) {
        return expectedStatusCode == actualStatusCode;
    }

    public boolean isStatusCodeInRange(@NonNull Integer min, @NonNull Integer max) {
        return min <= actualStatusCode && actualStatusCode <= max;
    }

    public boolean isStatusCodeAnyOf(@NonNull Integer... expectedStatusCodes) {
        for (Integer expectedStatusCode : expectedStatusCodes) {
            if (expectedStatusCode == actualStatusCode) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void run() {

    }

    @Override
    public CheckStatusCode exec() {
        return this;
    }
}
