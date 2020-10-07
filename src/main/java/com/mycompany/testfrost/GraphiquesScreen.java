package com.mycompany.testfrost;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler.LegendPosition;



import de.fraunhofer.iosb.ilt.sta.ServiceFailureException;
import de.fraunhofer.iosb.ilt.sta.model.Datastream;
import de.fraunhofer.iosb.ilt.sta.model.EntityType;
import de.fraunhofer.iosb.ilt.sta.model.Observation;
import de.fraunhofer.iosb.ilt.sta.model.Thing;
import de.fraunhofer.iosb.ilt.sta.model.ext.EntityList;
import de.fraunhofer.iosb.ilt.sta.query.ExpandedEntity;
import de.fraunhofer.iosb.ilt.sta.query.Expansion;
import de.fraunhofer.iosb.ilt.sta.query.InvalidRelationException;

public class GraphiquesScreen {
	
	private Thing thing;
	private Connection connection;

	public GraphiquesScreen(Thing t, Connection c) {
		// TODO Auto-generated constructor stub
		thing = t;
		connection = c;
		
		displayGraph();
	}
	
	public Thing getThing() {
		return thing;
	}

	public void setThing(Thing thing) {
		this.thing = thing;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void displayGraph() {
		// Display a JFrame
		JFrame frame = new JFrame("Data from the Thing : " + thing.getName());
		
		EntityList<Datastream> ds = thing.getDatastreams();
		
		Iterator<Datastream> iDs = ds.fullIterator();
        
        while(iDs.hasNext()) {
        	Datastream datastream = iDs.next();

        	EntityList<Datastream> dsWithObs;
        	System.out.println("donedddd");
            try {
            	dsWithObs = getConnection().getService().datastreams().query()
														.filter("@iot.id eq '"+datastream.getId()+"'")
														.expand(Expansion.of(EntityType.DATASTREAMS)
												                .with(ExpandedEntity.from(EntityType.OBSERVATIONS)))
														.list();
				
            	
				Iterator<Datastream> iDSWithObs = dsWithObs.fullIterator();
                
                while(iDSWithObs.hasNext()) {
                	
                	Datastream dsObs = iDSWithObs.next();
                	
                	final XYChart chart = createChart(dsObs);
                	
                	JPanel chartPanel = new XChartPanel<XYChart>(chart);
                    frame.add(chartPanel, BorderLayout.CENTER);
                }
                	                
                
			} catch (ServiceFailureException | InvalidRelationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        	
        	
        }		
		
		
	    frame.setSize(800, 600);
	    frame.setVisible(true);
	}
	
	public XYChart createChart(Datastream ds) {
		
			
		// Create Chart
		final XYChart chart = new XYChartBuilder().width(600).height(400).title("Chart").xAxisTitle("X").yAxisTitle("Y").build();
		
		// Customize Chart
		chart.getStyler().setLegendPosition(LegendPosition.InsideNE);
		//chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Area);
		
		List<Double> xDate = new ArrayList<Double>();
		List<Double> yResult = new ArrayList<Double>();
		
		
		
		
		EntityList<Observation> observations = ds.getObservations();
		
		Iterator<Observation> iObs = observations.fullIterator();
		double i = 0;
		
		
		while(iObs.hasNext()) {			
			
			Observation obs = iObs.next();
			
		    //xDate.add(obs.getResultTime());
			xDate.add(i);
			//yResult.add((Double) obs.getResult());
			yResult.add(ThreadLocalRandom.current().nextDouble(0,50));
			
			i ++;
		}
		
		//add data to chart	
		//chart.addSeries(ds.getName(), new double[] { 0, 2, 4, 6, 9 }, new double[] { 0, 2, 4, 6, 9 });
		
		//double[] x = new double[] {};
		//double[] y = new double[] {};
		
		chart.addSeries(ds.getName(), xDate, yResult);
		
		return chart;
	}

}
