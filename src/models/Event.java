/**
 * Represents a simulation event (Watcher update, Earthquake, or Query).
 * This class uses static integer constants to define event types.
 */
public class Event {
    // 1. Define constants for event types
    // These act as labels for our switch statement in the Main class.
    public static final int ADD = 0;         // New watcher joined
    public static final int DELETE = 1;      // Watcher left
    public static final int EARTHQUAKE = 2;  // Earthquake occurred
    public static final int QUERY = 3;       // Request for largest earthquake

    private double timestamp; // The time the event occurs
    private int type;         // The category of the event (ADD, DELETE, etc.)
    private Object payload;   // The data associated with the event (Watcher, Earthquake, or Name)

    /**
     * Constructs a new Event.
     * @param timestamp The time the event happens.
     * @param type The integer constant representing the type.
     * @param payload The object data for this event.
     */
    public Event(double timestamp, int type, Object payload) {
        this.timestamp = timestamp;
        this.type = type;
        this.payload = payload;
    }

    // 3. Getters to access the event data

    public double getTimestamp() {
        return timestamp;
    }

    public int getType() {
        return type;
    }

    public Object getPayload() {
        return payload;
    }
}
