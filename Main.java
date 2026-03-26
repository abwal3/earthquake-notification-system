//importing these classes so we can use them in reading files (watchers and earthquakes)
import java.io.BufferedReader;//helps us reading line by line
import java.io.FileReader;//storing the files and put them in the buffered reader
import java.io.IOException;//this exception is needed so we can avoid the program crashing
import java.util.Scanner; //so i can get the name of the files
/*
this class contains
How we will read the info from the files we have
How we will convert the info into objects so we can use them
printing the messages using toString() we have already built for both classes
How we will calcaulate the distance between the users (watchers) and the earthquake, so we can know who we should notify
the main idea is converting the info into earthquake and watchers objects after that we will make a queue for each of them
in the end we will make an event queue which will contain the events like earthquake or if a watcher is added or leaving
this is too smart there is noway any student will think the same way ;(0_0);
 */
public class Main {
    public static void main(String[] args) {
        try {
            // 1. Load and Merge Events
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the name of the watcher-file: ");
            String watcherFileName = scanner.nextLine();
            System.out.println("Enter the name of the earthquake-file: ");
            String earthquakeFileName = scanner.nextLine();
            LinkedQueue<Event> watchers = doingWatcherFile(watcherFileName);
            LinkedQueue<Event> earthquakes = doingEarthquakeFile(earthquakeFileName);
            LinkedQueue<Event> theBigQ = mergeQ(watchers, earthquakes);

            // 2. Initialize simulation state
            DoublyLinkedList<Watcher> activeWatchers = new DoublyLinkedList<>();
            DoublyLinkedList<Earthquake> historyList = new DoublyLinkedList<>();
            double currentTime = 0;

            // 3. The Simulation Loop
            while (!theBigQ.isEmpty()) {
                Event currentEvent = theBigQ.dequeue();
                currentTime = currentEvent.getTimestamp(); // Advance the clock ⏱️

                processEvent(currentEvent, activeWatchers, historyList, currentTime);
            }
        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
        }
    }

    /**
     * Parses the watcher-file into a Queue of Events
     */
    public static LinkedQueue<Event> doingWatcherFile(String fileName) throws IOException {
        //making an event queue
        LinkedQueue<Event> queue = new LinkedQueue<>();
        //bufferedReader to calculate the input
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        //here the line will be stored
        String line;
        //readin the file line by line
        while ((line = br.readLine()) != null) {
            line = line.trim(); // Remove leading/trailing whitespace
            if (line.isEmpty()) {
                continue; // Skip this iteration if the line is blank
            }
            //splitting the line into useful info
            String[] parts = line.split("\\s+");// "\\s+" means 1 or more white spaces

            //storing the time first
            double time = Double.parseDouble(parts[0]);
            //here is the command
            String command = parts[1];
            //here is the process if we need to add
            if (command.equals("add")) {
                //storing the info
                double lat = Double.parseDouble(parts[2]);
                double lon = Double.parseDouble(parts[3]);
                String name = parts[4];
                //now making the object
                Watcher w = new Watcher(name, lat, lon);
                //storing the event inside the queue
                queue.enqueue(new Event(time, Event.ADD, w));
            } else if (command.equals("delete")) {//if the command is deleting
                String name = parts[2];//storing the name
                queue.enqueue(new Event(time, Event.DELETE, name));//deleting it
            } else if (command.equals("query-largest")) {//the last command (finally)
                //storing the object too
                queue.enqueue(new Event(time, Event.QUERY, null));
            }
        }
        br.close();//closing the bufferedReader because I'm a good guy
        return queue;//returning the queue we made
    }

    /**
     * Merges two sorted queues into one master timeline
     * good planning makes the work easier and more organized
     */
    public static LinkedQueue<Event> mergeQ(LinkedQueue<Event> q1, LinkedQueue<Event> q2) {
        //making the queue
        LinkedQueue<Event> merged = new LinkedQueue<>();
        while (!q1.isEmpty() || !q2.isEmpty()) {//checking if it's empty
            //if one of the queues is empty we will basicly but the other queue in the main timeline
            if (q1.isEmpty()) {
                merged.enqueue(q2.dequeue());
            } else if (q2.isEmpty()) {
                merged.enqueue(q1.dequeue());
            } else {//if they are both not empty
                // Peek at timestamps to choose the earliest
                if (q1.first().getTimestamp() <= q2.first().getTimestamp()) {
                    //add this to the merged
                    merged.enqueue(q1.dequeue());
                } else {
                    //or this
                    merged.enqueue(q2.dequeue());
                }
            }
        }
        return merged;//returning the queue we made
    }

    //this function job is getting the info from the file (earthquake file) then turning it to a queue
    //because it was a complicated parsing process i had to make another helper method we will see it soon
    public static LinkedQueue<Event> doingEarthquakeFile(String fileName) throws IOException {
        //this is the queue we will put the objects in
        LinkedQueue<Event> queue = new LinkedQueue<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));//making a bufferReader and handel the file name
        String line;//here the lines are being stored

        while ((line = br.readLine()) != null) {//reading the lines
            line = line.trim();//making the string cleaner to be able to get the data from the line without extra spaces etc...
            if (line.equals("<earthquake>")) {
                // We found the start of an earthquake block
                // Now we need to read the specific fields until we hit </earthquake>

                //the helper function will delete the tags for us
                //getting the info and storing it into variables
                String id = rmvTag(br.readLine(), "id"); //thank u helper function (>_<)
                int time = Integer.parseInt(rmvTag(br.readLine(), "time"));
                String description = rmvTag(br.readLine(), "place");

                // Next is coordinates example: (0_0) <coordinates> -115.5808, 33.0187, 9.5 </coordinates>
                //first we have to remove the tags and split the line to 3 strings
                //after that each string will be stored as a double variable we will use later
                String coordLine = rmvTag(br.readLine(), "coordinates");
                String[] parts = coordLine.split(",");
                double lat = Double.parseDouble(parts[0].trim());
                double lon = Double.parseDouble(parts[1].trim());
                double depth = Double.parseDouble(parts[2].trim());
                //getting the magnitude
                double magnitude = Double.parseDouble(rmvTag(br.readLine(), "magnitude"));

                // Skip the closing </earthquake> tag
                br.readLine();
                //making an object and store the info we extracted earlier in it
                Earthquake eq = new Earthquake(id, time, description, lat, lon, depth, magnitude);
                //putting it in the queue we made earlier
                queue.enqueue(new Event(time, Event.EARTHQUAKE, eq));
            }
        }
        br.close();//closing the reader as always
        return queue;//returning the queue because we made the whole function to get this queue
        //it's 2 functions only for this file (_o_) <---- this is me sleeping after i wrote these comments
    }
    //this is a helper method for extracing the date from the earthquake file
    //we give the line and the tage and get it back without tags
    //I did 3 functions and deleted them because they were not working
    private static String rmvTag(String line, String tag) {
        // We create the tags based on the string passed in
        String openTag = "<" + tag + ">";
        String closeTag = "</" + tag + ">";

        // We replace the tags with an empty string to "remove" them
        return line.replace(openTag, "").replace(closeTag, "").trim();
    }

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        return Math.sqrt(Math.pow(lat1 - lat2, 2) + Math.pow(lon1 - lon2, 2));
    }

    private static void processEvent(Event event, DoublyLinkedList<Watcher> watchers,
                                     DoublyLinkedList<Earthquake> history, double time) {
        switch (event.getType()) {//always use switch case instead of if else; it looks cleaner and easier to understand

            case Event.ADD:
                // 1. Get the watcher from the payload
                Watcher wAdd = (Watcher) event.getPayload();
                // 2. Add them to the list
                watchers.addLast(wAdd);
                // 3. Print the confirmation message
                System.out.println(wAdd.getName() + " is added to the watcher-list");
                System.out.println(); // Adding a blank line for readability as seen in the example
                break;

            case Event.DELETE:
                // 1. Get the name of the watcher to be removed
                String nameToDelete = (String) event.getPayload();
                // 2. Remove them from the list
                watchers.removeByName(nameToDelete);
                // 3. Print the confirmation message
                System.out.println(nameToDelete + " is removed from the watcher-list");
                System.out.println();
                break;

            case Event.EARTHQUAKE://when an earthquake happens
                Earthquake eq = (Earthquake) event.getPayload();

                // 1. Cleaning up history (6-hour rule)
                while (!history.isEmpty() && history.first().getTime() < (time - 6)) {
                    history.removeFirst();
                }

                // 2. Print the insertion message to match requirements
                System.out.println("Earthquake " + eq.getDescription() + " is inserted into the earthquake-list");

                // 3. Calculate the specific notification threshold
                double threshold = 2 * Math.pow(eq.getMagnitude(), 3);

                // 4. Trigger the notifications within the list
                watchers.notifications(eq, threshold);

                // 5. Add the new earthquake to history
                history.addLast(eq);
                break;

            case Event.QUERY:
                history.handleQuery();//this method is in the doubly linked list class too
                System.out.println();
                break;//this is to match the output you gave us (i do care about details)
        }
    }

}