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
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Tag;

/**
 * 
 * Controller class for New Tag UI.
 *
 */
public class NewTagController {
	Stage stage;
	@FXML
	private ChoiceBox<Tag> cbExistingTags;
	
	@FXML
	private TextField txtTagValue;
	
	@FXML
	private TextField txtTagNameNew;
	
	@FXML
	private TextField txtTagValueNew;
	
	int tagIndex;
	
	/**
	 * Start method for stage.
	 * @param stage
	 * @throws IOException
	 */
	public void start(Stage stage) throws IOException{
		this.stage = stage;
		if(AlbumDisplayController.selectedPhoto.getPhotoTags()!=null) {
			cbExistingTags.setItems(FXCollections.observableArrayList(AlbumDisplayController.selectedPhoto.getPhotoTags()));
			cbExistingTags.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
					// TODO Auto-generated method stub
					tagIndex = arg2.intValue();
					txtTagValue.setText(AlbumDisplayController.selectedPhoto.getPhotoTags().get(tagIndex).getValue());
				}			
			});
		}
		
	}
	
	/**
	 * Method to save tags.
	 */
	@FXML
	void onSave() {
		if((!txtTagNameNew.getText().trim().isEmpty() && txtTagValueNew.getText().trim().isEmpty()) || txtTagNameNew.getText().trim().isEmpty() && !txtTagValueNew.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ALERT");
			alert.setHeaderText("Please add TAG name & value");
			alert.setContentText("Tag name and value must be provided.");
			alert.showAndWait();
			return;
		}
		Tag t = new Tag(txtTagNameNew.getText().trim(),txtTagValueNew.getText().trim());
		ArrayList<Tag> tList = AlbumDisplayController.selectedPhoto.getPhotoTags();
		if(tList==null) {
			tList = new ArrayList<Tag>();
		}else {
			tList.get(tagIndex).setValue(txtTagValue.getText()+"");
		}		
		tList.add(t);
		AlbumDisplayController.selectedPhoto.setPhotoTags(tList);
		LoginController.updateDB();
		
		
		
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
	 * Method to cancel add tag process and go back.
	 */
	@FXML
	void onCancel() {
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
}
