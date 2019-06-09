package cn.edu.nju.mutestdemo.web2;



import cn.edu.nju.mutestdemo.ASTMutation.Generate;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener.Change;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebHistory.Entry;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public class WebViewSample extends Application {

    private Scene scene;
    Generate gen=new Generate();
    public static WebView browser;
    @Override
    public void start(Stage primarystage) {
    	primarystage.setTitle("WebView test");             

    	browser = new WebView();
        WebEngine engine = browser.getEngine();


        /*engine.getLoadWorker().stateProperty().addListener((ObservableValue<? extends State> ov,State oldState, State newState)->{
            System.out.println("loadWorker");
            if(newState==State.SUCCEEDED){
                JSObject window=(JSObject)engine.executeScript("window");
                window.setMember("gen",gen);

                System.out.println("gen");
            }
        });*/
        String url = "D:\\IdeaProjects\\mutestdemo\\src\\main\\java\\cn\\edu\\nju\\mutestdemo\\web2\\main.html";//WebViewSample.class.getResource("main.html").toExternalForm();
        engine.load(url);

        StackPane sp = new StackPane();
        sp.getChildren().add(browser);

        Scene root = new Scene(sp);

        primarystage.setScene(root);
        primarystage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

