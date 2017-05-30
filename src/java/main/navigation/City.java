package navigation;

import Dijkstra.Node;

/**
 * Created by Acer Customer on 5/28/2017.
 */
public class City {
    private int key;
    private String code;
    private String name;
    private int population;
    private int elevation;
    private Node[] paths;

    public City(int key, String code, String name, int population, int elevation){
        this.key = key;
        this.code = code;
        this.name = name;
        this.population = population;
        this.elevation = elevation;
        this.paths = null;
    }

    public int getKey() {
        return key;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public int getElevation() {
        return elevation;
    }

    public void printInfo() {
        System.out.println(this.key + " " +
                this.code + " " +
                this.name + " " +
                this.population + " " +
                this.elevation);
    }

    public Node[] getPaths() {
        return paths;
    }

    public void setPaths(Node[] paths) {
        this.paths = paths;
    }

    public void printShortestDistances(){
        for(int i = 0; i < this.paths.length; i++){
            if(paths[i] == null){
                System.out.println("City is Unreachable");
            }
            else{
                System.out.println("Shortest Path to " + paths[i].getCityKey() + ": " + paths[i].getDistanceTo() +
                                   " Through " + paths[i].getPredecessor());
            }
        }
    }

    public void printPath(City destination, Graph map){
        if(this.paths == null){
            map.findMinimumPaths(this);
        }

        if(this.paths[destination.getKey() - 1] == null){
            System.out.println(this.name + " has no route to " + destination.getName());
        }
        else {
            printPathRecursively(destination.getKey(), map);
            System.out.println("Total Distance: " + paths[destination.getKey() - 1].getDistanceTo());
        }



    }

    public void printPathRecursively(int cityKey, Graph map){

        if(cityKey == this.key){
            return;
        }

        int predecessorKey = paths[cityKey - 1].getPredecessor();
        int distanceBetween = paths[cityKey - 1].getDistanceTo() - paths[predecessorKey - 1].getDistanceTo();

        printPathRecursively(predecessorKey, map);

        City current = map.getCityByKey(cityKey);
        City predecessor = map.getCityByKey(predecessorKey);

        System.out.println(predecessor.getName() + " - " + current.getName() + ": " +
                           distanceBetween + " miles");
    }
}
