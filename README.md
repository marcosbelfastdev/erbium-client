# Erbium API Tests

This repository contains API test collections and supporting resources
for **Erbium**.

------------------------------------------------------------------------

## 🚀 Quick Start

### Requirements

-   **Java 21**
    -   Make sure your IDE is configured for Java language level
        **21**.\
    -   Also ensure your project is set to use **Java 21**.
-   **Maven**
    -   Officially, **Maven 3.9.6+** is tested with and supports JDK
        21.\
    -   Older versions (≤ 3.8.x) may still run on Java 21, but are *not
        guaranteed* (some users hit issues with toolchains, TLS, or ASM
        dependencies).
    -   mvn clean install -DskipTests

------------------------------------------------------------------------

### Method 1 - Use the Cached Postman Collection

1.  Copy the file:

        src/test/java/resources/good_10891144-bbb2afb7-123c-4e52-aeb2-432e40ac004c

    to your local `.erbium` directory.

2.  Location of the `.erbium` directory by OS:

    -   **Windows**

            C:\Users\<your user profile here>\.erbium

        *(create the `.erbium` directory if it does not exist)*

    -   **macOS**

            /Users/<your user profile here>/.erbium

    -   **Linux**

            /home/<your user profile here>/.erbium

3.  By doing so, the tests will **never hit Postman** and will instead
    use the cached file.

------------------------------------------------------------------------

### Method 2 - Hit Your Own Postman Collection

1.  Import the collection file into Postman:

        src/test/java/resources/good_10891144-bbb2afb7-123c-4e52-aeb2-432e40ac004c

2.  Create the file `postman_key` containing your **Postman API Key**
    and place it in your user directory:

    -   **Windows**

            C:\Users\<your user profile here>

    -   **macOS**

            /Users/<your user profile here>

    -   **Linux**

            /home/<your user profile here>

3.  Replace the collection UID in your project with your own Postman
    collection UID:

    ``` java
    admin.common.factories.MasterFakeStoreFactory.FAKESTORE_COLLECTION_UID
    ```

------------------------------------------------------------------------

## 📂 Project Structure

-   `src/test/java/resources` --- Cached Postman collections and test
    data\
-   `.erbium` --- Local directory for storing cached collections

------------------------------------------------------------------------

## 📌 Notes

-   Use **Method 1** if you want a fast setup without Postman API
    calls.\
-   Use **Method 2** if you want to run tests directly against your own
    Postman collection.
