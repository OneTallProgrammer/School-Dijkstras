package Dijkstra;

/**
 * Created by Acer Customer on 5/28/2017.
 */

public class Node {
    private int cityKey;
    private int distanceTo;

    public Node(int cityKey, int distanceTo){
        this.cityKey = cityKey;
        this.distanceTo = distanceTo;
    }

    public int getCityKey() {
        return this.cityKey;
    }

    public void setCityKey(int cityKey) {
        this.cityKey = cityKey;
    }

    public int getDistanceTo() {
        return distanceTo;
    }

    public void setDistanceTo(int distanceTo) {
        this.distanceTo = distanceTo;
    }
}
