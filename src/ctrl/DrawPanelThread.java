package ctrl;

import javafx.application.Platform;
import javafx.scene.control.Button;

class DrawPanelThread extends Thread {
    private String color;
    private Button panel;
    private int sleep;
    private double volume;

    public DrawPanelThread(Button panel, String color, int sleep, double volume) {
        this.color = color;
        this.panel = panel;
        this.sleep = sleep;
        this.volume = volume;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(this.sleep);
            Platform.runLater(() -> {
                try {
                    PanelSound.play(color, volume);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.panel.setStyle("-fx-border-color: #000000; -fx-background-color: "+ PanelColor.toRGB(this.color) +";");
            });
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
    }
}
