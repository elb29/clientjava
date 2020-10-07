package com.mycompany.testfrost;

import javax.swing.JFrame;

import de.fraunhofer.iosb.ilt.sta.model.Datastream;
import de.fraunhofer.iosb.ilt.sta.model.Thing;
import de.fraunhofer.iosb.ilt.sta.model.ext.EntityList;

public class GraphiquesScreen {
	
	private Thing thing;

	public GraphiquesScreen(Thing t) {
		// TODO Auto-generated constructor stub
		thing = t;
		
		displayGraph();
	}
	
	public void displayGraph() {
		EntityList<Datastream> ds = thing.getDatastreams();
		
		
		// Display a JFrame
		JFrame frame = new JFrame("Data from the Thing : " + thing.getName());
	    frame.setSize(800, 600);
	    frame.setVisible(true);
	}

}
