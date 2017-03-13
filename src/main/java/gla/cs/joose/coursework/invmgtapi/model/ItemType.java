package gla.cs.joose.coursework.invmgtapi.model;

/**
 *
 * @author inah Omoronyia
 */

import java.io.Serializable;
import java.util.Arrays;

public enum ItemType  implements Serializable{
	ELECTRONIC, OUTDOOR, HEALTH, CLOTH, BOOK, CAR, GARDEN, OTHERS;

	public static String getTypeDesc(ItemType type){
		return type.name().toLowerCase();
	}
	
	public static ItemType getItemType(final String desc){
		return Arrays.stream(ItemType.values()).filter(e -> desc.contains(getTypeDesc(e))).findAny().orElse(null);
	}
}