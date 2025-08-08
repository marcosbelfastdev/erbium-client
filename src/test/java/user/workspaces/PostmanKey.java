package user.workspaces;

import br.com.erbium.utils.FileRepositoryUtil;

import java.io.File;
import java.io.IOException;

public class PostmanKey {
    static String demoKey;

    public static synchronized String demoKey() {
        try {
            File file = FileRepositoryUtil.readFile(System.getProperty("user.home") + "/postman_key");
            demoKey = FileRepositoryUtil.readFile(file).trim();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return demoKey;
    }
}
