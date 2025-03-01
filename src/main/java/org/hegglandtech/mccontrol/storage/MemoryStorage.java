package org.hegglandtech.mccontrol.storage;

import org.hegglandtech.mccontrol.utils.ServerLogger;

import java.util.ArrayList;
import java.util.List;

public class MemoryStorage extends Storage {

    private List<String> memory = new ArrayList<>();

    public MemoryStorage() {
        super();
    }

    public void loadMemory() {

        List<String> file = super.readFile();

        file.forEach(this::updateMemory);
    }

    public List<String> getMemory() {
        return this.memory;
    }

    // Deprecated method
    public String getMemory(boolean asString) {
        if (this.memory == null || this.memory.isEmpty()) {
            return null;
        }

        if (asString) {
            return String.join("\n", this.memory);
        }
        return this.memory.toString();
    }

    // Update memory by adding a new line if it doesn't exist
    public void updateMemory(String line) {
        if (!this.memory.contains(line)) {
            this.memory.add(line);
        }
    }

    public void clearMemory() {
        this.memory.clear();
    }


    @Override
    public String toString() {
        return "MemoryStorage{" +
                "memory=" + memory +
                '}';
    }
}
