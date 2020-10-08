package com.mycompany.testfrost;

import java.net.MalformedURLException;

import de.fraunhofer.iosb.ilt.sta.ServiceFailureException;
import de.fraunhofer.iosb.ilt.sta.model.Location;
import de.fraunhofer.iosb.ilt.sta.model.ext.EntityList;

public class ExamindConnection extends Connection {

	public ExamindConnection(String rootURL) throws MalformedURLException {
		super(rootURL);
		// TODO Auto-generated constructor stub
	}
	
	public EntityList<Location> getlastLocations() throws ServiceFailureException{
	    	EntityList<Location> lastLocations = getService().locations().query().list();   		    		    
	    	
	    	return lastLocations;
	    }
	    
}
