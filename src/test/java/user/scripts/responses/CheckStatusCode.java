package user.scripts.responses;

import br.com.erbium.core.base.scripts.ResponseScript;
import lombok.NonNull;

public class CheckStatusCode extends ResponseScript implements Runnable {

    Integer expectedStatusCode;
    Integer actualStatusCode;

    
    public boolean isStatusCode(@NonNull Integer expectedStatusCode) {
        return expectedStatusCode == actualStatusCode;
    }

    public void customMethod() {

    }


    @Override
    public void run() {

    }

    @Override
    public CheckStatusCode exec() {
        return this;
    }
}
