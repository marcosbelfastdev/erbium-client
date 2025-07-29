public abstract class BaseModel {

    public String asPrettyString() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Convert JSON string to JsonNode
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(
                    UtilConvert.convertToJsonString(this)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Convert JsonNode to pretty JSON string
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String asFormattedTable() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Convert JSON string to JsonNode
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(
                    UtilConvert.convertToJsonString(this)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Convert JsonNode to pretty JSON string
        String jsonNodeString = null;
        try {
            jsonNodeString =  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try {
            return JsonTableFormatter.getFormattedJsonTable(jsonNodeString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BaseModel set(String field, Object value) {
        ReflectionSetter reflectionSetter = new ReflectionSetter(this);
        reflectionSetter.setField(field, value);
        return this;
    }

    public BaseModel setByConvention(String field, Object valor) {

        if (valor instanceof String) {
            if (UtilConvert.isValueIntendedToBeNull(valor)) {
                set(field, null);
                return this;
            }

            if (UtilConvert.isValueIntendedToBeString(valor)) {
                set(field, UtilConvert.removeAllQuotes(valor));
                return this;
            }

            if (base.components2.utils.UtilConvert.isValueIntendedToBeNumberWithInitialStar(valor)) {
                set(field, base.components2.utils.UtilConvert.removeInitialNumberEnforcementIndicatorStar(valor));
                return this;
            }

            if (UtilConvert.isValueDigitsOnly(valor)) {
                set(field, UtilConvert.convertToIntegerFirstOrLong(valor));
                return this;
            }

            if (UtilConvert.isValueIntendedToBeOneBlankSpace(valor)) {
                set(field, UtilConvert.getSingleSpace());
                return this;
            }

            if (UtilConvert.isValueIntendedToBeEmptyString(valor)) {
                set(field, UtilConvert.getEmptyString());
                return this;
            }
        }

        set(field, valor);
        return this;
    }
}
