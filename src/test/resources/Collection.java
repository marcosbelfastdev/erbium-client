import base.components.core.base.scripts.HeadersTrigger;
import base.components.core.user.interfaces.HeadersManagerOperator;
import base.components.enums.CollectionAction;
import lombok.NonNull;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

public class Collection extends base.components.core.Collection {

    /**
     * Constructs a new Collection with a default name.
     */
    public Collection() {
        super();
    }

    /**
     * Constructs a new Collection with the specified name.
     *
     * @param name The name of the endpointsCollection.
     */
    public Collection(@NonNull String name) {
        super(name);
    }

    /**
     * Sets the unique identifier (UUID) for this endpointsCollection.
     *
     * @param collectionUid The UUID to set for the endpointsCollection.
     * @return This Collection instance, for method chaining.
     */
    @Override
    public Collection setUUID(@NonNull String collectionUid) {
        // Delegates to the superclass method to set the UUID.
        super.setUUID(collectionUid);
        return this;
    }

    public Endpoint $(@NonNull String name) {
        // This method allows for shorthand access to endpoints by name.
        return getEndpoint(name);
    }

    @Override
    public Endpoint getEndpoint(@NonNull String name) { // protected method
        return (Endpoint) super.getEndpoint(name);
    }

//    Collection createCollection(@NonNull String name) {
//        // This method creates a new Collection instance with the specified name.
//        return new Collection(name);
//    }

    /**
     * Adds a new Endpoint to this endpointsCollection with the specified name.
     * This method ensures that no concurrent modification issues occur by locking.
     *
     * @param name The name of the new Endpoint.
     * @return The newly created Endpoint instance.
     */
    public Endpoint addEndpoint(@NonNull String name) { // protected method
        lockManager().exitIfLocked(CollectionAction.ADD_ENPOINT);
        Endpoint endpoint = new Endpoint(name);
        return (Endpoint) super.addEndpoint(endpoint);
    }

    /**
     * Adds an existing Endpoint object to this endpointsCollection.
     * This method ensures that no concurrent modification issues occur by locking.
     *
     * @param endpoint The Endpoint object to add.
     * @return The added Endpoint instance.
     */
    public Endpoint addEndpoint(@NonNull Endpoint endpoint) { // protected method
        lockManager().exitIfLocked(CollectionAction.ADD_ENPOINT);
        super.importEndpoint(endpoint);
        return endpoint;
    }

    /**
     * Imports a Postman endpointsCollection using its UID and an API key.
     * This method ensures that no concurrent modification issues occur by locking.
     *
     * @param collectionUid The UID of the Postman endpointsCollection to import.
     * @param apiKey The API key for Postman.
     * @return This Collection instance, for method chaining.
     */
    public Collection importPostManCollection(String collectionUid, String apiKey) {
        lockManager().exitIfLocked(CollectionAction.POSTMAN_IMPORT);
        return (Collection) super.importPostManCollection(collectionUid, apiKey);
    }

    /**
     * Imports a Postman endpointsCollection from a file.
     * This method ensures that no concurrent modification issues occur by locking.
     *
     * @param file The file containing the Postman endpointsCollection.
     * @param collectionUId The UID of the endpointsCollection within the file (if applicable).
     * @return This Collection instance, for method chaining.
     */
    public Collection importPostManCollection(File file, String collectionUId) {
        lockManager().exitIfLocked(CollectionAction.POSTMAN_IMPORT);
        return (Collection) super.importPostManCollection(file, collectionUId);
    }

    /**
     * Imports a single Endpoint object into this endpointsCollection.
     * This method ensures that no concurrent modification issues occur by locking.
     *
     * @param endpoint The Endpoint object to import.
     * @return This Collection instance, for method chaining.
     */
    public Collection importEndpoint(@NonNull Endpoint endpoint) { // protected method
        lockManager().exitIfLocked(CollectionAction.IMPORT_ENDPOINT);
        return (Collection) super.importEndpoint(endpoint);
    }

    /**
     * Imports multiple Endpoint objects into this endpointsCollection.
     * This method ensures that no concurrent modification issues occur by locking.
     *
     * @param endpoints An array of Endpoint objects to import.
     * @return This Collection instance, for method chaining.
     */
    public Collection importEndpoints(@NonNull Endpoint... endpoints) { // protected method
        lockManager().exitIfLocked(CollectionAction.IMPORT_ENDPOINT);
        return (Collection) super.importEndpoint(endpoints);
    }

    /**
     * Imports an Endpoint by its name from another endpointsCollection.
     * This method ensures that no concurrent modification issues occur by locking.
     *
     * @param collectionName The name of the endpointsCollection from which to import the endpoint.
     * @param name The name of the endpoint to import.
     * @return This Collection instance, for method chaining.
     */
    public Collection importEndpoint(@NonNull String collectionName, @NonNull String name) { // protected method
        lockManager().exitIfLocked(CollectionAction.IMPORT_ENDPOINT);
        return (Collection) super.importEndpoint(collectionName, name);
    }

    /**
     * Imports multiple Endpoints by their names from another endpointsCollection.
     * This method ensures that no concurrent modification issues occur by locking.
     *
     * @param collectionName The name of the endpointsCollection from which to import the endpoints.
     * @param names An array of names of the endpoints to import.
     * @return This Collection instance, for method chaining.
     */
    public Collection importEndpoints(@NonNull String collectionName, @NonNull String... names) { // protected method
        for (String name : names) {
            importEndpoint(collectionName, name);
        }
        return this;
    }
    /**
     * Imports the environment settings from another endpointsCollection identified by its name.
     *
     * @param collectionName The name of the endpointsCollection whose environment is to be imported.
     * @return This Collection instance, for method chaining.
     */
    public Collection importEnvironment(@NonNull String collectionName) { // protected method
        return (Collection) super.importEnvironment(collectionName);
    }

    /**
     * Imports the environment settings from another Collection instance.
     *
     * @param collection The Collection instance whose environment is to be imported.
     * @return This Collection instance, for method chaining.
     */
    public Collection importEnvironment(base.components.core.Collection collection) { // protected method
        return (Collection) super.importEnvironment(collection);
    }

    /**
     * Retrieves a list of all Endpoint objects within this endpointsCollection.
     *
     * @return A List of Endpoint objects.
     */
    public List<Endpoint> getEndpoints() { // protected method
        return super.getEndpoints()
                .stream().map(endpointEngine -> (Endpoint) endpointEngine).toList();
    }

    /**
     * Retrieves a list of all Endpoint names within this endpointsCollection.
     *
     * @return A List of String representing the names of the endpoints.
     */
    public List<String> getAllEndpointNames() { // protected method
        return super.getAllEndpointEngineNames();
    }

    /**
     * Deletes a specific endpoint from the endpointsCollection by its name.
     * This method ensures that no concurrent modification issues occur by locking.
     *
     * @param endpointName The name of the endpoint to delete.
     * @return This Collection instance, for method chaining.
     */
    public Collection deleteEndpoint(@NonNull String endpointName) {
        lockManager().exitIfLocked(CollectionAction.DELETE_ENDPOINT);
        return (Collection) super.deleteEndpoint(endpointName);
    }

    /**
     * Deletes multiple endpoints from the endpointsCollection by their names.
     * This method ensures that no concurrent modification issues occur by locking.
     *
     * @param endpointNames An array of names of the endpoints to delete.
     * @return This Collection instance, for method chaining.
     */
    public Collection deleteEndpoints(@NonNull String... endpointNames) { // protected method
        lockManager().exitIfLocked(CollectionAction.DELETE_ENDPOINT);
        return (Collection) super.deleteEndpoints(endpointNames);
    }

    /**
     * Sets the host for this endpointsCollection.
     * This method ensures that no concurrent modification issues occur by locking.
     *
     * @param host The host URL or IP address to set.
     * @return This Collection instance, for method chaining.
     */
    public Collection setHost(String host) {
        lockManager().exitIfLocked(CollectionAction.SET_COLLECTION_HOST);
        return (Collection) super.setHost(host);
    }

    /**
     * Returns to the parent Workspace of this endpointsCollection.
     *
     * @return The parent Workspace instance.
     */
    public Workspace backToWorkspace() {
        return (Workspace) workspace();
    }

    /**
     * Sets a variable in the endpointsCollection's environment.
     *
     * @param varName The name of the variable to set.
     * @param value The value to assign to the variable.
     * @return This Collection instance, for method chaining.
     */
    public Collection set(String varName, Object value) {
        super.collectionEnvironment().set(varName, value);
        return this;
    }

    public Object get(String varName) {
        return super.collectionEnvironment().get(varName);
    }
    /**
     * Executes custom code in the context of this Endpoint instance.
     * Useful for fluent API extensions or dynamic configurations.
     *
     * @param action A lambda expression or functional interface that receives this Endpoint.
     * @return The current Endpoint instance for fluent chaining.
     */
    public Collection exec(@NonNull java.util.function.Consumer<Collection> action) {
        action.accept(this);
        return this;
    }

    public Collection queueHeaderScripts(@NonNull String name, @NonNull HeadersTrigger script) {
        // This method queues header scripts on all endpoints in the endpointsCollection.
        getEndpoints().forEach(endpoint -> {
            endpoint.queueHeaderScript(name, script);
        });
        return this;
    }

    public Collection queueHeaderScripts(@NonNull String name, @NonNull Consumer<HeadersManagerOperator> consumer) {
        // This method queues header scripts on all endpoints in the endpointsCollection.
        getEndpoints().forEach(endpoint -> {
            endpoint.queueHeaderScript(name, consumer);
        });
        return this;
    }

    public Collection copy() {
        Collection collection = new Collection(name);
        for (Endpoint endpoint : getEndpoints()) {
            collection.addEndpoint(endpoint.copy());
        }
        return collection;
    }

}
