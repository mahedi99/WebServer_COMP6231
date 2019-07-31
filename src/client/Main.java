package client;

import server.database.EventType;
import server.database.RequestType;

import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);

    // For RMI
    /*
     * public static void main(String[] args) { while(true) {
     * System.out.println("Enter your id: "); String customerClientID = sc.next();
     * if (customerClientID.substring(3, 4).contains("M")) { Manager emc1
     * = new Manager(); emc1.eventManagerClientRequest(customerClientID);
     * } else if (customerClientID.substring(3, 4).contains("C")) { Customer
     * cc1 = new Customer(); cc1.customerClientRequest(customerClientID); }
     * System.out.println("\n"); } }
     */

//	// For Corba
//	public static void main(String[] args) {
//		try {
//			while (true) {
//				System.out.println("Enter your id: ");
//				String customerClientID = sc.next();
//				if (customerClientID.substring(3, 4).contains("M")) {
//					EventManagerClientCORBA emc1 = new EventManagerClientCORBA();
//					emc1.eventManagerClientRequest(customerClientID);
//				} else if (customerClientID.substring(3, 4).contains("C")) {
//					CustomerClientCORBA cc1 = new CustomerClientCORBA();
//					cc1.customerClientRequest(customerClientID);
//				}
//				System.out.println("\n");
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

    // For WebServices
    public static void main(String[] args) {
        while (true) {
            System.out.println("Enter your id: ");
            String customerClientID = sc.next();
            if (customerClientID.substring(0,2).contains("MT")){ //Multithreading

                applyMultiThreading();
            }
            else if (customerClientID.substring(3, 4).contains("M")) {
                Manager emc1 = new Manager();
                emc1.eventManagerClientRequest(customerClientID);
            } else if (customerClientID.substring(3, 4).contains("C")) {
                Customer cc1 = new Customer();
                cc1.customerClientRequest(customerClientID);
            }
            System.out.println("\n");
        }
    }

    private static void applyMultiThreading() {


        ClientServerController emccsc = ClientServerController.getInstance();

        String managerID = "MTLM1";
        String eventID = "MTLE080619";
        EventType eventType = EventType.CONFERENCE;
        int bookingCapacity = 5;

        //Manager
        emccsc.managerAddEvent(managerID, RequestType.ADD_EVENT, eventID, eventType, bookingCapacity);//valid request

        String clientID = "MTLC1";

        //Client
        emccsc.customerBookEvent(clientID, RequestType.BOOK_EVENT, eventID, eventType);//valid request

        //Client
        emccsc.customerBookEvent(clientID, RequestType.BOOK_EVENT, "MTLE080101", eventType);//invalid request


        //Manager
        emccsc.managerListEventAvailability(managerID, RequestType.LIST_EVENT_AVAILABILITY, eventType);
    }
}
