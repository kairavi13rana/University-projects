//Kairavi & abhik 
package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import resources.SongLibController;

public class SongLib extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/resources/songlib.fxml"));
		AnchorPane rootLayout = loader.<AnchorPane>load();
		
		SongLibController songLibController = loader.getController();
		songLibController.start(primaryStage);
		
        Scene scene = new Scene(rootLayout, 800, 600);
        primaryStage.setTitle("Kairavi & Abhi's Song Library");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
	}
	
}
