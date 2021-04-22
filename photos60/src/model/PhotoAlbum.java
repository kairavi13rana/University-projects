package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PhotoAlbum implements Serializable {

	private static final long versionId = 1L;
	private String name;
	private int totalPhotos;
	private String dateRange;
	private ArrayList<Photo> albumPhotos;

	/**
	 * @param name
	 * @param totalPhotos
	 * @param albumPhotos
	 */
	public PhotoAlbum(String name, int totalPhotos, ArrayList<Photo> albumPhotos) {
		super();
		this.name = name;
		this.totalPhotos = totalPhotos;
		this.albumPhotos = albumPhotos;
	}
	
	/**
	 * @param name
	 */
	public PhotoAlbum(String name) {
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
	 * @return the totalPhotos
	 */
	public int getTotalPhotos() {
		return totalPhotos;
	}

	/**
	 * @param totalPhotos the totalPhotos to set
	 */
	public void setTotalPhotos(int totalPhotos) {
		this.totalPhotos = totalPhotos;
	}
	
	/**
	 * @return the dateRange
	 */
	public String getDateRange() {
		long start = getStartTimestamp();
		long end = getEndTimestamp();
		if(start==0 || end==0) {
			return null;
		}else {
			Date dateS= new Date(start);
			Date dateE= new Date(end);
	        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
			dateRange = df.format(dateS)+" to "+df.format(dateE);
			return dateRange;
		}
		
	}

	/**
	 * @return the albumPhotos
	 */
	public ArrayList<Photo> getAlbumPhotos() {
		return albumPhotos;
	}

	/**
	 * @param albumPhotos the albumPhotos to set
	 */
	public void setAlbumPhotos(ArrayList<Photo> albumPhotos) {
		this.albumPhotos = albumPhotos;
	}
	
	/**
	 * Get start timestamp.
	 */
	public long getStartTimestamp() {
		long timeStamp = 0;
		if(albumPhotos!=null) {
			timeStamp = albumPhotos.get(0).getDatestamp();
			for(Photo p : albumPhotos) {
				if(p.getDatestamp()<timeStamp) {
					timeStamp = p.getDatestamp();
				}
			}
		}
		
		return timeStamp;
	}
	
	/**
	 * Get end timestamp.
	 */
	public long getEndTimestamp() {
		long timeStamp = 0;
		if(albumPhotos!=null) {
			timeStamp = albumPhotos.get(0).getDatestamp();
			for(Photo p : albumPhotos) {
				if(p.getDatestamp()>timeStamp) {
					timeStamp = p.getDatestamp();
				}
			}
		}
		
		return timeStamp;
	}
	
	@Override
    public String toString() {
        return getName();
    }
}
