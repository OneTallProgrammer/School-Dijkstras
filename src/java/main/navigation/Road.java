package navigation;

/**
 * Created by Acer Customer on 5/28/2017.
 */
public class Road {
    //source and destination are identified by city key
    private int source;
    private int destination;
    private int distance;

    public Road(int source, int destination, int distance){
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

    public int getSource() {
        return source;
    }

    public int getDestination() {
        return destination;
    }

    public int getDistance() {
        return distance;
    }
}
