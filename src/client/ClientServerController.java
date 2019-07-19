package client;

import server.database.EventType;
import server.database.MessageModel;
import server.database.RequestType;
import utlis.LogUtils;
import utlis.Utils;
import server.service.WebInterface;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

public class ClientServerController {

	private static ClientServerController csc = null;
	private static final String MTL_URL = "http://localhost:" + Utils.MTL_SERVER_PORT + "/mtl?wsdl";
	private static final String TOR_URL = "http://localhost:" + Utils.TOR_SERVER_PORT + "/tor?wsdl";
	private static final String OTW_URL = "http://localhost:" + Utils.OTW_SERVER_PORT + "/otw?wsdl";

	private ClientServerController() {
	}

	public static ClientServerController getInstance() {
		if(csc == null)
			csc = new ClientServerController();
		return csc;
	}



	public void managerBookEvent(String managerID, String customerID, RequestType request, String eventID, EventType eventType) {

		WebInterface webInterface = init(customerID);

		if (webInterface != null) {
			MessageModel messageModel = new MessageModel();
			messageModel.setClientID(customerID);
			messageModel.setRequestType(request);
			messageModel.setEventID(eventID);
			messageModel.setEventType(eventType);
			System.out.println(webInterface.processRequest(messageModel));
			LogUtils.writeToFile("Manager_ID_" + managerID + ".txt", " Client_ID_" + customerID + " Request: " + request + '|' + " Event ID: " + eventID + "| Event Type: " + eventType);
		}
		else{
			System.out.println("Failed to connect server!");
		}
	}

	public void managerGetBookingSchedule(String managerID, String customerID, RequestType request) {
		//get booking schedule
		WebInterface webInterface = init(customerID);

		if (webInterface != null) {
			MessageModel messageModel = new MessageModel();
			messageModel.setClientID(customerID);
			messageModel.setRequestType(request);
			System.out.println(webInterface.processRequest(messageModel));
			LogUtils.writeToFile("Manager_ID_" + managerID + ".txt", "Client_ID_" + customerID + ".txt" + '|' + " Request: " + request);
		}
		else{
			System.out.println("Failed to connect server!");
		}

	}

	public void managerCancelEvent(String managerID, String customerID, RequestType request, String eventID, EventType eventType) {
		//cancel event
		WebInterface webInterface = init(customerID);

		if (webInterface != null) {
			MessageModel messageModel = new MessageModel();
			messageModel.setClientID(customerID);
			messageModel.setRequestType(request);
			messageModel.setEventID(eventID);
			messageModel.setEventType(eventType);
			System.out.println(webInterface.processRequest(messageModel));
			LogUtils.writeToFile("Manager_ID_" + managerID + ".txt", "Customer ID: " + customerID + " | Event ID: " + eventID + '|' + " Request: " + request + '|' + " Event type: " + eventType);
		}
		else{
			System.out.println("Failed to connect server!");
		}

	}

	public void managerRemoveEvent(String managerID, RequestType request, String eventID, EventType eventType ) {

		WebInterface webInterface = init(managerID);

		if (webInterface != null) {
			MessageModel messageModel = new MessageModel();
			messageModel.setClientID(managerID);
			messageModel.setRequestType(request);
			messageModel.setEventID(eventID);
			messageModel.setEventType(eventType);
			System.out.println(webInterface.processRequest(messageModel));
			LogUtils.writeToFile("Client_ID_" + managerID + ".txt", " Request type: " + request + '|' + " Event type: " + eventType + '|' + "Event id: " + eventID);
		}
		else {
			System.out.println("Failed to connect server!");
		}

	}

	public void managerListEventAvailability(String managerID, RequestType request, EventType eventType) {

		WebInterface webInterface = init(managerID);

		if (webInterface != null) {
			MessageModel messageModel = new MessageModel();
			//messageModel.setClientID(managerID);
			messageModel.setRequestType(request);
			messageModel.setEventType(eventType);
			System.out.println(webInterface.processRequest(messageModel));
			LogUtils.writeToFile("Client_ID_" + managerID + ".txt", " Request type: " + request + '|' + " Event type: " + eventType);
		}
		else {
			System.out.println("Failed to connect server!");
		}

	}

	public void managerAddEvent(String managerID, RequestType request, String eventID, EventType eventType, int bookingCapacity ) {

		if (!managerID.substring(0,3).equals(eventID.substring(0,3))){
			System.out.println("Input Mismatches, Manager cannot add events in FOREIGN cities.");
			return;
		}

		WebInterface webInterface = init(managerID);

		if (webInterface != null) {
			MessageModel messageModel = new MessageModel();
			messageModel.setClientID(managerID);
			messageModel.setRequestType(request);
			messageModel.setEventID(eventID);
			messageModel.setEventType(eventType);
			messageModel.setBookingCapacity(bookingCapacity);
			System.out.println(webInterface.processRequest(messageModel));
			LogUtils.writeToFile("Client_ID_" + managerID + ".txt", " Request type: " + request + '|' + " Event ID: " + eventID + '|' + " Event type: " + eventType + '|' + " Booking Capacity: " + bookingCapacity);
		}
		else {
			System.out.println("Failed to connect server!");
		}

	}

	public void customerCancelEvent(String customerID, RequestType request, String eventID, EventType eventType) {

		WebInterface webInterface = init(customerID);

		if (webInterface != null) {
			MessageModel messageModel = new MessageModel();
			messageModel.setClientID(customerID);
			messageModel.setRequestType(request);
			messageModel.setEventID(eventID);
			messageModel.setEventType(eventType);
			System.out.println(webInterface.processRequest(messageModel));
			LogUtils.writeToFile("Client_ID_" + customerID + ".txt", " Event ID: " + eventID + " Request type: " + request + '|' + " Event type: " + eventType);
		}
		else {
			System.out.println("Failed to connect server!");
		}
	}

	public void customerGetBookingSchedule(String customerID, RequestType request) {

		WebInterface webInterface = init(customerID);

		if (webInterface != null) {
			MessageModel messageModel = new MessageModel();
			messageModel.setClientID(customerID);
			messageModel.setRequestType(request);
			System.out.println(webInterface.processRequest(messageModel));
			LogUtils.writeToFile("Client_ID_" + customerID + ".txt", " Request type: " + request);
		}
		else {
			System.out.println("Failed to connect server!");
		}

	}

	public void customerBookEvent(String customerID, RequestType request, String eventID, EventType eventType) {

		WebInterface webInterface = init(customerID);

		if (webInterface != null) {
			MessageModel messageModel = new MessageModel();
			messageModel.setClientID(customerID);
			messageModel.setRequestType(request);
			messageModel.setEventID(eventID);
			messageModel.setEventType(eventType);
			System.out.println(webInterface.processRequest(messageModel));
			LogUtils.writeToFile("Client_ID_" + customerID + ".txt", " Request type: " + request + " | Event ID: " + eventID + " | Event type: " + eventType);
		}
		else {
			System.out.println("Failed to connect server!");
		}
	}
	public void customerSwapEvent(String customerID, String newEventID, EventType newEventType, String oldEventID, EventType oldEventType, RequestType requestType) {

		WebInterface webInterface = init(customerID);

		if (webInterface != null) {

			MessageModel messageModel = new MessageModel();
			messageModel.setClientID(customerID);
			messageModel.setEventID(oldEventID);
			messageModel.setEventType(oldEventType);
			messageModel.setNewEventID(newEventID);
			messageModel.setNewEventType(newEventType);
			messageModel.setRequestType(requestType);
			System.out.println(webInterface.processRequest(messageModel));
			LogUtils.writeToFile("Client_ID_" + customerID + ".txt", " Request type: " + requestType + " | Old Event ID: " + oldEventID + " | Old Event type: " + oldEventType + " | New Event ID: " + newEventID + " | New Event type: " + newEventType);
		}
		else {
			System.out.println("Failed to connect server!");
		}
	}

	public void managerSwapEvent(String managerID, String customerID, String newEventID, EventType newEventType, String oldEventID, EventType oldEventType, RequestType requestType) {
		WebInterface webInterface = init(customerID);

		if (webInterface != null) {
			MessageModel messageModel = new MessageModel();
			messageModel.setClientID(customerID);
			messageModel.setEventID(oldEventID);
			messageModel.setEventType(oldEventType);
			messageModel.setNewEventID(newEventID);
			messageModel.setNewEventType(newEventType);
			messageModel.setRequestType(requestType);
			System.out.println(webInterface.processRequest(messageModel));
			LogUtils.writeToFile("Manager_ID" + customerID + ".txt", " Request type: " + requestType + " | Old Event ID: " + oldEventID + " | Old Event type: " + oldEventType + " | New Event ID: " + newEventID + " | New Event type: " + newEventType);
		}
		else {
			System.out.println("Failed to connect server!");
		}

	}

	public void makeRmiRequestCustomer(String customerID, RequestType request) {

		System.out.println("you are not allowed to perform this operation");
		LogUtils.writeToFile("Client_ID_"+customerID+".txt", " Request Type: "+request);

	}

	private WebInterface init(String customerID){
		WebInterface webInterface = null;

		URL addURL = null;
		QName addQName = null;
		switch (customerID.substring(0,3)){
			case "TOR" :
				//webInterface= ResolveServer.getInstance().revokeServer(TOR_URL, "TorImplementationService");

				try {
					addURL = new URL(TOR_URL);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				addQName = new QName("http://impl.service.server/", "TorImplementationService");
				break;
			case "MTL" :
				//webInterface= ResolveServer.getInstance().revokeServer(MTL_URL, "MtlImplementationService");

				try {
					addURL = new URL(MTL_URL);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				addQName = new QName("http://impl.service.server/", "MtlImplementationService");
				break;
			case "OTW" :
				try {
					addURL = new URL(OTW_URL);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				addQName = new QName("http://impl.service.server/", "OtwImplementationService");
				break;
		}

		Service addition = Service.create(addURL, addQName);
		return addition.getPort(WebInterface.class);
	}


}