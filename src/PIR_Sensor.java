package Embeded_Project;

import java.util.List;

import org.ws4d.coap.core.enumerations.CoapMediaType;
import org.ws4d.coap.core.rest.BasicCoapResource;
import org.ws4d.coap.core.rest.CoapData;
import org.ws4d.coap.core.tools.Encoder;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.RaspiPin;

import mini_project.I2CLCD;

public class PIR_Sensor extends BasicCoapResource{
	private String pirstate = "Not detected";
	
	GpioController gpio = GpioFactory.getInstance();
	GpioPinDigitalInput pir = gpio.provisionDigitalInputPin(RaspiPin.GPIO_25);
	
	private PIR_Sensor(String path, String value, CoapMediaType mediaType) {
		super(path, value, mediaType);
	}

	public PIR_Sensor() {
		this("/pir", "Not detected", CoapMediaType.text_plain);
	}

	@Override
	public synchronized CoapData get(List<String> query, List<CoapMediaType> mediaTypesAccepted) {
		return get(mediaTypesAccepted);
	}
	
	@Override
	public synchronized CoapData get(List<CoapMediaType> mediaTypesAccepted) {
		boolean current_state = pir.isHigh();
		if (current_state == true) {
			this.pirstate = "Detected";
		}
		else{
			this.pirstate = "Not detected";
		}
	
		return new CoapData(Encoder.StringToByte(this.pirstate), CoapMediaType.text_plain);
	}

	public synchronized void optional_changed() {		
		boolean current_state = pir.isHigh();
		if (current_state == true) {
			if (this.pirstate.equals("Detected")) {
				System.out.println("Customer Eat Now");
				try {
					Thread.sleep(5000);
				}catch (Exception e) {
			}
		}
			else {
				System.out.println("Detected!");
				System.out.println("Customer Start Eating");
				this.pirstate = "Detected";
				this.changed(this.pirstate);
			}
		}
	
		else {
			if (this.pirstate.equals("Not Detected")) {
				System.out.println("Please Clean this Table");
			}
			else {
				System.out.println("Not Detected!");
				System.out.println("Customer Finishd Eating");
				this.pirstate = "Not Detected";
				this.changed(this.pirstate);
			}
		}
		
	}
	
	
	
	@Override
	public synchronized boolean setValue(byte[] value) {
		this.pirstate = Encoder.ByteToString(value);
		return true;
	}	
	
	@Override
	public synchronized boolean post(byte[] data, CoapMediaType type) {
		return this.setValue(data);
	}

	@Override
	public synchronized boolean put(byte[] data, CoapMediaType type) {
		return this.setValue(data);
	}
	
	@Override
	public synchronized String getResourceType() {
		return "Raspberry pi 4 Temperature Sensor";
	}
}