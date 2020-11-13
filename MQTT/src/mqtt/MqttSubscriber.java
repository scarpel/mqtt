package mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
public class MqttSubscriber implements MqttCallback  {

	private static final String brokerUrl ="tcp://localhost:1883";
	private static final String clientId = "clientId";
	private static final String topic = "temperature";

	public static void main(String[] args) {
		System.out.println(".: Temperature Sensor Subscriber :.");
		new MqttSubscriber().subscribe(topic);
	}

	public void subscribe(String topic) {
		MemoryPersistence persistence = new MemoryPersistence();
		try(MqttClient sampleClient = new MqttClient(brokerUrl, clientId, persistence)){	
			
			System.out.println("Connecting to broker " + brokerUrl +"...");
			
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);

			sampleClient.connect(connOpts);
			
			System.out.println("Connected successfully!");

			sampleClient.setCallback(this);
			sampleClient.subscribe(topic);

			System.out.println("Subscribed to temperature publisher!");
			System.out.println("Listening to temperatures...\n");
			System.out.println("--------------------------------------");

		} catch (MqttException me) {
			System.out.println(me);
		}
	}

	 //Called when the client lost the connection to the broker
	public void connectionLost(Throwable arg0) {
		
	}

	//Called when a outgoing publish is complete
	public void deliveryComplete(IMqttDeliveryToken arg0) {

	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("New temperature: " + message + "\n");
	}

}