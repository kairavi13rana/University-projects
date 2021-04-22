/**
 * 
 * @author Kairavi & Abhi
 *
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User;
/**
 * 
 * Controller for managing Admin Dashboard actions.
 *
 */
public class AdminDashboardController {

	private Stage stage;
	
	@FXML
	private Button btnCreateUser;
	
	@FXML
	private Button btnDeleteUser;
	
	@FXML
	private Button btnListUser;
	
	@FXML
	private Button btnLogout;
	
	@FXML
	private TextField txtUsername;
	
	@FXML
	private ListView<User> userListView;
	
	/**
	 * Start method for stage.
	 * @param stage
	 * @throws IOException
	 */
	public void start(Stage stage) throws IOException{
		this.stage = stage;	
		updateListView();
	}
	
	/**
	 * method to create user on click of create button.
	 */
	@FXML
	public void createUser() {
		String userName = txtUsername.getText().trim();
		if(!userName.isEmpty() || !userName.equalsIgnoreCase("")) {
			if(isUserExist(userName)) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Username already exist!");
				alert.setHeaderText("Please try with different user.");
				alert.setContentText("A User already exists with same username");
				txtUsername.setText("");
				alert.showAndWait();
			}
			else {
				User user = new User(userName);
				LoginController.photosUsers.add(user);
				txtUsername.setText("");
				updateListView();
				LoginController.updateDB();
			}
		}
	}
	
	/**
	 * method to delete user on click of delete button.
	 */
	@FXML
	public void deleteUser() {
		String userName = userListView.getSelectionModel().getSelectedItem().getName();
		if(!userName.isEmpty() || !userName.equalsIgnoreCase("")) {
			if(isUserExist(userName)) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Are you sure?");
				alert.setHeaderText("Are you sure?");
				alert.setContentText("Are you sure you want to delete selected user?");
				Optional<ButtonType> confirm = alert.showAndWait();
				if (confirm.get() == ButtonType.OK) {
					LoginController.photosUsers.remove(userListView.getSelectionModel().getSelectedItem());
					updateListView();
					LoginController.updateDB();
				}
			}
		}
	}
	
	/**
	 * Method to logout and go back to login screen.
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
	 * Check if user already exist.
	 * @param userName - userName to check.
	 */
	public boolean isUserExist(String userName) {
		for(User tUser: LoginController.photosUsers) {
			if(tUser.getName().equals(userName)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method to update User list view.
	 */
	private void updateListView() {
		userListView.setItems(FXCollections.observableArrayList(LoginController.photosUsers));
		userListView.setCellFactory(param -> new ListCell<User>() {
            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }   
            
        });
		userListView.getSelectionModel().select(0);
	}
}
