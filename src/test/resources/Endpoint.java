import base.components.core.*;
import base.components.core.base.scripts.HeadersTrigger;
import base.components.core.Collection;
import base.components.core.user.interfaces.HeadersManagerOperator;
import base.components.enums.Method;
import base.components.enums.RequestType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class Endpoint extends base.components.core.Endpoint {

    @Getter(AccessLevel.PACKAGE) @Accessors(fluent = true)
    final Map<String, Consumer<HeadersManagerOperator>> headersPolicyScripts = new LinkedHashMap<>();

    public Endpoint() {
        super();
    }

    /**
     * Constructs an Endpoint with a specified name.
     *
     * @param name The name of the endpoint.
     */
    public Endpoint(@NonNull String name) {
        super(name);
    }

    public Endpoint setRequestType(RequestType requestType) {
        return (Endpoint) super.setRequestType(requestType);
    }

    public Object get(@NonNull String varName) {
        return super.get(varName);
    }

    public JsonRequest getJsonRequest() {
        return super.getJsonRequest();
    }

    public XmlRequestManager getXmlRequest() {
        return super.getXmlRequest();
    }

    /**
     * Sets the HTTP method for this endpoint.
     *
     * @param method The HTTP method as a String (e.g., "GET", "POST").
     * @return The current Endpoint instance for method chaining.
     */
    public Endpoint setMethod(@NonNull String method) {
        return (Endpoint) super.setMethod(method);
    }

    /**
     * Sets the HTTP method for this endpoint using the {@link Method} enum.
     *
     * @param method The HTTP method as a {@link Method} enum value.
     * @return The current Endpoint instance for method chaining.
     */
    public Endpoint setMethod(@NonNull Method method) {
        return (Endpoint) super.setMethod(method);
    }

    /**
     * Sets the host for this endpoint.
     *
     * @param host The host URL or IP address.
     * @return The current Endpoint instance for method chaining.
     */
    public Endpoint setHost(@NonNull String host) {
        return (Endpoint) super.setHost(host);
    }

    public String getHost() {
        requireCollectionDependency();
        return super.getHost();
    }

    /**
     * Sets the URL path for this endpoint.
     *
     * @param url The URL path.
     * @return The current Endpoint instance for method chaining.
     */
    public Endpoint setUrl(@NonNull String url) {
        return (Endpoint) super.url(url);
    }

    /**
     * Navigates back to the parent {@link Collection} of this endpoint.
     * Requires that this endpoint is part of a endpointsCollection.
     *
     * @return The parent Collection instance.
     * @throws IllegalStateException if the endpoint is not associated with a endpointsCollection.
     */
    public Collection backToCollection() {
        requireCollectionDependency();
        return (Collection) parentEndpointsCollection();
    }

    /**
     * Adds a header to this endpoint.
     *
     * @param name The name of the header.
     * @param value The value of the header.
     * @return The current Endpoint instance for method chaining.
     */
    public Endpoint addHeader(@NonNull String name, @NonNull String value) {
        super.buildEndpointChildDependencies();
        super.addHeader(name, value);
        return this;
    }

    /**
     * Adds a header to this endpoint with an optional type.
     *
     * @param name The name of the header.
     * @param value The value of the header.
     * @param type The type of the header (e.g., "text", "json").
     * @return The current Endpoint instance for method chaining.
     */
    public Endpoint addHeader(@NonNull String name, String value, String type) {
        super.buildEndpointChildDependencies();
        super.addHeader(name, value, "", type);
        return this;
    }

    /**
     * Adds a header to this endpoint with an optional type and description.
     *
     * @param name The name of the header.
     * @param value The value of the header.
     * @param type The type of the header (e.g., "text", "json").
     * @param description A description for the header.
     * @return The current Endpoint instance for method chaining.
     */

    public Endpoint addHeader(@NonNull String name, @NonNull String value, String type, String description) {
        super.buildEndpointChildDependencies();
        super.addHeader(name, value, type, description);
        return this;
    }

    public Endpoint removeHeader(@NonNull String name) {
        return (Endpoint) super.removeHeader(name);
    }

    public Endpoint setSslSecurity(SslSecurity sslSecurity) {
        super.sslSecurity(sslSecurity);
        return this;
    }

    /**
     * Executes custom code in the context of this Endpoint instance.
     * Useful for fluent API extensions or dynamic configurations.
     *
     * @param action A lambda expression or functional interface that receives this Endpoint.
     * @return The current Endpoint instance for fluent chaining.
     */
    public Endpoint exec(@NonNull java.util.function.Consumer<Endpoint> action) {
        action.accept(this);
        return this;
    }

    public Endpoint queueHeaderScript(@NonNull String name, @NonNull HeadersTrigger script) {
        return (Endpoint) super.queueHeaderTrigger(name, script);
    }

    public Endpoint queueHeaderScript(@NonNull HeadersTrigger script) {
        return queueHeaderTrigger(UUID.randomUUID().toString(), script);
    }

    public Endpoint queueHeaderScript(@NonNull String name, @NonNull Consumer<HeadersManagerOperator> consumer) {
        return (Endpoint) super.queueHeaderTrigger(name, consumer);
    }

    public Endpoint runHeaderScripts() {
        return (Endpoint) super.runHeaderTriggers();
    }

    public Endpoint copy() {
        Endpoint endpoint = new Endpoint(name);
        base.components.core.Endpoint endpointEngine = copy(endpoint);
        endpointEngine.parentEndpointsCollection(parentEndpointsCollection());
        return (Endpoint) endpointEngine;
    }

}
