import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.InvalidJsonException;
import com.jayway.jsonpath.Option;

import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A utility class to safely handle Postman-style variables (e.g., {{variable}}) within a JSON string.
 * <p>
 * Postman allows variables to be used as raw values (e.g., `{"key": {{port}}}`), which is not valid JSON.
 * This class provides `normalize` and `denormalize` methods to convert such a string into a parsable
 * format and then restore it.
 */
public class PostmanJsonNormalizer {

    private static final Configuration JSON_PATH_VALIDATION_CONFIGURATION = Configuration.builder()
            .options(EnumSet.noneOf(Option.class))
            .build();

    /**
     * Checks if a given string is a valid JSON.
     * It attempts to parse the string using Jayway JsonPath's underlying JSON provider.
     *
     * @param jsonString The string to validate.
     * @return true if the string is a valid JSON, false otherwise.
     */
    public static boolean isValidJson(String jsonString) {
        if (jsonString == null || jsonString.trim().isEmpty()) {
            return false;
        }
        try {
            JSON_PATH_VALIDATION_CONFIGURATION.jsonProvider().parse(jsonString);
            return true;
        } catch (InvalidJsonException e) {
            return false;
        } catch (Exception e) {
            // Catch any other potential runtime exceptions during parsing
            return false;
        }
    }

    /**
     * Method for making postman json request a valid JSON.
     * This workaround is required for use of standard JSON libraries that manipulate JSON strings.
     * It adds double quotes around variables that have no double quotes around, such as: {{variable}}; and
     * adds one more pair of open/close curly braces around variables within existing double quotes such as: "{{string}}"
     * This effectively transforms:
     * {{variable}} into "{{variable}}"; and
     * "{{variable}}" into "{{{variable}}}"
     * @param json
     * @return
     */
    public static String normalize(String json) {
        // Regex for Postman variables already enclosed in double quotes (e.g., "{{variable}}")
        // These will be transformed to "{{{variable}}}" by replacing with "__variable__" temporarily.
        final String REGEX_QUOTED_POSTMAN_VARIABLE = "\"\\{\\{([a-zA-Z0-9_-]+)}}\"";
        Pattern quotedVarPattern = Pattern.compile(REGEX_QUOTED_POSTMAN_VARIABLE);
        Matcher quotedVarMatcher = quotedVarPattern.matcher(json);
        json = quotedVarMatcher.replaceAll("\"__$1__\"");

        // Regex for Postman variables not enclosed in double quotes (e.g., {{variable}})
        // These will be transformed to "{{variable}}" by replacing with "::variable::" temporarily.
        final String REGEX_UNQUOTED_POSTMAN_VARIABLE = "\\{\\{([a-zA-Z0-9_-]+)}}";
        Pattern unquotedVarPattern = Pattern.compile(REGEX_UNQUOTED_POSTMAN_VARIABLE);
        // Create a matcher for the input string
        Matcher unquotedVarMatcher = unquotedVarPattern.matcher(json);
        json = unquotedVarMatcher.replaceAll("\"::$1::\"");

        return json;
    }

    /**
     * Method for removing additional characters done by "normalize" method.
     * This is required for substitution of variables for literal values.
     * It removes double quotes around variables that have 2 pairs of curly braces: "{{variable}}"
     * and removes 1 pair of curly braces when there aree 3 pairs: "{{{string}}}"
     * This effectively transforms:
     * "{{variable}}" into {{variable}}; and
     * "{{{variable}}}" into "{{variable}}"
     * @param json
     * @return
     */
    public static String denormalize(String json) {
        // Regex to restore variables that were originally quoted (e.g., "{{variable}}")
        // These were temporarily "__variable__" and are now restored to "{{variable}}".
        final String REGEX_DENORMALIZE_QUOTED_VAR_TEMP = "\"__([a-zA-Z0-9_-]+)__\"";
        Pattern quotedVarTempPattern = Pattern.compile(REGEX_DENORMALIZE_QUOTED_VAR_TEMP);
        Matcher quotedVarTempMatcher = quotedVarTempPattern.matcher(json);
        json = quotedVarTempMatcher.replaceAll("\"\\{\\{$1}}\"");

        // Regex to restore variables that were originally unquoted (e.g., {{variable}})
        // These were temporarily "::variable::" and are now restored to {{variable}}.
        final String REGEX_DENORMALIZE_UNQUOTED_VAR_TEMP = "\"::([a-zA-Z0-9_-]+)::\"";
        Pattern unquotedVarTempPattern = Pattern.compile(REGEX_DENORMALIZE_UNQUOTED_VAR_TEMP);
        // Create a matcher for the input string
        Matcher unquotedVarTempMatcher = unquotedVarTempPattern.matcher(json);
        json = unquotedVarTempMatcher.replaceAll("\\{\\{$1}}");

        // This seems to be a duplicate of the previous denormalization step.
        // It's kept for now but might indicate a potential simplification.
        Pattern unquotedVarTempPatternInternal = Pattern.compile(REGEX_DENORMALIZE_UNQUOTED_VAR_TEMP);
        // Create a matcher for the input string
        Matcher unquotedVarTempMatcherInternal = unquotedVarTempPatternInternal.matcher(json);
        json = unquotedVarTempMatcherInternal.replaceAll("\\{\\{$1}}");

        return json;
    }
}