package models;
/*
    This class main function is simply storing earthquake info in objects
    each object contains several info "the attributes"
    we will need this class's objects in another class's to be able to run the system we are working on
    we will use generic data structures to store these data later too
 */


public class Earthquake {
    //these are the attributes we will need according to the given pdf
    //they are private for privacy and we can use setters and getters to change anything (i don't think we'll need them)
    private String id; //this is how we will identify each earthquake
    private int time; //the time of when the earthquake happened
    private String description; //the description of the earthquake
    private double latitude; // helps us know where the earthquake is happening
    private double longitude; // helps us know where the earthquake is happening
    private double depth; // helps us know where the earthquake is happening
    private double magnitude; // helps us know where the earthquake is happening

    //a constructor to initialize objects
    public Earthquake(String id, int time, String description, double latitude, double longitude, double depth, double magnitude) {
        this.id = id;
        this.time = time;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.depth = depth;
        this.magnitude = magnitude;
    }

    //setters and getters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }
    //toString is made to show the info of an object
    //we will use it while sending messages a lot so we should structure it well as written in the pdf
    @Override
    public String toString() {
        return "Magnitude " + magnitude + " at " + description;
    }
}
