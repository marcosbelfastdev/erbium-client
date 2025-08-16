package admin.fakeStore.scripts.responses;

import br.com.erbium.core.Collection;
import br.com.erbium.core.EItem;
import br.com.erbium.core.EType;
import br.com.erbium.core.ResponseManager;
import br.com.erbium.core.base.scripts.ResponseScript;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import lombok.Getter;
import repositories.common.fakeStore.FakeStoreSchemas;

import java.util.Set;

public class SchemaValidation extends ResponseScript {

    @Getter
    Boolean isValid;
    String schemaJson;
    String endpointName;

    public SchemaValidation setSchema(String schemaJson, String endpointName) {
        this.schemaJson = schemaJson;
        this.endpointName = endpointName;
        return this;
    }

    public Boolean isValidSchema() {
        if (isValid == null) {
            exec();
        }
        if (isValid == null)
            return true;
        if (!isValid) {
            responseManager().out().log(EType.ERROR, EItem.MESSAGE, "The following schema belonging to endpoint " + endpointName + " is invalid:\n" + schemaJson);
        } else {
            responseManager().out().log(EType.SUCESS, EItem.MESSAGE, "SUCCESS");
        }
        return isValid;
    }

    @Override
    public ResponseScript exec() {
        run();
        return this;
    }

    @Override
    public void run() {

        if (schemaJson == null || schemaJson.isEmpty()) {
            responseManager().out().log(EType.LIGHT_WARNING, EItem.MESSAGE, "\nSchema not found for endpoint: " + endpointName);
            isValid = null;
            return;
        }

        String body = responseManager().getLastResponse().body();
                ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(body);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012);
        JsonSchema schema = factory.getSchema(schemaJson);

        Set<ValidationMessage> errors = schema.validate(jsonNode);
        isValid = errors.isEmpty();
        responseManager().out().log(EType.INFO, EItem.MESSAGE, "\nValidating schema for endpoint: " + endpointName);
    }
}
