package model;
import java.io.Serializable;
import java.util.ArrayList;

public class Photo implements Serializable{
	
	private static final long versionId = 1L;
	private String name;
	private String caption;
	private long datestamp;
	private String filePath;
	private ArrayList<Tag> photoTags;
	/**
	 * @param name
	 * @param caption
	 * @param datestamp
	 * @param filePath
	 */
	public Photo(String name, String caption, long datestamp, String filePath) {
		super();
		this.name = name;
		this.caption = caption;
		this.datestamp = datestamp;
		this.filePath = filePath;
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
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}
	/**
	 * @param caption the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	/**
	 * @return the datestamp
	 */
	public long getDatestamp() {
		return datestamp;
	}
	/**
	 * @param datestamp the datestamp to set
	 */
	public void setDatestamp(long datestamp) {
		this.datestamp = datestamp;
	}
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	/**
	 * @return the photoTags
	 */
	public ArrayList<Tag> getPhotoTags() {
		return photoTags;
	}
	/**
	 * @param photoTags the photoTags to set
	 */
	public void setPhotoTags(ArrayList<Tag> photoTags) {
		this.photoTags = photoTags;
	}
	
	
	
}
