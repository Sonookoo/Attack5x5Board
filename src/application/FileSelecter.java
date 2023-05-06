package application;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileSelecter {

    private FileSelecter() {}
    
    public static File open() throws Exception {
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        
        fileChooser.setTitle("ファイル選択");
        // 拡張子フィルタを設定
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("csvファイル", "*.csv"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("すべてのファイル", "*.*"));
        // 初期ディレクトリをホームにする。
        fileChooser.setInitialDirectory(new File("./tabledata"));

        // ファイル選択
        return fileChooser.showOpenDialog(stage);
    }

    public static File save() throws Exception {
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        
        fileChooser.setTitle("ファイル保存");
        // 拡張子フィルタを設定
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("csvファイル", "*.csv"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("すべてのファイル", "*.*"));
        // 初期ディレクトリをホームにする。
        fileChooser.setInitialDirectory(new File("./tabledata"));

        // ファイル選択
        return fileChooser.showSaveDialog(stage);
    }
}