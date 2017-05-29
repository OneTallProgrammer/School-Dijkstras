package navigation;

import Dijkstra.Heap;

import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by Acer Customer on 5/28/2017.
 */
public class Graph {
    ArrayList<Road> roads = new ArrayList<Road>();
    ArrayList<City> cities = new ArrayList<City>();
    boolean[][] adjacencyMatrix;

    public Graph(){
        this.loadRoads();
        this.loadCities();
        this.buildAdjacencies();
    }

    /*
    loads in road data from roads.txt
     */
    public void loadRoads() {
        File roadList = new File("./src/java/resources/roads.txt");
        String filePath = roadList.getPath();
        String line = null;

        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            //reads in single road data and adds a new road to the ArrayList roads
            while((line = bufferedReader.readLine()) != null) {
                String[] data = line.trim().split("\\s+");
                int source = Integer.parseInt(data[0]);
                int destination = Integer.parseInt(data[1]);
                int distance = Integer.parseInt(data[2]);

                roads.add(new Road(source, destination, distance));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /*
    loads in city data from cities.txt
     */
    public void loadCities() {
        File cityList = new File("./src/java/resources/cities.txt");
        String filePath = cityList.getPath();
        String line = null;

        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                String[] data = line.trim().split("\\s+");
                int key = Integer.parseInt(data[0]);
                String code = data[1];
                String name = data[2].replace("_", " "); // replace underscores with spaces
                int population = Integer.parseInt(data[3]);
                int elevation = Integer.parseInt(data[4]);

                cities.add(new City(key, code, name, population, elevation));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildAdjacencies() {
        adjacencyMatrix = new boolean[cities.size()][cities.size()];

        for(Road road: roads){
            int source = road.getSource();
            int destination = road.getDestination();

            // save the index associated with the road + 1 to avoid conflicts in the adjacency matrix
            adjacencyMatrix[source - 1][destination - 1] = true;
        }
    }

    public void addRoadByCityCodes(String sourceCode, String destCode, int distance){
        City source = getCityByCode(sourceCode);
        City destination = getCityByCode(destCode);

        if(source == null){
            System.out.println("Error: Source City not Found");
        }
        else if(destination == null){
            System.out.println("Error: Destination City not Found");
        }
        else if(adjacencyMatrix[source.getKey() - 1][destination.getKey() - 1] == true){
            System.out.println("Warning: There is alread a road from " + sourceCode +
                               " to " + destCode);
        }
        else {
            int sourceKey = getCityByCode(sourceCode).getKey();
            int destinationKey = getCityByCode(destCode).getKey();

            roads.add(new Road(sourceKey, destinationKey, distance));
            adjacencyMatrix[sourceKey - 1][destinationKey - 1] = true;
        }

        this.printAdjacencies();
    }

    public void removeRoadByCityCodes(String sourceCode, String destCode){
        City source = getCityByCode(sourceCode);
        City destination = getCityByCode(destCode);

        if(source == null){
            System.out.println("Error: Source City not Found");
        }
        else if(destination == null){
            System.out.println("Error: Destination City not Found");
        }
        else{
            int sourceKey = source.getKey();
            int destinationKey = destination.getKey();

            adjacencyMatrix[sourceKey - 1][destinationKey - 1] = false;
            purgeRoad(sourceKey, destinationKey);
        }
    }

    public void findMinimumPath(int sourceKey, int destinationKey){
        int[] cityKeys = new int[this.cities.size()];

        for(int i = 0; i < cityKeys.length; i++){
            cityKeys[i] = this.cities.get(i).getKey();
        }

        Heap queue = new Heap(sourceKey, cityKeys);
        String[] predecessors = new String[this.cities.size()];

        queue.printNodeIndices();
        queue.printNodes();

        int i = queue.pop().getCityKey() - 1;

        for(int j = 0; j < adjacencyMatrix[i].length; j++){
            if(adjacencyMatrix[i][j] == true){
                for(Road road: roads){
                    if(road.getSource() == i + 1 && road.getDestination() == j + 1){
                        System.out.println(road.getSource() + " " + road.getDestination() + " " + road.getDistance());
                        queue.updateDistance(road.getSource() + 1, road.getDestination(), road.getDistance());

                    }
                }
            }
        }




    }

    private void purgeRoad(int sourceKey, int destinationKey){
        for(int i = 0; i < roads.size(); i++){
            if(roads.get(i).getSource() == sourceKey && roads.get(i).getDestination() == destinationKey){
                roads.remove(i);
            }
        }

        this.printRoads();
        this.printAdjacencies();
    }

    /*
    returns a city from ArrayList cities with tthe given city code
    @param cityCode: the code being searched
    @return: the city with the matching city code
     */
    public City getCityByCode(String cityCode) {
        for(City city: this.cities){
            if(city.getCode().equals(cityCode.toUpperCase())){
                return city;
            }
        }

        return null;
    }

    public void printRoads(){
        for(Road road: roads){
            System.out.println("source: " + road.getSource() +
                               " destination: " + road.getDestination() +
                               " distance: " + road.getDistance());
        }
    }

    public void printCities(){
        for(City city: cities){
            System.out.println("key: " + city.getKey() +
                               " code: " + city.getCode() +
                               " name: " + city.getName() +
                               " pop: " + city.getPopulation() +
                               " elev: " + city.getElevation());
        }
    }

    public void printAdjacencies(){
        for(int i = 0; i < adjacencyMatrix.length; i++){
            for(int j = 0; j < adjacencyMatrix[i].length; j++){
                System.out.print(adjacencyMatrix[i][j] + " ");
            }
            System.out.println();
        }

    }
}
