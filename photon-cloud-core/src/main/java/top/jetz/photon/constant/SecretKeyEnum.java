/**  
 * Copyright © 2018 personal. All rights reserved.
 * @Description: //TODO
 * @author: qiangcao  
 * @date: Jun 27, 2018 11:35:21 AM
 * @version: V1.0  
 */
package top.jetz.photon.constant;

// TODO: Auto-generated Javadoc
/**
 * The Enum SecretKeyEnum.
 */
public enum SecretKeyEnum {	

/** The key. */
//	KEY("SomeLooooooooooooongStringForEncNJusttoConfuseThepeople");
	KEY("PassWoooooooorld");
	
	/** The val. */
	private String val;
	
	/**
	 * Instantiates a new secret key enum.
	 *
	 * @param val the val
	 */
	private SecretKeyEnum(String val) {
		this.val=val;
	}
	public String getVal() {
		return val;
	}
	public String getReverseVal() {
		return new StringBuilder(val).reverse().toString();
	}
}
