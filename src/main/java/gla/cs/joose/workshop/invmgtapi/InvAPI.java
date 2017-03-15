package gla.cs.joose.workshop.invmgtapi;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	
	@DELETE //TYPE OF HTML REQUEST
	@Path("/items/{itemid}") //WHERE TO LOOK FOR IT {itemid} is like a variable lol
	@Produces(MediaType.APPLICATION_JSON) //WHAT TYPE OF THING IT SENDS BACK AS A RESPONSE
	public Response deleteItem(@PathParam("itemid") long itemid,@Context UriInfo uriinfo) {		
		//~~actual logic dont touch unless josh ~~
		boolean deleted = ItemFactory.delete(itemid);
		//~~actual logic dont touch unless josh ~~
		
		//takes the uri passed to it and returns the full path
		URI uri = uriinfo.getAbsolutePathBuilder().build();
		
		
		if (deleted) return Response.ok(uri).status(200).build();//if successful, returns that it did what it was asked
		else return Response.status(404).build(); //else it returns that it couldn't find the item
		
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
	
	@GET //as it's a get im not sure i want to actually make it consume anything
	@Path("/items/{desc}/{pattern}/{limit}") //im not sure if this is right but we can feed parameters using uris?
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItem(@PathParam("desc") String searchbydesc, @PathParam("pattern") String pattern,
			@PathParam("limit") int limit, @Context UriInfo uriinfo) {        
		
		
		//~~actual logic dont touch unless josh ~~
		Item[] results = ItemFactory.search(searchbydesc, pattern, limit);
		//~~actual logic dont touch unless josh ~~
		
        URI uri = uriinfo.getAbsolutePathBuilder().build(); 
        
		if (results.length !=0) //if more than one result it prob found something
			return Response.ok(uri).entity(results).status(200).build(); //so returns that it found stuff
		else return Response.status(404).build(); //otherwise it didn't :((
		
		//Task 6
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
	public Response updateItem(@PathParam("itemid") long updateitemid,
							   long newBarcode,
							   String newItemName, 
							   String newItemType_s, 
							   int newQty,
							   String newSupplier,
							   String newDesc,
							   @Context UriInfo uriinfo){	
		
		URI uri = uriinfo.getAbsolutePathBuilder().build();		
		
		//~~actual logic dont touch unless josh ~~
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
		//~~actual logic dont touch unless josh ~~
		
		
		if (updated) return Response.ok(uri).entity(uitem).status(200).build(); //i did thing!
		else return Response.status(404).build(); //i didn't :(
		//Task 7		
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
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addItem(long barcode,
							String itemName, 
					        String itemType_s, 
					        int qty,
					        String supplier,
					        String desc,
					        @Context UriInfo uriinfo){			
		URI uri = uriinfo.getAbsolutePathBuilder().build();	//todo: update this
		
		//~~actual logic dont touch unless josh ~~
		ItemType itemType = ItemType.getItemType(itemType_s);
		Item item = new Item(barcode, itemName, itemType, qty, supplier, desc);
		
		boolean done = ItemFactory.addItem(item);
		//~~actual logic dont touch unless josh ~~
		
		
		if (done) return Response.created(uri).build(); //need to update this with accurate info
		else return Response.status(404).build(); 
		
		// Task 8-- DEFINITELY STILL NEEDS WORK
	}
	
}
