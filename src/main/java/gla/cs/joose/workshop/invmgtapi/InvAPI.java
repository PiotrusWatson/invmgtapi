package gla.cs.joose.workshop.invmgtapi;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import gla.cs.joose.coursework.invmgtapi.model.Item;
import gla.cs.joose.coursework.invmgtapi.model.ItemFactory;
import gla.cs.joose.coursework.invmgtapi.model.ItemType;

@Path("/invapi")
public class InvAPI {
	
	/**
	 * This function receives request from rest client to delete an item from the inventory
	 * @param itemid
	 * @param uriinfo
	 * @return Return a Response object containing the status code after delete operation
	 */
	
	@DELETE
	@Path("/items/{itemid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteItem(long itemid,@Context UriInfo uriinfo) {		
		
		boolean deleted = ItemFactory.delete(itemid);
		URI uri = uriinfo.getAbsolutePathBuilder().build();
		
		if (deleted) return Response.ok(uri).build();
		else return Response.status(404).build();
		
		// Task 5
		
		
	}
	
	/**
	 * This function receives request from rest client to retrieve a set of items that matches 
	 * a search pattern from the inventory
	 * @param searchbydesc
	 * @param pattern
	 * @param limit
	 * @param uriinfo
	 * @return return a Response object containing a list of items that matches a search pattern and the status code	 * 		  
	 */
	
	@GET
	@Path("/items/{desc}/{pattern}/{limit}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItem(String searchbydesc, String pattern,int limit, @Context UriInfo uriinfo) {        
       
		Item[] results = ItemFactory.search(searchbydesc, pattern, limit);
        URI uri = uriinfo.getAbsolutePathBuilder().build();
		if (results.length !=0)
			return Response.ok(uri).entity(results).status(200).build();
		else return Response.status(404).build();
		
	}
	
	/**
	 * This function receives request from rest client to update an item in the inventory
	 * @param updateitemid
	 * @param newBarcode
	 * @param newItemName
	 * @param newItemType_s
	 * @param newQty
	 * @param newSupplier
	 * @param newDesc
	 * @param uriinfo
	 * @return return a Response object containing  the updated item and the status code
	 */
	@PUT
	@Path("/items/{itemid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateItem(long updateitemid,
							   long newBarcode,
							   String newItemName, 
							   String newItemType_s, 
							   int newQty,
							   String newSupplier,
							   String newDesc,
							   @Context UriInfo uriinfo){	
		
		URI uri = uriinfo.getAbsolutePathBuilder().build();		        
		boolean updated = false;
		
		boolean deleted = ItemFactory.delete(updateitemid);
		Item uitem = null;
		
		if(deleted){
			ItemType itemType = ItemType.getItemType(newItemType_s);
			uitem = new Item(newBarcode, newItemName, itemType, newQty, newSupplier, newDesc);
			uitem.setId(updateitemid);
			boolean done = ItemFactory.addItem(uitem);
			if(done){
				updated = true;
			}
		}	
		
		
		//Task 7
		if (updated) return Response.ok(uri).entity(uitem).status(200).build();
		else return Response.status(404).build();
					
	}
	
	/**
	 * This function receives request from rest client to add an item to the inventory
	 * @param barcode
	 * @param itemName
	 * @param itemType_s
	 * @param qty
	 * @param supplier
	 * @param desc
	 * @param uriinfo
	 * @return return a Response object containing  the status code of the operation
	 */
	@POST
	@Path("/items") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addItem(long barcode,
							String itemName, 
					        String itemType_s, 
					        int qty,
					        String supplier,
					        String desc,
					        @Context UriInfo uriinfo){			
		        
		ItemType itemType = ItemType.getItemType(itemType_s);
		Item item = new Item(barcode, itemName, itemType, qty, supplier, desc);
				
		boolean done = ItemFactory.addItem(item);
		
		// Task 8	
		
		return null;
	}
	
}
