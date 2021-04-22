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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Photo;
import model.PhotoAlbum;

/**
 * 
 * Controller class to manage RecaptionPhoto UI.
 *
 */
public class RecaptionPhotoController {
	Stage stage;
	@FXML
	private Text txtOldName;
	
	@FXML
	private TextField txtNewName;
	
	public void start(Stage stage) throws IOException{
		this.stage = stage;
	}
	
	/**
	 * Method to cancel recaption process and go back.
	 */
	@FXML
	void onCancel() {
		FXMLLoader fxLoader = new FXMLLoader(getClass().getResource("/view/AlbumDisplay.fxml"));
		try {
			Parent root = (Parent)fxLoader.load();
			Scene scene = new Scene(root);
			AlbumDisplayController adController = fxLoader.getController();
			adController.start(stage);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * MEthod to save new caption for photo.
	 */
	@FXML
	void onSave() {
		if(txtNewName.getText().trim()!=null) {
			/*ArrayList<Photo> tempList = AlbumListController.selectedAlbum.getAlbumPhotos();
			Photo temp = AlbumDisplayController.selectedPhoto;
			temp.setCaption(txtNewName.getText().trim());
			tempList.add(AlbumDisplayController.selectedPhotoId,temp);
			AlbumListController.selectedAlbum.setAlbumPhotos(tempList);
			ArrayList<PhotoAlbum> paList = LoginController.loggedInUser.getUserAlbumList();
			paList.set(AlbumListController.selectedAlbumIndex, AlbumListController.selectedAlbum);
			LoginController.loggedInUser.setUserAlbumList(paList);*/
			AlbumDisplayController.selectedPhoto.setCaption(txtNewName.getText().trim());
			LoginController.updateDB();
			
			FXMLLoader fxLoader = new FXMLLoader(getClass().getResource("/view/AlbumDisplay.fxml"));
			try {
				Parent root = (Parent)fxLoader.load();
				Scene scene = new Scene(root);
				AlbumDisplayController adController = fxLoader.getController();
				adController.start(stage);
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
			alert.setTitle("Invalid Caption");
			alert.setHeaderText("Invalid Caption Name!");
			alert.setContentText("Caption must be provided.");
			alert.showAndWait();
			return;
		}
	}
}
