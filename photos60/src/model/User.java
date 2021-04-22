package model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{

	private String name;
	private static final long versionId = 1L;
	private ArrayList<PhotoAlbum> userAlbumList = new ArrayList<PhotoAlbum>();
	/**
	 * @param name
	 */
	public User(String name) {
		super();
		this.name = name;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the userAlbumList
	 */
	public ArrayList<PhotoAlbum> getUserAlbumList() {
		return userAlbumList;
	}
	/**
	 * @param userAlbumList the userAlbumList to set
	 */
	public void setUserAlbumList(ArrayList<PhotoAlbum> userAlbumList) {
		this.userAlbumList = userAlbumList;
	}
	
	/**
	 * @param album the album to add
	 */
	public void addUserAlbum(PhotoAlbum album) {
		this.userAlbumList.add(album);
	}
	
	/**
	 * @param album the album to add
	 */
	public void removeUserAlbum(PhotoAlbum album) {
		this.userAlbumList.remove(album);
	}
}
