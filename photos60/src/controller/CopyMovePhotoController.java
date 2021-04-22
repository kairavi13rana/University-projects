/**
 * 
 * @author Kairavi & Abhi
 *
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import model.Photo;
import model.PhotoAlbum;
/**
 * 
 * Controller class for copy photo.
 *
 */
public class CopyMovePhotoController {
	
	@FXML
	private ChoiceBox<PhotoAlbum> cbAlbumList;
	
	@FXML
	private Button btnCopy;
	
	@FXML
	private Button btnCancel;
	
	Stage stage;
	PhotoAlbum albumToCopy;
	
	/**
	 * Start method for stage.
	 * @param stage
	 * @throws IOException
	 */
	public void start(Stage stage) throws IOException{
		this.stage = stage;
		if(LoginController.loggedInUser.getUserAlbumList().size()>1) {
			cbAlbumList.setItems(FXCollections.observableArrayList(LoginController.loggedInUser.getUserAlbumList()));
			cbAlbumList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
					// TODO Auto-generated method stub
					albumToCopy = LoginController.loggedInUser.getUserAlbumList().get(arg2.intValue());
				}			
			});
		}
	}
	
	/**
	 * method to copy photo.
	 */
	@FXML
	private void onCopy() {
		
		ArrayList<Photo> tempList = albumToCopy.getAlbumPhotos();
		if(tempList!=null) {
			tempList.add(AlbumDisplayController.selectedPhoto);
		}
		else {
			tempList = new ArrayList<Photo>();
			tempList.add(AlbumDisplayController.selectedPhoto);
		}
		albumToCopy.setAlbumPhotos(tempList);
		albumToCopy.setTotalPhotos(tempList.size());
		//ArrayList<PhotoAlbum> paList = LoginController.loggedInUser.getUserAlbumList();
		//paList.set(AlbumListController.selectedAlbumIndex, AlbumListController.selectedAlbum);
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
	
	/**
	 * Method to cancel copy task.
	 */
	@FXML
	private void onCancel() {
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
}
