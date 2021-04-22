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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.PhotoAlbum;

/**
 * 
 * Controller for create album.
 *
 */
public class CreateAlbumController {

	@FXML
	private TextField txtAlbumName;

	@FXML
	private Button btnCreate;

	@FXML
	private Button btnCancel;

	Stage stage;

	public void start(Stage stage) throws IOException {
		this.stage = stage;
	}

	/**
	 * Method to create album.
	 */
	@FXML
	void onCreate() {
		if (txtAlbumName.getText().trim().toString().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Invalid Name");
			alert.setHeaderText("Invalid Album Name!");
			alert.setContentText("Album name must be provided.");
			alert.showAndWait();
			return;
		}

		String albumName = txtAlbumName.getText().trim().toString();

		if (isAlbumExist(albumName)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Album already exist!");
			alert.setHeaderText("Album exist!");
			alert.setContentText("An album already exist with same name. Please use different one.");
			alert.showAndWait();
			return;
		}
		
		ArrayList<PhotoAlbum> tempList = LoginController.loggedInUser.getUserAlbumList();
		PhotoAlbum temp = new PhotoAlbum(albumName, 0, null);
		tempList.add(temp);
		LoginController.loggedInUser.setUserAlbumList(tempList);
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

	/**
	 * method to cancel create album process and go back.
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
	 * Method to check if album is already exist or not.
	 * @param albumName
	 * @return true if exist.
	 */
	static boolean isAlbumExist(String albumName) {
		for (PhotoAlbum pa : LoginController.loggedInUser.getUserAlbumList()) {
			if (pa.getName().trim().toLowerCase().equals(albumName.trim().toLowerCase())) {
				return true;
			}
		}
		return false;
	}
}
