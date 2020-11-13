package mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttPublisher {
	public static int getTemperature(int minTemp, int maxTemp) {
		return (int)(Math.random() * (maxTemp-minTemp+1) + minTemp);
	}
	
    public static void main(String[] args) {
        String topic = "temperature";
        int qos = 0, temp = 0;
        String broker = "tcp://localhost:1883";
        String clientId = "JavaSample";
        MemoryPersistence persistence = new MemoryPersistence();

        try(MqttClient sampleClient = new MqttClient(broker, clientId, persistence)) {
        	System.out.println(".: Temperature Sensor :.");
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: "+broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected to broker");
            System.out.println("Starting measures...");
            System.out.println("--------------------------------------");
            
            do {
            	System.out.println("Measuring temperature...");
            	temp = getTemperature(15, 46);
            	String msg = temp + " °C";
            	MqttMessage message = new MqttMessage(msg.getBytes());
            	message.setQos(qos);
            	System.out.println("Current temperature: " + msg);
            	sampleClient.publish(topic, message);
            	System.out.println("The temperature was published!\n");
            	Thread.sleep(1000);
            }while(true);
            
        } catch(MqttException me) {
	        System.out.println("reason "+me.getReasonCode());
	        System.out.println("msg "+me.getMessage());
	        System.out.println("loc "+me.getLocalizedMessage());
	        System.out.println("cause "+me.getCause());
	        System.out.println("excep "+me);
	        me.printStackTrace();
        } catch(InterruptedException e) {
        	System.out.println("Error" + e.getMessage());
        	e.printStackTrace();
        }
    }
}