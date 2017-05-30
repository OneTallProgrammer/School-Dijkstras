import java.util.Scanner;
import navigation.*;

/**
 * Created by Acer Customer on 5/28/2017.
 */
public class Project3 {
    public static void main(String[] args) {
        printMenu();
        boolean notDone = true;
        Scanner scanner = new Scanner(System.in).useDelimiter("\\n");

        Graph graph = new Graph();

        while(notDone){
            System.out.print("Command: ");
            String input = scanner.next();
            input = input.toUpperCase().trim();

            switch(input){
                case "Q":
                    queryGraph(graph);
                    break;
                case "D":
                    findMinDistance(graph);
                    break;
                case "I":
                    insertRoadIntoGraph(graph);
                    break;
                case "R":
                    removeRoadFromGraph(graph);
                    break;
                case "H":
                    printMenu();
                    break;
                case "E":
                    notDone = false;
                    break;
                default:
                    System.out.println("Error: Invalid Selection");
                    break;
            }
        }

    }

    public static void printMenu(){
        System.out.println("Select a Command: \n" +
                           "Q Query the city information by entering the city code.\n" +
                           "D Find the minimum distance between two cities.\n" +
                           "I Insert a road by entering two city codes and distance.\n" +
                           "R Remove an existing road by entering two city codes.\n" +
                           "H Display this message.\n" +
                           "E Exit.\n");
    }

    public static void queryGraph(Graph graph){
        System.out.print("City Code: ");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();

        try {
            graph.getCityByCode(input).printInfo();
        } catch (NullPointerException e) {
            System.out.println("City Not Found");
        }
    }

    public static void insertRoadIntoGraph(Graph graph){
        Scanner scanner = new Scanner(System.in).useDelimiter("\\n");

        System.out.print("City Codes and Distance: ");
        String input = scanner.next();
        String[] roadData = input.trim().split("\\s+");

        int upperDistanceThreshhold = 1000;

        if(roadData.length != 3){
            System.out.println("Error: Invalid Argument Count \n" +
                    "Expected Format: SourceCityCode DestinationCityCode Distance");
        }
        else if(roadData[0].length() != 2){
            System.out.println("Error: Invalid Source City Code");
        }
        else if(roadData[1].length() != 2){
            System.out.println("Error: Invalid Destination City Code");
        }
        else{
            try {
                int distance = Integer.parseInt(roadData[2]);
                String source = roadData[0];
                String destination = roadData[1];

                if(distance >= upperDistanceThreshhold){
                    System.out.println("Warning: Excessive Road Length Detected (" + distance + ")");
                }

                graph.addRoadByCityCodes(source, destination, distance);
            } catch (NumberFormatException e) {
                System.out.println("Error: Non-numerical Distance Entered");
            }
        }
    }

    public static void removeRoadFromGraph(Graph graph){
        Scanner scanner = new Scanner(System.in).useDelimiter("\\n");

        System.out.print("City Codes: ");
        String input = scanner.next();
        String[] roadData = input.trim().split("\\s+");

        if(roadData.length != 2){
            System.out.println("Error: Invalid Argument Count \n" +
                               "Expected Format: SourceCityCode DestinationCityCode");
        }
        else if(roadData[0].length() != 2){
            System.out.println("Error: Invalid Source City Code");
        }
        else if(roadData[1].length() != 2){
            System.out.println("Error: Invalid Destination City Code");
        }
        else{
            String source = roadData[0];
            String destination = roadData[1];
            graph.removeRoadByCityCodes(source, destination);
        }
    }

    public static void findMinDistance(Graph graph){
        Scanner scanner = new Scanner(System.in).useDelimiter("\\n");

        System.out.print("City Codes: ");
        String input = scanner.next();
        String[] roadData = input.trim().split("\\s+");

        if(roadData.length != 2){
            System.out.println("Error: Invalid Argument Count \n" +
                    "Expected Format: SourceCityCode DestinationCityCode");
        }
        else if(roadData[0].length() != 2){
            System.out.println("Error: Invalid Source City Code");
        }
        else if(roadData[1].length() != 2){
            System.out.println("Error: Invalid Destination City Code");
        }
        else {
            City source = graph.getCityByCode(roadData[0]);
            City destination = graph.getCityByCode(roadData[1]);
            source.printPath(destination, graph);
        }
    }
}

