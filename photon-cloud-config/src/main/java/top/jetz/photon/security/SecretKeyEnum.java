/**
 * @author qiangcao
 * @year 2018
 * @date May 16, 2018
 */
package top.jetz.photon.security;

/**
 * @author qiangcao
 * @year 2018
 * @date May 16, 2018
 */
public enum SecretKeyEnum {	
	KEY("SomeLooooooooooooongStringForEncNJusttoConfuseThepeople");
	private String val;
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
