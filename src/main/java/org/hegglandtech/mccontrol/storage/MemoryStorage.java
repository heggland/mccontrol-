package org.hegglandtech.mccontrol.storage;

import java.util.ArrayList;
import java.util.List;

public class MemoryStorage extends Storage {

    private List<String> memory = new ArrayList<>();

    public MemoryStorage() {
        super();
    }

    public void loadMemory() {
        this.memory = super.readFile();
    }

    public List<String> getMemory() {
        return this.memory;
    }

    // Update memory by adding a new line if it doesn't exist
    public void updateMemory(String line) {
        if (!this.memory.contains(line)) {
            this.memory.add(line);
        }
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

    @Override
    public String toString() {
        return "MemoryStorage{" +
                "memory=" + memory +
                '}';
    }
}
