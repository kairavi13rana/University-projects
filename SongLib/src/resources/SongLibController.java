//Kairavi & Abhik 
package resources;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Optional;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Song;

public class SongLibController {
	
	
	@FXML
	private ListView<Song> songsList;
	@FXML
	private Label songName;
	@FXML
	private Label artistName;
	@FXML
	private Label albumName;
	@FXML
	private Label year;
	@FXML
	private AnchorPane panSongDetails;
	@FXML
	private AnchorPane panEdit;
	@FXML
	private Label editPanLabel;
	
	@FXML
	private TextField txtNameField;
	@FXML
	private TextField txtArtistField;
	@FXML
	private TextField txtAlbumField;
	@FXML
	private TextField txtYearField;
	@FXML
	private Button btnDelete;
	@FXML
	private Button btnEdit;
	
	private boolean isAdd=false;
	private Song selectedSong;
	private ObservableList<Song> songsObList;
	SongComparator songComparator = new SongComparator();
	// Add a public no-args constructor
    public SongLibController() 
    {
    }
    //Creating primary stage and checking if there is any song and if there is then create songs.txt
    public void start(Stage primaryStage)
    {
    	File songBank = new File("songs.txt");
    	try {
			if(songBank.createNewFile()) {
				//System.out.println("New File created.");
			}
			else {
				//System.out.println("File Already exists.");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	// using scanner method for songbank 
    	songsObList = FXCollections.observableArrayList();
    	try {
			Scanner scanner = new Scanner(songBank);
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] songDetails = line.split("\\|");
				if(songDetails.length==2) {
					songsObList.add(new Song(songDetails[0],songDetails[1]));
				}
				else if(songDetails.length==3) {
					songsObList.add(new Song(songDetails[0],songDetails[1],songDetails[2]));
				}
				else {
					songsObList.add(new Song(songDetails[0],songDetails[1],songDetails[2],songDetails[3]));
				}		
			}
			scanner.close();
		}
		catch(Exception e) {
			
		}
    	//Sorting songs and/or artist in Ascending order 
    	songsList.setItems(new SortedList<Song>(songsObList));
    	songsList.setCellFactory(param -> new ListCell<Song>() {
            @Override
            protected void updateItem(Song item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName()+", "+item.getArtist());
                }
            }
            
            
        });
    	
    	//songsObList.sort(songComparator);
    	FXCollections.sort(songsObList,songComparator);
    	panEdit.setVisible(false);
    	panSongDetails.setVisible(true);
    	// if songlist is empty select the first 
    	if(!songsObList.isEmpty()) {
    		songsList.getSelectionModel().selectFirst();
    		showDetails();
    		//button functions 
    		btnDelete.setDisable(false);
    		btnEdit.setDisable(false);
    		selectedSong = songsList.getSelectionModel().getSelectedItem();
		}
    	else {
    		btnDelete.setDisable(true);
    		btnEdit.setDisable(true);
    	}
    	songsList.getSelectionModel().selectedItemProperty().addListener(
				(obs, oldValue, newValue) -> showDetails());
    	
    	primaryStage.setOnCloseRequest(event -> {
    		writeSongBankToFile();
    	});
    }
    // showing details for the detail page
    private void showDetails() {
		Song song = songsList.getSelectionModel().getSelectedItem();
		if(song != null) {
			songName.setText(song.getName());
			artistName.setText(song.getArtist());
			albumName.setText(song.getAlbum());
			year.setText(song.getYear());
		}
		
	}
    //Checking song existence 
    private int checkIfSongExists(Song songToAdd) {
    	for(int i=0;i<songsObList.size();i++) {
    		if(songsObList.get(i).getName().equalsIgnoreCase(songToAdd.getName()) && songsObList.get(i).getArtist().equalsIgnoreCase(songToAdd.getArtist())) {
    			return i;
    		}
    	}
    	return -1;
    }
    //Checking song existence while editing the list 
    private int checkIfSongExistsWhenEdit(Song songToEdit, int position) {
    	
    	for(int i=0;i<songsObList.size();i++) {
    		if(songsObList.get(i).getName().equalsIgnoreCase(songToEdit.getName()) && songsObList.get(i).getArtist().equalsIgnoreCase(songToEdit.getArtist()) && position!=i) {
    			return i;
    		}
    	}
    	return -1;
    }
    // Checking for the valid input 
    private boolean checkForValidTextInputs(Song song) {
    	if(song.getName().contains("|") || song.getArtist().contains("|") || song.getAlbum().contains("|") || (!song.getYear().isBlank() && !song.getYear().matches("[0-9]+"))) {
    		return false;
    	}
    	else {
    		return true;
    	}
    }
    
    @FXML
    // To add song 
    private void addSong() {
    	panSongDetails.setVisible(false);
    	panEdit.setVisible(true);
    	editPanLabel.setText("Add Song");
    	isAdd = true;
    }
    
    @FXML
    // To save any changes 
    private void saveSong() {
    	
    	String name = txtNameField.getText().trim();
    	String artist = txtArtistField.getText().trim();
    	String album = txtAlbumField.getText().trim();
    	String year = txtYearField.getText().trim();
    	// for the pop up box 
    	if(name.trim().isBlank() || artist.trim().isBlank()) {
    		Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setHeaderText("Please enter valid song details.");
			alert.setContentText("Song name and artist must be provided.");
			alert.showAndWait();
			return;
    	}
    	
    	Song song = new Song(name, artist, album, year);
    	if(checkForValidTextInputs(song)) {
    		if(isAdd) {
        		if(checkIfSongExists(song)>=0) {
            		Alert alert = new Alert(AlertType.WARNING);
        			alert.setTitle("WARNING");
        			alert.setHeaderText("Song already exist.");
        			alert.setContentText("Song with same artist already exist in library.");
        			alert.showAndWait();
        			return;
            	}    		
        		Alert alert = new Alert(AlertType.CONFIRMATION);
    			alert.setTitle("Warning");
    			alert.setHeaderText("Are you sure?");
    			alert.setContentText("This will add new song " + name + " by "+ artist + " in library.\nAre you sure?");			
    			Optional<ButtonType> result = alert.showAndWait();
    			if (result.get() == ButtonType.OK) {
    				songsObList.add(song);
    				txtNameField.setText("");
    				txtArtistField.setText("");
    				txtAlbumField.setText("");
    				txtYearField.setText("");
    				btnDelete.setDisable(false);
    				btnEdit.setDisable(false);
    			} else {
    				return;
    			}
        	}
        	else {
        		int position = checkIfSongExists(selectedSong);
        		if(checkIfSongExistsWhenEdit(song,position)>=0) {
        			Alert alert = new Alert(AlertType.WARNING);
        			alert.setTitle("WARNING");
        			alert.setHeaderText("Song already exist.");
        			alert.setContentText("Song with same artist already exist in library.");
        			alert.showAndWait();
        			return;
        		}
        		else {
        			songsObList.remove(position);
        			songsObList.add(song);
        			txtNameField.setText("");
    				txtArtistField.setText("");
    				txtAlbumField.setText("");
    				txtYearField.setText("");
        		}
        	}    	
    	}
    	else {
    		Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setHeaderText("Please enter valid song details.");
			alert.setContentText("Song name, artist and album must not contains \"|\" .\nSong year must contains only year number in YYYY format.");
			alert.showAndWait();
			return;
    	}
    	panSongDetails.setVisible(true);
    	panEdit.setVisible(false);
    	isAdd = false;
    	FXCollections.sort(songsObList,songComparator);
    	int position = checkIfSongExists(song);
    	if(!songsObList.isEmpty()) {
    		songsList.getSelectionModel().select(position);
    		showDetails();
		}
    }
    
    @FXML
    // for editing 
    private void editSong() {
    	panSongDetails.setVisible(false);
    	panEdit.setVisible(true);
    	editPanLabel.setText("Edit Song");
    	isAdd = false;
    	selectedSong = songsList.getSelectionModel().getSelectedItem();
    	txtNameField.setText(selectedSong.getName());
    	txtArtistField.setText(selectedSong.getArtist());
    	txtAlbumField.setText(selectedSong.getAlbum());
    	txtYearField.setText(selectedSong.getYear());
    }
    
    @FXML
    // for deleting 
    private void deleteSong() {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("WARNING");
		alert.setHeaderText("Are you sure?");
		alert.setContentText("This will delete selected song from library. Are you sure?");
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			int selectedIndex = songsList.getSelectionModel().getSelectedIndex();
			songsObList.remove(selectedIndex);			
			if(songsObList.isEmpty()) {			//Removed the only item in the list
				songName.setText("");
				artistName.setText("");
				albumName.setText("");
				year.setText("");
				btnDelete.setDisable(true);
				btnEdit.setDisable(true);
			} else if (selectedIndex == songsObList.size()-1) {
				songsList.getSelectionModel().select(selectedIndex--);
			} else {
				songsList.getSelectionModel().select(selectedIndex++);
			}
		} else {
			return;
		}
    }
    
    @FXML
    //for go back page 
    private void goBack() {
    	panSongDetails.setVisible(true);
    	panEdit.setVisible(false);
    	txtNameField.setText("");
		txtArtistField.setText("");
		txtAlbumField.setText("");
		txtYearField.setText("");
    }
   // When done making changes write songbank to songs.txt 
    private void writeSongBankToFile() {
		PrintWriter printWriter;
		try {
			File songBank = new File("songs.txt");
			songBank.createNewFile();
			printWriter = new PrintWriter(songBank);
			for(int i = 0; i < songsObList.size(); i++) {
				printWriter.println(songsObList.get(i).getName()+"|"+songsObList.get(i).getArtist()+"|"+songsObList.get(i).getAlbum()+"|"+songsObList.get(i).getYear());
			}
			printWriter.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

//Comparator class to define two level of sorting for Song list view 
class SongComparator implements Comparator<Song> {
	  @Override
	  public int compare(Song song1, Song song2) {
		  if(song1.getName().equalsIgnoreCase(song2.getName())) {
			  return song1.getArtist().compareToIgnoreCase(song2.getArtist());
		  }
		  else {
			  return song1.getName().compareToIgnoreCase(song2.getName());
		  }	      
	  }
}
