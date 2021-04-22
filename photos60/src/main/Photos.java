/**
 * 
 * @author Kairavi & Abhi
 *
 */

package main;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Photos extends Application{

	public static void main(String[] args) {
		launch(args);

	}

	
	/**
	 * method will start the main stage of application.
	 * @param primaryStage - main stage
	 */
	@Override
	public void start(Stage pStage) throws Exception {
		FXMLLoader fxLoader = new FXMLLoader();
		fxLoader.setLocation(getClass().getResource("/view/Login.fxml"));
		AnchorPane rootPan = (AnchorPane)fxLoader.load();
		
		LoginController loginController = fxLoader.getController();
		loginController.start(pStage);
		Scene scene = new Scene(rootPan);
		pStage.setScene(scene);
		pStage.centerOnScreen();
		pStage.show();
		
	}
}
