package code;

import java.io.File;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
    private VBox root;
    private File file;
    
    public Main(){
        root = new VBox();
    }
    
    
    @Override
    public void start(Stage primaryStage) {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("src/"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Java Programs", "java"));
        
        Button openFile = new Button();
        openFile.setText("Open File");
        openFile.setOnAction(e -> {
            file = fc.showOpenDialog(primaryStage);
            if(file != null)
                analize();
        });
        
        
        root.setAlignment(Pos.CENTER_LEFT);
        root.getChildren().add(openFile);
        
        Scene scene = new Scene(root, 300, 500);
        
        primaryStage.setTitle("Analizador Lexico");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    private void analize(){
        VBox tokensBox = new VBox();
        VBox clasificationBox = new VBox();
        HBox results = new HBox();
        
        results.getChildren().addAll(tokensBox, clasificationBox);
        root.getChildren().add(results);
        
        String file = this.file.getName();
        file = file.replace("\n", " ");
        String [] tokens = file.split(" ");
        
        Text tokenText = new Text();
        int state;
        for (int i = 0; i < tokens.length; i++) {
            tokenText.setText(tokens[i]);
            
            char [] tokenChars = tokens[i].toCharArray();
            char c;
            state = 0;
            for (int j = 0; j < tokenChars.length; j++) {
                c = tokenChars[j];
                switch(state){
                    case 0: 
                        if(isAlpha(c))
                            state = 1;
                        else if(isNumber(c))
                            state = 2;
                        else if(c == ' ')
                            state = 0;
                        else state = 6;
                        break;
                    case 1: break;
                    case 2: break;
                    case 3: break;
                }
            }
            
        }
    }
    
    private boolean isAlpha(char c){
        return (c >= 'A' && c <= 'Z' || (c >= 'a' && c <= 'z'))? true: false;
    }
    
    private boolean isNumber(char c){
        return (c >= '0' && c <= '9')? true: false;
    }
    
}
