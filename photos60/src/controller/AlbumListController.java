/**
 * 
 * @author Kairavi & Abhi
 *
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Stage;
import model.PhotoAlbum;

/**
 * 
 * Controller class to manage album list UI.
 *
 */
public class AlbumListController {

	@FXML
	private TableView<Map> albumTable;

	@FXML
	private Button btnCreate;
	
	@FXML
	private Button btnOpen;
	
	@FXML
	private Button btnRename;
	
	@FXML
	private Button btnDelete;
	
	@FXML
	private Button btnSearch;
	
	@FXML
	private Button btnLogout;
	
	static PhotoAlbum selectedAlbum;
	static int selectedAlbumIndex;
	Stage stage;
	
	/**
	 * Start method for stage.
	 * @param stage
	 */
	public void start(Stage stage) {
		this.stage = stage;
		albumTable.setPlaceholder(new Label("No album to display"));
		updateTable();
	}

	/**
	 * Method to get albums name.
	 * @return List of album name
	 */
	private ArrayList<String> getAlbumsName() {
		ArrayList<String> tempList = new ArrayList<String>();
		for (PhotoAlbum pa : LoginController.loggedInUser.getUserAlbumList()) {
			tempList.add(pa.getName());
		}
		return tempList;
	}

	/**
	 * Method to get albums photo count array
	 * @return returns integer array of photo count.
	 */
	private ArrayList<Integer> getAlbumsPhotoCount() {
		ArrayList<Integer> tempList = new ArrayList<Integer>();
		for (PhotoAlbum pa : LoginController.loggedInUser.getUserAlbumList()) {
			System.out.println(pa.getTotalPhotos());
			tempList.add(pa.getTotalPhotos());
		}
		return tempList;
	}

	/**
	 * Method to get album date range array.
	 * @return return string array.
	 */
	private ArrayList<String> getAlbumsDateRange() {
		ArrayList<String> tempList = new ArrayList<String>();
		for (PhotoAlbum pa : LoginController.loggedInUser.getUserAlbumList()) {
			tempList.add(pa.getDateRange());
		}
		return tempList;
	}

	/**
	 * Method to update table UI.
	 */
	private void updateTable() {
		albumTable.getItems().removeAll(albumTable.getItems());
		TableColumn<Map, String> name = new TableColumn<>("Name");
		name.setCellValueFactory(new MapValueFactory<>("name"));

		TableColumn<Map, Integer> numPhotos = new TableColumn<>("Number of Photos");
		numPhotos.setCellValueFactory(new MapValueFactory<>("totalPhotos"));

		TableColumn<Map, String> dateRange = new TableColumn<>("Date Range");
		dateRange.setCellValueFactory(new MapValueFactory<>("dateRange"));

		albumTable.getColumns().addAll(name, numPhotos, dateRange);

		ObservableList<Map<String, Object>> items = FXCollections.<Map<String, Object>>observableArrayList();

		ArrayList<String> nameList = getAlbumsName();
		ArrayList<Integer> nPhotosList = getAlbumsPhotoCount();
		ArrayList<String> dRangeList = getAlbumsDateRange();

		for (int i = 0; i < nameList.size(); i++) {
			Map<String, Object> temp = new HashMap<>();
			temp.put("name", nameList.get(i));
			temp.put("totalPhotos", nPhotosList.get(i));
			temp.put("dateRange", dRangeList.get(i));
			items.add(temp);
		}

		albumTable.getItems().addAll(items);
	}
	
	/**
	 * method to open album.
	 */
	@FXML
	void onOpen() {
		selectedAlbumIndex = albumTable.getSelectionModel().getSelectedIndex();
		selectedAlbum = LoginController.loggedInUser.getUserAlbumList().get(selectedAlbumIndex);
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
	 * method to search album.
	 */
	@FXML
	void onSearch() {
		FXMLLoader fxLoader = new FXMLLoader(getClass().getResource("/view/SearchAlbum.fxml"));
		try {
			Parent root = (Parent)fxLoader.load();
			Scene scene = new Scene(root);
			SearchAlbumController saController = fxLoader.getController();
			saController.start(stage);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * method to create album.
	 */
	@FXML
	void onCreate() {
		FXMLLoader fxLoader = new FXMLLoader(getClass().getResource("/view/CreateAlbum.fxml"));
		try {
			Parent root = (Parent)fxLoader.load();
			Scene scene = new Scene(root);
			CreateAlbumController caController = fxLoader.getController();
			caController.start(stage);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * method to rename album.
	 */
	@FXML
	void onRename() {
		selectedAlbumIndex = albumTable.getSelectionModel().getSelectedIndex();
		selectedAlbum = LoginController.loggedInUser.getUserAlbumList().get(selectedAlbumIndex);
		FXMLLoader fxLoader = new FXMLLoader(getClass().getResource("/view/RenameAlbum.fxml"));
		try {
			Parent root = (Parent)fxLoader.load();
			Scene scene = new Scene(root);
			RenameAlbumController raController = fxLoader.getController();
			raController.start(stage);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * method to delete album.
	 */
	@FXML
	void onDelete() {
		ArrayList<PhotoAlbum> tempList = LoginController.loggedInUser.getUserAlbumList();
		tempList.remove(selectedAlbumIndex);
		LoginController.loggedInUser.setUserAlbumList(tempList);
		LoginController.updateDB();
		updateTable();
	}
	
	/**
	 * method to logout and go back to login screen.
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
}
