/**
 * 
 * @author Kairavi & Abhi
 *
 */
package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Photo;
import model.PhotoAlbum;
import model.Tag;

/**
 * 
 * Controller class to display selected album.
 *
 */
public class AlbumDisplayController {

	Stage stage;
	static Photo selectedPhoto;
	static int selectedPhotoId = 0;
	Node clickedNode;
	@FXML
	private Label labAlbumName;
	
	@FXML
	private GridPane photoGallary;
	
	/**
	 * Start method for stage.
	 * @param stage
	 * @throws IOException
	 */
	public void start(Stage stage) throws IOException{
		this.stage = stage;
		labAlbumName.setText("Album: "+AlbumListController.selectedAlbum.getName());
		updateAlbumUI();
	}
	
	/**
	 * Mehtod to update album UI.
	 */
	private void updateAlbumUI() {
		int i=0,j=0;
		photoGallary.getChildren().removeAll(photoGallary.getChildren());
		photoGallary.getRowConstraints().removeAll(photoGallary.getRowConstraints());
		if(AlbumListController.selectedAlbum.getAlbumPhotos()!=null) {
			for(Photo p : AlbumListController.selectedAlbum.getAlbumPhotos()) {
				FileInputStream input = null;
				try {
					input = new FileInputStream(p.getFilePath());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        Image image = new Image(input,100,100,false,false);
		        ImageView imageView = new ImageView(image);
		        Label label = new Label("Caption: "+p.getCaption(), imageView);
		        StackPane sp = new StackPane(label);
		        photoGallary.add(sp, j, i);
		        j++;
				if(j>=2) {
					j=0;
					i++;
					RowConstraints con = new RowConstraints();
			        con.setPrefHeight(100);
			        con.setMinHeight(100);
			        con.setVgrow(Priority.ALWAYS);
			        photoGallary.getRowConstraints().add(con);
				}
			}
		}
	}
	
	/**
	 * Method to detect click event on GridPane cell.
	 * @param e
	 */
	@FXML
	void onClicked(MouseEvent e) {
		
		ObservableList<Node> childrens = photoGallary.getChildren();
		
		for(Node n : childrens) {
			n.setStyle("");
		}
		
		clickedNode = e.getPickResult().getIntersectedNode();
		if (clickedNode != photoGallary) {
		    // click on descendant node
		    Node parent = clickedNode.getParent();
		    while (parent != photoGallary) {
		        clickedNode = parent;
		        parent = clickedNode.getParent();
		    }
		    Integer colIndex = GridPane.getColumnIndex(clickedNode);
		    Integer rowIndex = GridPane.getRowIndex(clickedNode);
		    selectedPhotoId = (rowIndex * 2) + colIndex;
		    //System.out.println(selectedPhotoId);
		    clickedNode.setStyle("-fx-background-color: LIGHTGREEN");
		    selectedPhoto = AlbumListController.selectedAlbum.getAlbumPhotos().get(selectedPhotoId);
		    ArrayList<Tag> tList = selectedPhoto.getPhotoTags();
		    if(tList!=null) {
		    	//System.out.println(tList.get(0).getName());
		    }
		}
       
	}
	
	/**
	 * Method to load slideshow for album.
	 */
	@FXML
	void onDisplayPhoto() {
		FXMLLoader fxLoader = new FXMLLoader(getClass().getResource("/view/Slideshow.fxml"));
		try {
			Parent root = (Parent)fxLoader.load();
			Scene scene = new Scene(root);
			SlideshowController sController = fxLoader.getController();
			sController.start(stage);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * method to open add photo UI.
	 */
	@FXML
	void onAddPhoto() {
		FXMLLoader fxLoader = new FXMLLoader(getClass().getResource("/view/AddNewPhoto.fxml"));
		try {
			Parent root = (Parent)fxLoader.load();
			Scene scene = new Scene(root);
			AddNewPhotoController anpController = fxLoader.getController();
			anpController.start(stage);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to open recaption UI.
	 */
	@FXML
	void onRecaption() {
		FXMLLoader fxLoader = new FXMLLoader(getClass().getResource("/view/RecaptionPhoto.fxml"));
		try {
			Parent root = (Parent)fxLoader.load();
			Scene scene = new Scene(root);
			RecaptionPhotoController rpController = fxLoader.getController();
			rpController.start(stage);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to remove photo from album.
	 */
	@FXML
	void onRemovePhoto() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete");
		alert.setHeaderText("Delete Photo");
		alert.setContentText("Are you sure? This will delete the selected photo.");
		Optional<ButtonType> confirm = alert.showAndWait();
		if (confirm.get() == ButtonType.OK) {
			ArrayList<Photo> tempList = AlbumListController.selectedAlbum.getAlbumPhotos();
			tempList.remove(selectedPhotoId);
			AlbumListController.selectedAlbum.setAlbumPhotos(tempList);
			AlbumListController.selectedAlbum.setTotalPhotos(tempList.size());
			ArrayList<PhotoAlbum> paList = LoginController.loggedInUser.getUserAlbumList();
			paList.set(AlbumListController.selectedAlbumIndex, AlbumListController.selectedAlbum);
			LoginController.updateDB();
			updateAlbumUI();
		}
	}
	
	/**
	 * Method to go back on Album list screen.
	 */
	@FXML
	void goBack() {
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
	
	/**
	 * Method to logout and got to login screen.
	 */
	@FXML
	public void doLogout() {
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
	 * Method to open copy UI.
	 */
	@FXML
	void onCopy() {
		if(selectedPhoto==null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ALERT");
			alert.setHeaderText("Select a Photo");
			alert.setContentText("Please select photo to copy.");
			alert.showAndWait();
			return;
		}
		FXMLLoader fxLoader = new FXMLLoader(getClass().getResource("/view/CopyMovePhoto.fxml"));
		try {
			Parent root = (Parent)fxLoader.load();
			Scene scene = new Scene(root);
			CopyMovePhotoController cpController = fxLoader.getController();
			cpController.start(stage);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
