package Embeded_Project;

import org.ws4d.coap.core.rest.CoapResourceServer;

import Project_1.PIR_Sensor;
import Project_1.PIR_Server;

public class Project_Server {
	private static PIR_Server coapServer;
	private CoapResourceServer resourceServer;
	
	public static void main(String[] args) {
		coapServer = new PIR_Server();
		coapServer.start();
	}
	
	public void start() {
		System.out.println("===Run Test Server ===");

		// create server
		if (this.resourceServer != null)	this.resourceServer.stop();
		this.resourceServer = new CoapResourceServer();
		// initialize resource
		
		PIR_Sensor pir_sensor = new PIR_Sensor(); 
		this.resourceServer.createResource(pir_sensor);
		pir_sensor.registerServerListener(resourceServer); 


		// add resource to server
		
		
		// run the server
		try {
			this.resourceServer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		while(true) {
			try {
				Thread.sleep(3000);
				pir_sensor.optional_changed();
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		

	}

}
