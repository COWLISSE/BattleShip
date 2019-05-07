package ca.qc.cegeptr.mat1892498.battleship.game.boards;

public class Opponent extends Board {

    private String username;

    public Opponent(String username) {
        super();
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
