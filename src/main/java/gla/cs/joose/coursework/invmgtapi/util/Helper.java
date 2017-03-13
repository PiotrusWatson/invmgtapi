package gla.cs.joose.coursework.invmgtapi.util;

/**
 *
 * @author inah Omoronyia
 */
public class Helper {

	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
}
