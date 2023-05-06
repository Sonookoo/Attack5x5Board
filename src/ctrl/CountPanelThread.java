package ctrl;

import application.Reversi;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class CountPanelThread extends Thread {
    private Reversi reversi;
    private String color;
    private Label label;
    private int count;

    public CountPanelThread(Reversi reversi, String color, Label label) {
        this.reversi = reversi;
        this.color = color;
        this.label = label;
        this.count = 0;
    }

    @Override
    public void run() {
        int i;
        int j;
        
        for (j=0; j<5; j++) {
            for (i=0; i<5; i++) {
                if (this.reversi.getTable()[j][i].equals(this.color)) {
                    this.count++;
                }
            }
        }

        Platform.runLater(() -> {
            this.label.setText(Integer.valueOf(this.count).toString());
            new CountPanelThread(this.reversi, this.color, this.label).start();
        });
    }
}
