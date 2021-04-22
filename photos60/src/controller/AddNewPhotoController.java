/**
 * 
 * @author Kairavi & Abhi
 *
 */
package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Photo;
import model.PhotoAlbum;
import model.Tag;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Controller for adding new photo
 *
 */
/**
 * @author devka
 *
 */
/**
 * @author devka
 *
 */
public class AddNewPhotoController {

	@FXML
	private Button btnBrowse;
	@FXML
	private Button btnAdd;
	@FXML
	private Button btnCancel;
	@FXML
	private Button btnLogout;
	
	@FXML
	private TextField txtBrowse;
	
	@FXML
	private TextField txtCaption;
	
	@FXML
	private TextField txtTagNameNew;
	
	@FXML
	private TextField txtTagValueNew;
	
	
	
	Stage stage;
	File photoToAdd;
	String photoFilePath;
	
	/**
	 * Start method for stage
	 * @param stage
	 */
	public void start(Stage stage) {
		this.stage = stage;

	}

	/**
	 * Method to browse and select image file.
	 */
	@FXML
	void onBrowse() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select a Photo");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpeg", "*.jpg", "*.gif","*.bmp", "*.PNG", "*.JPEG", "JPG", "*.GIF", "*.BMP"));
		photoToAdd = fileChooser.showOpenDialog(stage);
		if (photoToAdd != null) {
			photoFilePath = photoToAdd.getAbsolutePath();
			txtBrowse.setText(photoFilePath);
		} else {
			photoFilePath = null;
		}

	}
	
	/**
	 * On add button click this method will trigger which will add new photo to album.
	 */
	@FXML
	void onAdd() {
		
		if(txtTagNameNew.getText().trim().isEmpty() || txtTagValueNew.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ALERT");
			alert.setHeaderText("Please add TAG name & value");
			alert.setContentText("Tag name and value must be provided.");
			alert.showAndWait();
			return;
		}
		
		Date date = new Date();
		long timeStamp = date.getTime();
		File dest = new File(LoginController.ROOT+"\\"+LoginController.loggedInUser.getName()+"\\"+photoToAdd.getName());
		try {
			copyFileUsingStream(photoToAdd,dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Photo p = new Photo(photoToAdd.getName(), txtCaption.getText(),timeStamp,LoginController.ROOT+"\\"+LoginController.loggedInUser.getName()+"\\"+photoToAdd.getName());
		Tag t = new Tag(txtTagNameNew.getText().trim(),txtTagValueNew.getText().trim());
		ArrayList<Tag> tList = new ArrayList<Tag>();
		tList.add(t);
		p.setPhotoTags(tList);
		ArrayList<Photo> tempList = AlbumListController.selectedAlbum.getAlbumPhotos();
		if(tempList!=null) {
			tempList.add(p);
		}
		else {
			tempList = new ArrayList<Photo>();
			tempList.add(p);
		}
		AlbumListController.selectedAlbum.setAlbumPhotos(tempList);
		AlbumListController.selectedAlbum.setTotalPhotos(tempList.size());
		ArrayList<PhotoAlbum> paList = LoginController.loggedInUser.getUserAlbumList();
		paList.set(AlbumListController.selectedAlbumIndex, AlbumListController.selectedAlbum);
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
	 * method to cancel current add photo task on click of cancel button.
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
	 * logout and go back to login screen.
	 */
	@FXML
	void doLogout() {
		FXMLLoader fxLoader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
		try {
			Parent root = (Parent)fxLoader.load();
			Scene scene = new Scene(root);
			LoginController loginController = fxLoader.getController();
			loginController.start(stage);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to copy file from one directory to another.
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	private static void copyFileUsingStream(File source, File dest) throws IOException {
	    InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	}
}
