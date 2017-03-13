package gla.cs.joose.coursework.invmgtapi.util;

/**
 * @author inah Omoronyia
 */

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum SearchBy implements Serializable {
    ITEM_TYPE, DESCRIPTION, BARCODE, QUANTITY, SUPPLIER, ITEM_NAME;

    public static String getSearchByDesc(SearchBy type) {
        return (type.name().contains("_") ?
                Arrays.stream(type.name().split("_")).map(x -> x.substring(0, 1).toUpperCase() + x.substring(1)).collect(Collectors.joining(" ")) :
                type.name().substring(0, 1).toUpperCase() + type.name().substring(1));
//        String searchbydesc = null;
//
//        switch (type) {
//            case ITEM_TYPE:
//                searchbydesc = "Item Type";
//                break;
//            case DESCRIPTION:
//                searchbydesc = "Description";
//                break;
//            case BARCODE:
//                searchbydesc = "Barcode";
//                break;
//            case QUANTITY:
//                searchbydesc = "Quantity";
//                break;
//            case SUPPLIER:
//                searchbydesc = "Supplier";
//                break;
//            case ITEM_NAME:
//                searchbydesc = "Item Name";
//                break;
//        }
//        return searchbydesc;
    }

    public static SearchBy getSearchBy(String searchByDesc) {
        return searchByDesc != null ?
                Arrays.stream(SearchBy.values()).filter(e -> searchByDesc.contains(getSearchByDesc(e))).findFirst().orElse(SearchBy.DESCRIPTION) :
                SearchBy.DESCRIPTION;
    }
}