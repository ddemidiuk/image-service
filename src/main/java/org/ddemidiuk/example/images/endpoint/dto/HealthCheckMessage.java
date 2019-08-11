package org.ddemidiuk.example.images.endpoint.dto;

/**
 * Transfer object for heath status.
 */
public class HealthCheckMessage {
    private String name;
    private String message;
    private boolean isHealth;

    public HealthCheckMessage() {
    }

    /**
     * Instantiate HealthCheckMessage object with parameters.
     * @param name     name of system
     * @param message  status message
     * @param isHealth status
     */
    public HealthCheckMessage(String name, String message, boolean isHealth) {
        this.name = name;
        this.message = message;
        this.isHealth = isHealth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isHealth() {
        return isHealth;
    }

    public void setHealth(boolean health) {
        isHealth = health;
    }
}
