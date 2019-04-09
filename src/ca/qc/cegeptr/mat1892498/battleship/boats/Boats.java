package ca.qc.cegeptr.mat1892498.battleship.boats;

import java.util.ArrayList;

public enum Boats {

    CARRIER(5, "Carrier"),
    BATTLESHIP(4, "Battleship"),
    CRUISER(3, "Cruiser"),
    SUBMARINE(3, "Submarine"),
    DESTROYER(2, "Destroyer");

    private final int width;
    private final String name;
    private final Dirs direction;
    private static ArrayList<String> boatPos = new ArrayList<>();

    Boats(int width, String name){
        this.width = width;
        this.name = name;
        this.direction = Dirs.EAST;
    }

    public int getWidth() {
        return width;
    }

    public String getName() {
        return name;
    }

    public Dirs getDirection() {
        return direction;
    }

    public static ArrayList<String> getBoatPos() {
        return boatPos;
    }

    public static void setBoatPos(ArrayList<String> boatPos) {
        Boats.boatPos = boatPos;
    }

    @Override
    public String toString() {
        return "Boats{" +
                "width=" + width +
                ", name='" + name + '\'' +
                ", direction=" + direction +
                ",oatPos=" + boatPos +
                '}';
    }
}
