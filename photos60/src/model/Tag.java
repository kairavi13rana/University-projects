package model;

import java.io.Serializable;

public class Tag implements Serializable {

	private String name;
	private String value;
	private static final long versionId = 1L;
	/**
	 * @param name
	 * @param value
	 */
	public Tag(String name, String value) {
		super();
		this.name = name;
		this.value = value;
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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
    public String toString() {
        return getName();
    }
}
