/*
this class stores watchers and their locations with private attributes
setters and getters and to string to be able to print the messages
 */
public class Watcher {
    //attributes
    String name; //the name of the watcher
    double latitude; // for location info
    double longitude; // for location info

    // a constructor to initialize objects
    public Watcher(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    //setters and getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    //toString to print the message later
    @Override
    public String toString() {
        return name; // Returns just the name for easier message building
    }

    //add watcher
    public static void addWatcher(DoublyLinkedList<Watcher> watchers, Watcher watcher){
        //adding to the list
        watchers.addLast(watcher);
    }

    //remove watcher
    //the input is the name of the watcher we will delete and the list
    public static void removeWatcher(DoublyLinkedList<Watcher> watchers, String name){
        //checking if the watchers list is empty to avoid errors
        if (watchers.isEmpty())
            System.out.println( "No watchers to remove.");
        else{
            //this function i wrote in doublyLinkedList class you can check it at the end of the class
            watchers.removeByName(name);
        }
    }
}
