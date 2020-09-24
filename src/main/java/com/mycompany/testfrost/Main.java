/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testfrost;

import de.fraunhofer.iosb.ilt.sta.ServiceFailureException;
import de.fraunhofer.iosb.ilt.sta.model.Thing;
import de.fraunhofer.iosb.ilt.sta.model.ext.EntityList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.util.Iterator;

import javax.swing.*;
/**
 *
 * @author elebihan
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws ServiceFailureException 
     */
    public static void main(String[] args) throws MalformedURLException, ServiceFailureException {
        // TODO code application logic here
    	
    	JFrame f=new JFrame();//creating instance of JFrame  
    	
    	JLabel l1;
    	l1=new JLabel("Root service URL :");  
    	l1.setBounds(130,50, 200,40); 
    	f.add(l1);
    	
    	
    	JTextField urlText=new JTextField("http://visi-sxt-docker.ifremer.fr:8181/FROST-Server/v1.0");  
        urlText.setBounds(130,100, 150,20); 
        f.add(urlText);
        
    	JButton validateButton=new JButton("Validate");//creating instance of JButton  
    	validateButton.setBounds(130,200,100, 40);//x axis, y axis, width, height  
    	          
    	validateButton.addActionListener(new ActionListener() { 
    		  public void actionPerformed(ActionEvent e) { 
    			    try {
						Connection staConn = new Connection(urlText.getText());
						
						System.out.println(staConn.getlastLocations());
						
				    } catch (MalformedURLException | ServiceFailureException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    			  }
    	});
    
    	f.add(validateButton);//adding button in JFrame  
    	          
    	f.setSize(400,500);//400 width and 500 height  
    	f.setLayout(null);//using no layout managers  
    	f.setVisible(true);//making the frame visible  
    	     
    

    }
}
