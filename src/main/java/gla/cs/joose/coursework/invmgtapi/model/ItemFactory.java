package gla.cs.joose.coursework.invmgtapi.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author inah Omoronyia
 */

import gla.cs.joose.coursework.invmgtapi.util.SearchBy;
import gla.cs.joose.coursework.invmgtapi.util.Util;

public class ItemFactory {
		
	public static boolean addItem(Item item) {
        return item.getItemName() != null && item.getItemName().length() > 0 && !containsSpecialCharacter(item.getItemName()) && item.getSupplier() != null && item.getSupplier().length() > 0 && !containsSpecialCharacter(item.getSupplier()) && !itemSupplierExist(item.getItemName(), item.getSupplier()) && item.getBarcode() > 99 && item.getBarcode() < 9999 && item.getQuantity() >= 0 && item.getQuantity() <= 100 && Util.storeItem(item);
    }
	
	public static boolean addItem(long barcode, String itemName, String itemType_s, int qty, String supplier, String desc){
		boolean done = false;
        if (itemType_s != null && itemName != null && itemName.length() > 0 &&
                !containsSpecialCharacter(itemName) && supplier != null && supplier.length() > 0 &&
                !containsSpecialCharacter(supplier) && !itemSupplierExist(itemName, supplier) &&
                barcode > 99 && barcode < 9999 && qty >= 0 && qty <= 100) {
            done = Util.storeItem(new Item(barcode, itemName, ItemType.getItemType(itemType_s), qty, supplier, desc));
        }
		return done;
	}
	
	public static Item[] search(String searchbydesc, String pattern, int limit){
		return Util.getMatchingItems(pattern != null ? pattern : "" , SearchBy.getSearchBy(searchbydesc), limit);
	}
	
	public static boolean delete(Item item){
		return Util.deleteItem(item);
	}
	
	public static boolean delete(long itemId){
		return Util.deleteItem(itemId);
	}
	
	private static boolean itemSupplierExist(String itemName, String supplierName){
		return Util.getSupplierItem(itemName, supplierName) != null;
	}
	
	private static boolean containsSpecialCharacter(String text){
		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(text);
		return m.find();
	}
	

}
