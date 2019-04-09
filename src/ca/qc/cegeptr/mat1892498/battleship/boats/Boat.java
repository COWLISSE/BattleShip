package ca.qc.cegeptr.mat1892498.battleship.boats;

import javafx.scene.control.RadioButton;

public class Boat {
    private Boats type;
    private RadioButton btn;

    public Boat(Boats type, RadioButton btn){
        this.type = type;
        this.btn = btn;
    }

    public Boats getType() {
        return type;
    }

    public RadioButton getBtn() {
        return btn;
    }
}
