package Examind;

import java.util.Iterator;

import com.mycompany.testfrost.Connection;
import com.mycompany.testfrost.LocationTooltip;
import com.mycompany.testfrost.ThingsMap;

import de.fraunhofer.iosb.ilt.sta.ServiceFailureException;
import de.fraunhofer.iosb.ilt.sta.model.EntityType;
import de.fraunhofer.iosb.ilt.sta.model.Location;
import de.fraunhofer.iosb.ilt.sta.model.Thing;
import de.fraunhofer.iosb.ilt.sta.model.ext.EntityList;
import de.fraunhofer.iosb.ilt.sta.query.ExpandedEntity;
import de.fraunhofer.iosb.ilt.sta.query.Expansion;
import de.fraunhofer.iosb.ilt.sta.query.InvalidRelationException;

public class ExamindThingsMap extends ThingsMap {

	public ExamindThingsMap() {
		// TODO Auto-generated constructor stub
	}

	public ExamindThingsMap(EntityList<Location> loc, Connection conn) {
		super(loc, conn);
		// TODO Auto-generated constructor stub
	}
	
	protected void builderGraphiquesScreen(LocationTooltip tooltip) {
	   		
	   		EntityList<Thing> things;
	         try {
	        	 things = getConnection().getService().things().query()
							.filter("Thing/id eq "+tooltip.getLoc().getName()+"")
							.expand(Expansion.of(EntityType.THING)
									.with(ExpandedEntity.from(EntityType.DATASTREAMS)))
							.list();
	         	
	         	Iterator<Thing> iThg = things.fullIterator();

	     		while(iThg.hasNext()) {
	     			Thing thing = iThg.next();

	     			new ExamindGraphiquesScreen(thing,getConnection());
					
	            }
	             	                
	             
			} catch (ServiceFailureException | InvalidRelationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	}

}
