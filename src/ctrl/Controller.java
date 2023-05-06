package ctrl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Stack;

import application.FileSelecter;
import application.Reversi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

/***********************************************
 * 画面のコントローラー
 * @author tarosa0001
 ***********************************************/
public class Controller implements Initializable {
    @FXML private ChoiceBox<String> choiceBoxColor;
    @FXML private CheckBox checkBoxAttack;
    @FXML private Button panel01;
    @FXML private Button panel02;
    @FXML private Button panel03;
    @FXML private Button panel04;
    @FXML private Button panel05;
    @FXML private Button panel06;
    @FXML private Button panel07;
    @FXML private Button panel08;
    @FXML private Button panel09;
    @FXML private Button panel10;
    @FXML private Button panel11;
    @FXML private Button panel12;
    @FXML private Button panel13;
    @FXML private Button panel14;
    @FXML private Button panel15;
    @FXML private Button panel16;
    @FXML private Button panel17;
    @FXML private Button panel18;
    @FXML private Button panel19;
    @FXML private Button panel20;
    @FXML private Button panel21;
    @FXML private Button panel22;
    @FXML private Button panel23;
    @FXML private Button panel24;
    @FXML private Button panel25;
    @FXML private Label labelRedCount;
    @FXML private Label labelGreenCount;
    @FXML private Label labelWhiteCount;
    @FXML private Label labelBlueCount;
    @FXML private MenuBar menuBar;
    @FXML private Slider slider;

    private LinkedList<Button> pList;
    private Reversi game;
    private File nowFile;
    private boolean saveflag;
    private ArrayList<DrawPanelThread> thList;

    /** ************************************************************
     * 初期化処理
     * 画面表示時に行いたい処理を実装する。
     * @param location
     * @param resources
     * *************************************************************/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game = new Reversi();
        game.initTable();
        checkBoxAttack.setSelected(false);
        nowFile = null;
        saveflag = false;
        choiceBoxColor.setValue("Red");
        slider.setValue(100);

        pList = new LinkedList<>();
        pList.add(panel01); pList.add(panel02); pList.add(panel03); pList.add(panel04); pList.add(panel05);
        pList.add(panel06); pList.add(panel07); pList.add(panel08); pList.add(panel09); pList.add(panel10);
        pList.add(panel11); pList.add(panel12); pList.add(panel13); pList.add(panel14); pList.add(panel15);
        pList.add(panel16); pList.add(panel17); pList.add(panel18); pList.add(panel19); pList.add(panel20);
        pList.add(panel21); pList.add(panel22); pList.add(panel23); pList.add(panel24); pList.add(panel25);

        new CountPanelThread(game, "Red", labelRedCount).start();
        new CountPanelThread(game, "Green", labelGreenCount).start();
        new CountPanelThread(game, "White", labelWhiteCount).start();
        new CountPanelThread(game, "Blue", labelBlueCount).start();

        thList = new ArrayList<DrawPanelThread>();
    }

    /** ************************************************************
     * ボタン押下時のアクション
     * @param event イベント
     * *************************************************************/
    @FXML
    public void onClickPanel(ActionEvent event) {
        drawThreadsReset();
        int number = 0;
        String color = null;
        try {
            number = Integer.parseInt(((Button) event.getSource()).getText());
            color = choiceBoxColor.getValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int i = (number-1)%5;
        int j = (number-1)/5;
        String pcolor = game.getTable()[j][i];
        if (checkBoxAttack.isSelected()) {
            color = "Yellow";
        }
        if (color.equals("Yellow") && (pcolor.equals("None") || pcolor.equals("Yellow"))){
            return;
        }
        if (!color.equals("Yellow") && !pcolor.equals("None") && !pcolor.equals("Yellow")) {
            return;
        }
        
        game.searchAllDirection(i, j, color); 
        int count=0;
        int sleep;
        for (int y=0; y<5; y++) {
            for (int x=0; x<5; x++) {
                if (game.diffTable()[y][x]) {
                    if (x == i && y == j) {
                        sleep = 0;
                    } else {
                        count++;
                        sleep = 500*count;
                    }
                    DrawPanelThread th = new DrawPanelThread(pList.get(5*y+x), color, sleep, slider.getValue());
                    th.start();
                    thList.add(th);
                }
            }
        }
        saveflag = true;
        setTitle();
        checkBoxAttack.setSelected(false);
        //game.showTable();
    }

    private void drawThreadsReset() {
        for (DrawPanelThread dt : thList) {
            dt.interrupt();
            drawAllPanel(game.getTable());
        }
        thList.clear();
    }

    @FXML
    public void onClickNew(ActionEvent event) {
        drawThreadsReset();
		try {
			ShowNewWindow.start();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

    @FXML
    public void onClickSave(ActionEvent event) {
        drawThreadsReset();
        if (nowFile == null) {
            onClickSaveAs(event);
        } else if (saveflag) {
            try {
                saveflag = false;
                setTitle();
                game.saveCsv(nowFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onClickSaveAs(ActionEvent event) {
        drawThreadsReset();
        File openCsv = null;
        try {
            openCsv = FileSelecter.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (openCsv != null) {
            try {
                nowFile = openCsv;
                saveflag = false;
                setTitle();
                game.saveCsv(openCsv);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        drawAllPanel(game.getTable());
    }

    @FXML
    public void onClickOpen(ActionEvent event) {
        drawThreadsReset();
        File openCsv = null;
        try {
            openCsv = FileSelecter.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (openCsv != null) {
            try {
                nowFile = openCsv;
                saveflag = false;
                setTitle();
                game.loadCsv(openCsv);
                game.setStack(new Stack<String[][]>());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        drawAllPanel(game.getTable());
    }

    @FXML
    public void onClickRestart(ActionEvent event) {
        drawThreadsReset();
        menuBar.getScene().getWindow().hide();
        try {
			ShowNewWindow.start();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
    }

    @FXML
    public void onClickClose(ActionEvent event) {
        drawThreadsReset();
        menuBar.getScene().getWindow().hide();
    }

    @FXML
    public void onClickReset(ActionEvent event) {
        drawThreadsReset();
        game.initTable();
        drawAllPanel(game.getTable());
    }

    @FXML
    public void onClickBack(ActionEvent event) {
        drawThreadsReset();
        game.backTable();
        drawAllPanel(game.getTable());
    }

    public void drawAllPanel(String[][] table) {
        int i;
        int j;
        for (j=0; j<5; j++) {
            for (i=0; i<5; i++) {
                drawOnePanel(i, j, table[j][i]);
            }
        }
    }

    public void drawOnePanel(int i, int j, String color) {
        Button panel;
        panel = pList.get(5*j+i);
        panel.setStyle("-fx-border-color: #000000; -fx-background-color: "+ PanelColor.toRGB(color) +";");
    }

    public void setTitle() {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        String status = saveflag ? "● " : "";
        if (nowFile == null) {
            stage.setTitle(status + "Untitled.csv" + " - Attack25");
        } else {
            stage.setTitle(status + nowFile.getName() + " - Attack25");
        }
    }
}