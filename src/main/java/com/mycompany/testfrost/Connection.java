/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testfrost;


import de.fraunhofer.iosb.ilt.sta.ServiceFailureException;
import de.fraunhofer.iosb.ilt.sta.model.EntityType;
import de.fraunhofer.iosb.ilt.sta.model.Location;
import de.fraunhofer.iosb.ilt.sta.model.Thing;
import de.fraunhofer.iosb.ilt.sta.model.ext.EntityList;
import de.fraunhofer.iosb.ilt.sta.query.ExpandedEntity;
import de.fraunhofer.iosb.ilt.sta.query.Expansion;
import de.fraunhofer.iosb.ilt.sta.query.InvalidRelationException;
import de.fraunhofer.iosb.ilt.sta.service.SensorThingsService;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.*; 
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author elebihan
 */
public class Connection {
	
	/**
	 * La classe connection permet la connection au server STA
	 */
    
	private URL url ;
    private SensorThingsService service;
    
    
    
    public URL getUrl(){
            return url;
    }
    
    public void setUrl(String newURL){
        try {
            url = new URL(newURL);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setUrl(URL url) {
		this.url = url;
	}
   
    public SensorThingsService getService(){
        return service;
    }
    
    public void setService(SensorThingsService serv){
       service = serv;
    }
    
    public Connection(String rootURL) throws MalformedURLException { 
        url = new URL(rootURL);
        service  = new SensorThingsService(url);
     
    }
    
    public Connection() {
		// TODO Auto-generated constructor stub
	}

	public EntityList<Location> getlastLocations() throws ServiceFailureException, InvalidRelationException{
    	EntityList<Thing> allthings = getService().things().query().expand(Expansion.of(EntityType.THING)
                													.with(ExpandedEntity.from(EntityType.LOCATIONS))).list();
    	
    	EntityList<Location> lastLocations = new EntityList<Location>(EntityType.LOCATION);
    	
    	Iterator<Thing> iThings = allthings.fullIterator();
		while (iThings.hasNext()) {
		    Thing selectedThing = iThings.next();
		    
		    EntityList<Location> thingLoc = selectedThing.getLocations();
		    		    		    
		    Iterator<Location> locations = thingLoc.fullIterator();
		    
		    while(locations.hasNext()) {
		    	Location lastLoc = locations.next();
		    	
		    	lastLocations.add(lastLoc);
		    }
		}
    	
    	return lastLocations;
    }
	
	public void builderMap(EntityList<Location> locations) {
		new ThingsMap(locations,this);
	}
    	
    
}


