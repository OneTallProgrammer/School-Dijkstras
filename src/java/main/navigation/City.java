package navigation;

/**
 * Created by Acer Customer on 5/28/2017.
 */
public class City {
    private int key;
    private String code;
    private String name;
    private int population;
    private int elevation;

    public City(int key, String code, String name, int population, int elevation){
        this.key = key;
        this.code = code;
        this.name = name;
        this.population = population;
        this.elevation = elevation;

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
}
