/**
 * 
 * @author Kairavi & Abhi
 *
 */

package controller;

import java.util.Date;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import model.Photo;
import model.PhotoAlbum;
import model.User;

/**
 * 
 * Controller class to handle login action. Also create user folder if not exist
 * and manage user login data.
 *
 */
public class LoginController {

	@FXML
	private Button btnGo;

	@FXML
	private TextField txtUsername;

	private final static String dbPath = "database/photos.dat";
	public final static String ROOT = "database";
	private final static String ADMIN = "admin";
	public static ArrayList<User> photosUsers = new ArrayList<User>();
	public static User loggedInUser;
	private Stage stage;
	
	/**
	 * start method for stage.
	 * @param stage
	 * @throws IOException
	 */
	public void start(Stage stage) throws IOException {          

		this.stage = stage;
	}
	
	/**
	 * Method to store data in .dat file.
	 */
	static void updateDB() {
		try {
			FileOutputStream fileOpStream = new FileOutputStream(dbPath);
			ObjectOutputStream objectOpSteam = new ObjectOutputStream(fileOpStream);
			objectOpSteam.writeObject(photosUsers);
			objectOpSteam.close();
			objectOpSteam.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Method to get .dat file to store data. IF does not exsit it will be created.
	 * @return
	 */
	public File getDbFile() {
		File fileDb = new File(dbPath);
		if (!fileDb.exists() || !fileDb.isFile()) {
			try {
				fileDb.createNewFile();
				PhotoAlbum sAlbum = new PhotoAlbum("stock");
				ArrayList<Photo> albumPhotos = new ArrayList<Photo>();
				File image;
				for (int i = 0; i < 7; i++) {
					String imagePath = ROOT + "/stock/image" + (i+1) + ".jpg";
					image = new File(imagePath);
					if (image != null) {
						String name = image.getName();
						Date date = new Date();
						long timeStamp = date.getTime();
						System.out.print(image.getPath());
						Photo photo = new Photo(name, null, timeStamp, imagePath);
						albumPhotos.add(photo);
					}
				}
				sAlbum.setAlbumPhotos(albumPhotos);
				sAlbum.setTotalPhotos(albumPhotos.size());
				User sUser = new User("stock");
				sUser.addUserAlbum(sAlbum);
				photosUsers.add(sUser);
				updateDB();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fileDb;
	}

	/**
	 * Check if user name exsit in the database.
	 * @param userName - Username to check and return as user object.
	 */
	public User checkUsername(String userName) {
		for(User tUser : photosUsers) {
			if(tUser.getName().equalsIgnoreCase(userName))
			{
				return tUser;
			}
		}
		return null;
	}
	
	/**
	 * Validate user and perform login action
	 */
	public void goLogin() {
		File dbData = getDbFile();
		try {
			FileInputStream fs = new FileInputStream(dbData);
			ObjectInputStream os = new ObjectInputStream(fs);
			photosUsers = (ArrayList)os.readObject();
			os.close();
			fs.close();
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String userName = txtUsername.getText().trim();
		
		if(userName.equalsIgnoreCase(ADMIN)) {
			FXMLLoader fxLoader = new FXMLLoader(getClass().getResource("/view/AdminDashboard.fxml"));
			try {
				Parent root = (Parent)fxLoader.load();
				Scene scene = new Scene(root);
				AdminDashboardController adminController = fxLoader.getController();
				adminController.start(stage);
				stage.setScene(scene);
				stage.centerOnScreen();
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
			User photoUser = checkUsername(userName);
			if(photoUser!=null) {
				loggedInUser = photoUser;
				File direcory = new File(ROOT+"/"+loggedInUser.getName());
				if(!direcory.exists()) {
					direcory.mkdir();
				}
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
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Invalid Login");
				alert.setHeaderText("Invalid User");
				alert.setContentText("Please try to login with valid user.");
				alert.showAndWait();
			}
		}
	}
}
