import base.components.enums.WorkspaceAction;
import lombok.NonNull;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Workspace extends base.components.core.Workspace implements Serializable {

    public Workspace() {

    }

    @Override
    protected Collection createCollection(@NonNull String name) {
        return new Collection(name);
    }

    public List<String> getCollectionNames() {
        return super.getCollectionNames();
    }

    /**
     * Adds a new endpointsCollection to the workspace.
     * This operation will exit if the workspace is locked for registration.
     *
     * @param name The name of the endpointsCollection to add. Must not be null.
     * @return The newly added Collection object.
     */
    public Collection addCollection(@NonNull String name) {
        lockManager().exitIfLocked(WorkspaceAction.REGISTER);
        return (Collection) super.addCollection(name);
    }

    /**
     * Deletes a endpointsCollection from the workspace.
     * This operation will exit if the workspace is locked for unregistration.
     *
     * @param name The name of the endpointsCollection to delete. Must not be null.
     * @return The current Workspace instance after deletion.
     */
    public Workspace deleteCollection(@NonNull String name) {
        lockManager().exitIfLocked(WorkspaceAction.UNREGISTER);
       return (Workspace) super.deleteCollection(name);
    }

    public Collection getCollection(@NonNull String name) {
        return (Collection) super.getCollection(name);
    }

    /**
     * Returns a list of all collections in this workspace, correctly converted to the
     * concrete {@link Collection} type.
     *
     * @return A new list containing all {@link Collection} objects.
     */

    public LinkedList<Collection> getCollections() {
        return super.getBaseCollections().stream()
                .map(collection -> (Collection) collection)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public Workspace copy() {
        Workspace workspace = new Workspace();
        for (Collection collection : getCollections()) {
            Collection copiedCollection = collection.copy();
            workspace.addCollection(copiedCollection);
        }
        return workspace;
    }
}
