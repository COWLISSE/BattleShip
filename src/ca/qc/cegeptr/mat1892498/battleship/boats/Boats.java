package ca.qc.cegeptr.mat1892498.battleship.boats;

public enum Boats {

    CARRIER(5, "Carrier"),
    BATTLESHIP(4, "Battleship"),
    CRUISER(3, "Cruiser"),
    SUBMARINE(3, "Submarine"),
    DESTROYER(2, "Destroyer");

    private final int length;
    private final String name;
    private Dirs direction;



    Boats(int length, String name){
        this.length = length;
        this.name = name;
        this.direction = Dirs.EAST;
    }

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public Dirs getDirection() {
        return direction;
    }

    public void setDirection(Dirs direction) {
        this.direction = direction;
    }






    @Override
    public String toString() {
        return "Boats{" +
                "length=" + length +
                ", name='" + name + '\'' +
                ", direction=" + direction +
                '}';
    }
}
