package org.hegglandtech.mccontrol.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Storage {

    private final File file;
    private final String filePath;


    // No-argument constructor that defaults to "mccontrol-config.txt"
    public Storage() {
        this("mccontrol-config.txt"); // Call the parameterized constructor with the default file path
    }

    public Storage(String filePath) {

        if (filePath == null || filePath.isEmpty()) {
            filePath = "mccontrol-config.txt";
        }

        // Get the directory where the plugin JAR is located
        String pluginDirectory = System.getProperty("user.dir") + File.separator + "plugins" + File.separator + "mccontrol"; // Path to /plugins/mccontrol
        File pluginFolder = new File(pluginDirectory);

        // Ensure the mccontrol folder exists
        if (!pluginFolder.exists()) {
            boolean folderCreated = pluginFolder.mkdirs();
            if (!folderCreated) {
                throw new RuntimeException("Failed to create mccontrol folder inside /plugins");
            }
        }

        this.file = new File(pluginFolder, filePath);
        this.filePath = filePath;

        createFile();

    }


    public void createFile() {
        try {
            if (!file.exists()) {
                if (file.getParentFile() != null && !file.getParentFile().exists()) {
                    boolean dirsCreated = file.getParentFile().mkdirs();
                    if (!dirsCreated) {
                        throw new IOException("Failed to create parent directories for file: " + filePath);
                    }
                }

                boolean fileCreated = file.createNewFile();
                if (!fileCreated) {
                    throw new IOException("Failed to create the file: " + filePath);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage file: " + filePath, e);
        }
    }

    /**
     * Writes data to the file. Creates the file if it does not exist.
     *
     * @param data The data to write to the file.
     */
    public void writeToFile(String data) {
        try {
            if (!file.exists()) {
                if (file.getParentFile() != null && !file.getParentFile().exists()) {
                    boolean dirsCreated = file.getParentFile().mkdirs();
                    if (!dirsCreated) {
                        throw new IOException("Failed to create parent directories for file: " + file.getPath());
                    }
                }

                boolean fileCreated = file.createNewFile();
                if (!fileCreated) {
                    throw new IOException("Failed to create the file: " + file.getPath());
                }
            }

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(data);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not write to file: " + file.getPath(), e);
        }
    }

    public boolean clearFile() {
        try {
            if (!file.exists()) {
                if (file.getParentFile() != null && !file.getParentFile().exists()) {
                    boolean dirsCreated = file.getParentFile().mkdirs();
                    if (!dirsCreated) {
                        throw new IOException("Failed to create parent directories for file: " + file.getPath());
                    }
                }

                boolean fileCreated = file.createNewFile();
                if (!fileCreated) {
                    throw new IOException("Failed to create the file: " + file.getPath());
                }
            }

            try (FileWriter writer = new FileWriter(file)) {
                writer.write("");
            }
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Could not write to file: " + file.getPath(), e);
        }
    }

    /**
     * Reads the content of the file. Creates the file if it does not exist.
     *
     * @return The content of the file as a String.
     */
    public String readFromFile() {
        try {
            if (!file.exists()) {
                if (file.getParentFile() != null && !file.getParentFile().exists()) {
                    boolean dirsCreated = file.getParentFile().mkdirs();
                    if (!dirsCreated) {
                        throw new IOException("Failed to create parent directories for file: " + file.getPath());
                    }
                }

                boolean fileCreated = file.createNewFile();
                if (!fileCreated) {
                    throw new IOException("Failed to create the file: " + file.getPath());
                }

                return ""; // Return empty string for a newly created file
            }

            return Files.readString(Path.of(file.getPath()));
        } catch (IOException e) {
            throw new RuntimeException("Could not read from file: " + file.getPath(), e);
        }

    }

    /**
     * Reads the file and returns its content as a List<String>, where each line is an element of the list.
     *
     * @return The content of the file as a List<String>.
     */
    protected List<String> readFile() {
        try {
            return Files.readAllLines(Path.of(file.getPath()));
        } catch (IOException e) {
            createFile();
        }
        return List.of();
    }
}
