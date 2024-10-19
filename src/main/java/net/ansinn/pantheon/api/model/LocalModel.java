package net.ansinn.pantheon.api.model;

import net.ansinn.pantheon.Pantheon;
import net.ansinn.pantheon.PrivateConstants;
import net.ansinn.pantheon.api.web.ModelResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Represents a model hosted locally on the computer.
 * This is locked to the default language implementation to be used.
 */
public class LocalModel implements ModelRepresentation {

    private Process process;
    private final Thread threadContainer = new Thread(this::run);


    public LocalModel() {}

    @Override
    public void initialize() {
        // Roll up a brand-new thread for requests to our local model.
        threadContainer.start();
    }

    @Override
    public void terminate() {
        if (process != null)
            this.process.destroy();
    }

    @Override
    public ModelResponse prompt(String prompt, int maxTokens) {
        String url = "http://localhost:5000/generate";
        try (var client = HttpClients.createDefault()) {
            var post = new HttpPost(url);
            var entity = new StringEntity("{\"input\":\"" + prompt + "\", \"max_tokens\":" + maxTokens +"}");
            post.setEntity(entity);
            post.setHeader("Content-Type", "application/json");

            try (var response = client.execute(post)) {
                String responseBody = EntityUtils.toString(response.getEntity());

                return ModelRepresentation.GSON.fromJson(responseBody, ModelResponse.class);  // Process the response as needed
            }

        } catch (Exception ignored) {}
        return ModelResponse.EMPTY_RESPONSE;
    }


    public void run() {
        try {
            this.process = new ProcessBuilder(
                    PrivateConstants.VENV_PYTHON_PATH,
                    PrivateConstants.PYTHON_FILE
            ).redirectErrorStream(true).start();

            var istream = new InputStreamReader(process.getInputStream());
            var reader = new BufferedReader(istream);

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
