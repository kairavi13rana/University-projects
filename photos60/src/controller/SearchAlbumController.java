/**
 * 
 * @author Kairavi & Abhi
 *
 */
package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * 
 * Controller class for Search UI.
 *
 */
public class SearchAlbumController {
	Stage stage;
	public void start(Stage stage) throws IOException{
		this.stage = stage;
	}
	
	/**
	 * Method to cancel search task and go back.
	 */
	@FXML
	void onCancel() {
		FXMLLoader fxLoader = new FXMLLoader(getClass().getResource("/view/AlbumList.fxml"));
		try {
			Parent root = (Parent)fxLoader.load();
			Scene scene = new Scene(root);
			AlbumListController alController = fxLoader.getController();
			alController.start(stage);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
