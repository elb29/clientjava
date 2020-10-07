package com.mycompany.testfrost;


import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JFrame;
import javax.swing.JToolTip;

import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

import com.google.gson.JsonObject;

import de.fraunhofer.iosb.ilt.sta.ServiceFailureException;
import de.fraunhofer.iosb.ilt.sta.model.Datastream;
import de.fraunhofer.iosb.ilt.sta.model.EntityType;
import de.fraunhofer.iosb.ilt.sta.model.Location;
import de.fraunhofer.iosb.ilt.sta.model.Thing;
import de.fraunhofer.iosb.ilt.sta.model.ext.EntityList;
import de.fraunhofer.iosb.ilt.sta.query.ExpandedEntity;
import de.fraunhofer.iosb.ilt.sta.query.Expansion;
import de.fraunhofer.iosb.ilt.sta.query.InvalidRelationException;

public class ThingsMap {
	
	private EntityList<Location> locations;
	private Connection connection;
	
	public Connection getConnection() {
		return connection;
	}


	public void setConnection(Connection connection) {
		this.connection = connection;
	}


	public ThingsMap() {
		
		displayMap();

	}
	
	
	public ThingsMap(EntityList<Location> loc, Connection conn) {
		
		setLocations(loc);
		setConnection(conn);
		
		displayMap();

	}
	
	
	
	public EntityList<Location> getLocations() {
		return locations;
	}


	public void setLocations(EntityList<Location> locations) {
		this.locations = locations;
	}
	
	
	public GeoPosition createLocationToolTip(JXMapKit map, Location loc) {
		Object location = loc.getLocation();
		
		System.out.println(location.toString());
		
		final GeoPosition gp = new GeoPosition(ThreadLocalRandom.current().nextInt(-50,50), ThreadLocalRandom.current().nextInt(-50,50)); 
		
			
	    final LocationTooltip tooltip = new LocationTooltip(loc);
	    tooltip.setTipText(loc.getName());
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
	    
	    map.getMainMap().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
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
	
	                //creation fenetre de graphiques
	                
	                
	                
	                EntityList<Thing> things;
	                try {
						things = connection.getService().things().query()
																.filter("name eq '"+loc.getName()+"'")
																.expand(Expansion.of(EntityType.THING)
														                .with(ExpandedEntity.from(EntityType.DATASTREAMS)))
																.list();
						
						Iterator<Thing> iThg = things.fullIterator();
		                
		                while(iThg.hasNext()) {
		                	Thing thing = iThg.next();
		                	
		                	EntityList<Datastream> ds = thing.getDatastreams();
		                	
		                	
		                	
		                }
		                	                
		                //GraphiquesScreen graphScr = new GraphiquesScreen();	
					} catch (ServiceFailureException | InvalidRelationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	                                
	            }
	            else
	            {
	                tooltip.setVisible(false);
	            }
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
	    	
	    });
	    return(gp);
	}
	
	public void displayMap() {
		
		final JXMapKit jXMapKit = new JXMapKit();
	    TileFactoryInfo info = new OSMTileFactoryInfo();
	    DefaultTileFactory tileFactory = new DefaultTileFactory(info);
	    jXMapKit.setTileFactory(tileFactory);
	    
	    final GeoPosition mappos = new GeoPosition(48.2, -4); 
	    
	    
	    List<Waypoint> listWaypoints = new ArrayList<Waypoint>();
	    
	    //location of Java
	    Iterator<Location> locIterator = locations.fullIterator();
	    while(locIterator.hasNext()) {
			Location loc = locIterator.next();
		
			GeoPosition gp = createLocationToolTip(jXMapKit,loc);
			
			listWaypoints.add(new DefaultWaypoint(gp));
		}
	    
	    Set<Waypoint> waypoints = new HashSet<Waypoint>(listWaypoints);
	    
	    WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
        waypointPainter.setWaypoints(waypoints);

        // Create a compound painter that uses both the route-painter and the waypoint-painter
        List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
        painters.add(waypointPainter);

        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
        jXMapKit.getMainMap().setOverlayPainter(painter);
	    
	
	    jXMapKit.setZoom(11);
	    jXMapKit.setAddressLocation(mappos);    
	
	    
	
	    // Display the viewer in a JFrame
	    JFrame frame = new JFrame("Locations Map");
	    frame.getContentPane().add(jXMapKit);
	    frame.setSize(800, 600);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	
	}
}
