package ca.qc.cegeptr.mat1892498.battleship.boats;


import java.util.ArrayList;
import java.util.List;

public class BoatManager {

    public static List<Boat> boats = new ArrayList<>();
    public static boolean valid = false;

    public static void addBoat(Boat boat){
        boats.add(boat);
    }

    public static List<Boat> getBoats() {
        return boats;
    }

}
