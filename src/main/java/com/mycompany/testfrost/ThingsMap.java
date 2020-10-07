package com.mycompany.testfrost;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JToolTip;

import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

import com.google.gson.JsonObject;

import de.fraunhofer.iosb.ilt.sta.model.Location;
import de.fraunhofer.iosb.ilt.sta.model.ext.EntityList;

public class ThingsMap {
	
	private EntityList<Location> locations;
	
	public ThingsMap() {
		
		displayMap();

	}
	
	
	public ThingsMap(EntityList<Location> loc) {
		
		setLocations(loc);
		
		displayMap();

	}
	
	
	
	public EntityList<Location> getLocations() {
		return locations;
	}


	public void setLocations(EntityList<Location> locations) {
		this.locations = locations;
	}
	
	
	public void createLocationToolTip(JXMapKit map, Object location) {
		System.out.println(location.toString());
		
		final GeoPosition gp = new GeoPosition(48, -3.7); 
		map.setAddressLocation(gp);
		
	    final JToolTip tooltip = new JToolTip();
	    tooltip.setTipText("oui");
	    tooltip.setComponent(map.getMainMap());
	    map.getMainMap().add(tooltip);
	    
	    map.getMainMap().addMouseMotionListener(new MouseMotionListener() {
	        @Override
	        public void mouseDragged(MouseEvent e) { 
	            // ignore
	        }
	
	        @Override
	        public void mouseMoved(MouseEvent e)
	        {
	            JXMapViewer mapV = map.getMainMap();
	
	            // convert to world bitmap
	            Point2D worldPos = mapV.getTileFactory().geoToPixel(gp, mapV.getZoom());
	
	            // convert to screen
	            Rectangle rect = mapV.getViewportBounds();
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
	}
	
	public void displayMap() {
		
		final JXMapKit jXMapKit = new JXMapKit();
	    TileFactoryInfo info = new OSMTileFactoryInfo();
	    DefaultTileFactory tileFactory = new DefaultTileFactory(info);
	    jXMapKit.setTileFactory(tileFactory);
	    
	    final GeoPosition mappos = new GeoPosition(48.2, -4); 
	
	    //location of Java
	    Iterator<Location> locIterator = locations.fullIterator();
	    while(locIterator.hasNext()) {
			Location loc = locIterator.next();
			
			Object objLoc = loc.getLocation();
			
			//JsonObject jsonLoc = (JsonObject) objLoc;
			
			createLocationToolTip(jXMapKit,objLoc);
		    
		} 
	    
	
	    jXMapKit.setZoom(11);
	    
	
	    
	
	    // Display the viewer in a JFrame
	    JFrame frame = new JFrame("JXMapviewer2 Example 6");
	    frame.getContentPane().add(jXMapKit);
	    frame.setSize(800, 600);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	
	}
}
