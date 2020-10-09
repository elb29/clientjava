package Examind;

import java.math.BigDecimal;

import com.mycompany.testfrost.Connection;
import com.mycompany.testfrost.GraphiquesScreen;

import de.fraunhofer.iosb.ilt.sta.ServiceFailureException;
import de.fraunhofer.iosb.ilt.sta.model.Datastream;
import de.fraunhofer.iosb.ilt.sta.model.EntityType;
import de.fraunhofer.iosb.ilt.sta.model.Observation;
import de.fraunhofer.iosb.ilt.sta.model.Thing;
import de.fraunhofer.iosb.ilt.sta.model.ext.EntityList;
import de.fraunhofer.iosb.ilt.sta.query.ExpandedEntity;
import de.fraunhofer.iosb.ilt.sta.query.Expansion;
import de.fraunhofer.iosb.ilt.sta.query.InvalidRelationException;

public class ExamindGraphiquesScreen extends GraphiquesScreen {

	public ExamindGraphiquesScreen(Thing t, Connection c) {
		super(t, c);
		// TODO Auto-generated constructor stub
	}
	
	protected EntityList<Datastream> requestDSwithData(Datastream datastream) throws ServiceFailureException, InvalidRelationException {
		
		EntityList<Datastream>	dsWithObs = getConnection().getService().datastreams().query()
												.filter("Datastream/id eq "+datastream.getId()+"")
												.expand(Expansion.of(EntityType.DATASTREAMS)
														.with(ExpandedEntity.from(EntityType.OBSERVATIONS))
														.with(ExpandedEntity.from(EntityType.OBSERVED_PROPERTY)))
												.list();	
		
		// TODO Auto-generated method stub
		return dsWithObs;
	}
	
	protected Double extractMesureResult(Observation obs) {
		
		Double result = ((BigDecimal) obs.getResult()).doubleValue();
		
		// TODO Auto-generated method stub
		return result;
	}
}
