package ca.qc.cegeptr.mat1892498.battleship.boats;


import java.util.ArrayList;
import java.util.List;

public class BoatManager {

    private List<Boat> boats = new ArrayList<>();

    public void addBoat(Boat boat){
        boats.add(boat);
    }

    public List<Boat> getBoats() {
        return boats;
    }
}
