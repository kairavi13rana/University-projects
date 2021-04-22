/**
 * 
 * @author Kairavi & Abhi
 *
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.PhotoAlbum;

/**
 * 
 * controller class for rename album UI.
 *
 */
public class RenameAlbumController {
	Stage stage;
	
	@FXML
	private Text txtOldName;
	
	@FXML
	private TextField txtNewName;
	
	public void start(Stage stage) throws IOException{
		this.stage = stage;
		
		txtOldName.setText(AlbumListController.selectedAlbum.getName());
	}
	
	/**
	 * Method to cancel rename album process and go back.
	 */
	@FXML
	void onCancel() {
		FXMLLoader fxLoader = new FXMLLoader(getClass().getResource("/view/AlbumList.fxml"));
		try {
			Parent root = (Parent) fxLoader.load();
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
	
	/**
	 * Method to save new album name.
	 */
	@FXML
	void onSave() {
		if(txtNewName.getText().trim()!=null) {
			/*ArrayList<PhotoAlbum> tempList = LoginController.loggedInUser.getUserAlbumList();
			PhotoAlbum temp = AlbumListController.selectedAlbum;
			temp.setName(txtNewName.getText().trim());
			tempList.add(AlbumListController.selectedAlbumIndex,temp);
			LoginController.loggedInUser.setUserAlbumList(tempList);*/
			AlbumListController.selectedAlbum.setName(txtNewName.getText().trim());
			LoginController.updateDB();
			
			FXMLLoader fxLoader = new FXMLLoader(getClass().getResource("/view/AlbumList.fxml"));
			try {
				Parent root = (Parent) fxLoader.load();
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
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Invalid Name");
			alert.setHeaderText("Invalid Album Name!");
			alert.setContentText("Album name must be provided.");
			alert.showAndWait();
			return;
		}
	}
}
