package ca.qc.cegeptr.mat1892498.battleship.boats;

import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.Arrays;

public class Boat {
    private Boats type;
    private RadioButton btn;
    private static ArrayList<String[]> boatPos = new ArrayList<>(Arrays.asList(
            new String[5],
            new String[4],
            new String[3],
            new String[3],
            new String[2]
    ));
    private Paint color = Color.color(Math.random(), Math.random(), Math.random());


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

    public static ArrayList<String[]> getBoatPos() {
        return boatPos;
    }

    public Paint getColor() {
        return this.color;
    }
}
