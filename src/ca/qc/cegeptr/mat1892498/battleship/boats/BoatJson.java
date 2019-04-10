package ca.qc.cegeptr.mat1892498.battleship.boats;

public class BoatJson {

    String[] pos;
    String direction;

    public BoatJson(String[] pos){
        this.pos = pos;
        this.setDirection();
    }

    public void setDirection() {
        if(this.pos.length >= 2){
            String[] iPos1 = this.pos[0].split(",");
            String[] iPos2 = this.pos[1].split(",");
            // NORTH
            if(Integer.parseInt(iPos1[1]) - 1 == Integer.parseInt(iPos2[1]) && iPos1[0].equals(iPos2[0])){
                this.direction = "NORTH";
            }
            // EAST
            else if(Integer.parseInt(iPos1[0]) + 1 == Integer.parseInt(iPos2[0]) && iPos1[1].equals(iPos2[1])) {
                this.direction = "EAST";
            }
            // SOUTH
            else if(Integer.parseInt(iPos1[1]) + 1 == Integer.parseInt(iPos2[1]) && iPos1[0].equals(iPos2[0])) {
                this.direction = "SOUTH";
            }
            // WEST
            else if(Integer.parseInt(iPos1[0]) - 1 == Integer.parseInt(iPos2[0]) && iPos1[1].equals(iPos2[1])) {
                this.direction = "WEST";
            }
        }
    }
}