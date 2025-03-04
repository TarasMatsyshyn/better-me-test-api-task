package api.models;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class InventoryResponse {
    private Map<String, Integer> inventory = new HashMap<>();

    @JsonAnySetter
    public void setInventory(String key, Integer value) {
        inventory.put(key, value);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}
