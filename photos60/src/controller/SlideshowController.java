/**
 * 
 * @author Kairavi & Abhi
 *
 */
package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Photo;
import model.Tag;
/**
 * 
 * Controller for slideshow UI.
 *
 */
public class SlideshowController {
	Stage stage;
	int index = 0;
	
	@FXML
	private Button btnBack;
	@FXML
	private Button btnNext;
	
	@FXML
	private Button btnPrev;
	
	@FXML
	private Text labCaption;
	
	@FXML
	private Text labDate;
	
	@FXML
	private Text labTagName;
	
	@FXML
	private Text labTagValue;
	
	@FXML 
	private ImageView imageView;
	
	/**
	 * Start method for stage.
	 * @param stage
	 */
	public void start(Stage stage) {
		this.stage = stage;
		Photo p;
		if(AlbumDisplayController.selectedPhoto != null) {
			p = AlbumDisplayController.selectedPhoto;
			index = AlbumDisplayController.selectedPhotoId;
		}
		else {
			p = AlbumListController.selectedAlbum.getAlbumPhotos().get(0);
			index = 0;
		}
		FileInputStream input = null;
		try {
			input = new FileInputStream(p.getFilePath());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image image = new Image(input);
        imageView.setImage(image);
        labCaption.setText("Caption: "+p.getCaption());
        Date date= new Date(p.getDatestamp());
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        labDate.setText("Date: "+df.format(date));
        ArrayList<Tag> tagList = p.getPhotoTags();
        if(tagList!=null) {
        	labTagName.setText("Tag Name: "+p.getPhotoTags().get(0).getName());
            labTagValue.setText("Tag Value: "+p.getPhotoTags().get(0).getValue());
        }        
     }
	
	/**
	 * Method to go to next photo.
	 */
	@FXML
	void onNext() {
		if(index < AlbumListController.selectedAlbum.getAlbumPhotos().size()-1) {
			index++;
		}
		updateImageView();
	}
	
	/**
	 * Method to go to previous photo.
	 */
	@FXML
	void onPrev() {
		if(index >0) {
			index--;
		}
		updateImageView();
	}
	
	/**
	 * Method to update image view with selected photo.
	 */
	void updateImageView() {
		
		Photo p = AlbumListController.selectedAlbum.getAlbumPhotos().get(index);
		AlbumDisplayController.selectedPhoto = p;
		AlbumDisplayController.selectedPhotoId = index;
		FileInputStream input = null;
		try {
			input = new FileInputStream(p.getFilePath());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image image = new Image(input);
        imageView.setImage(image);
        labCaption.setText("Caption: "+p.getCaption());
        Date date= new Date(p.getDatestamp());
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        labDate.setText("Date: "+df.format(date));
        ArrayList<Tag> tagList = p.getPhotoTags();
        if(tagList!=null) {
        	labTagName.setText("Tag Name: "+p.getPhotoTags().get(0).getName());
            labTagValue.setText("Tag Value: "+p.getPhotoTags().get(0).getValue());
        }
        else {
        	labTagName.setText("Tag Name: ");
            labTagValue.setText("Tag Value: ");
        }
	}
	
	/**
	 * Method to go back to previous screen.
	 */
	@FXML
	void goBack() {
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
	 * Method to log out and go back to login screen.
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
	 * Method to edit tag.
	 */
	@FXML
	void onEditTag() {
		FXMLLoader fxLoader = new FXMLLoader(getClass().getResource("/view/NewTag.fxml"));
		try {
			Parent root = (Parent)fxLoader.load();
			Scene scene = new Scene(root);
			NewTagController ntController = fxLoader.getController();
			ntController.start(stage);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
