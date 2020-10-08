package com.mycompany.testfrost;

import javax.swing.JToolTip;

import de.fraunhofer.iosb.ilt.sta.model.Location;

public class LocationTooltip extends JToolTip {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Location loc;

	public Location getLoc() {
		return this.loc;
	}

	public void setLoc(Location location) {
		this.loc = location;
	}

	public LocationTooltip(Location location) {
		// TODO Auto-generated constructor stub
		loc = location;
	}

}
