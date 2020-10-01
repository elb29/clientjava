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
import de.fraunhofer.iosb.ilt.sta.service.SensorThingsService;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*; 
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JToolTip;

import org.jxmapviewer.*;
import org.jxmapviewer.viewer.*;


/**
 *
 * @author elebihan
 */
public class Connection {
    
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
        
        final JXMapKit jXMapKit = new JXMapKit();
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        jXMapKit.setTileFactory(tileFactory);

        //location of Java
        final GeoPosition gp = new GeoPosition(48.2, -4); 

        final JToolTip tooltip = new JToolTip();
        tooltip.setTipText("Java");
        tooltip.setComponent(jXMapKit.getMainMap());
        jXMapKit.getMainMap().add(tooltip);

        jXMapKit.setZoom(11);
        jXMapKit.setAddressLocation(gp);

        jXMapKit.getMainMap().addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) { 
                // ignore
            }

            @Override
            public void mouseMoved(MouseEvent e)
            {
                JXMapViewer map = jXMapKit.getMainMap();

                // convert to world bitmap
                Point2D worldPos = map.getTileFactory().geoToPixel(gp, map.getZoom());

                // convert to screen
                Rectangle rect = map.getViewportBounds();
                int sx = (int) worldPos.getX() - rect.x;
                int sy = (int) worldPos.getY() - rect.y;
                Point screenPos = new Point(sx, sy);

                // check if near the mouse
                if (screenPos.distance(e.getPoint()) < 20)
                {
                    screenPos.x -= tooltip.getWidth() / 2;

                    tooltip.setLocation(screenPos);
                    tooltip.setVisible(true);
                }
                else
                {
                    tooltip.setVisible(false);
                }
            }
        });

        // Display the viewer in a JFrame
        JFrame frame = new JFrame("JXMapviewer2 Example 6");
        frame.getContentPane().add(jXMapKit);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public EntityList<Location> getlastLocations() throws ServiceFailureException{
    	EntityList<Thing> allthings = service.things().query().list();
    	
    	EntityList<Location> lastLocations = new EntityList<Location>(EntityType.LOCATION);
    	
    	Iterator<Thing> iThings = allthings.fullIterator();
		while (iThings.hasNext()) {
		    Thing selectedThing = iThings.next();
		    
		    EntityList<Location> thingLoc = selectedThing.locations().query().list();
		    		    		    
		    Iterator<Location> locations = thingLoc.fullIterator();
		    
		    while(locations.hasNext()) {
		    	Location lastLoc = locations.next();
		    	
		    	lastLocations.add(lastLoc);
		    }
		    
		}
    	
    	return lastLocations;
    }
    	
    
}


