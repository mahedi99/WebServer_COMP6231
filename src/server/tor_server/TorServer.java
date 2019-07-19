package server.tor_server;

import server.service.impl.TorImplementation;
import utlis.Utils;
import server.database.EventType;
import server.database.RequestType;

import javax.xml.ws.Endpoint;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.lang.*;

public class TorServer {

    private static TorImplementation implementation;

    public static void main(String[] args) {

        //Establishing connection between servers
        new Thread(() -> receive()).start();

        try{
            System.out.println("Tor Server Started...");
            implementation = new TorImplementation();
            Endpoint.publish("http://localhost:" + Utils.TOR_SERVER_PORT + "/tor", implementation);
        }
        catch (Exception re) {
            System.out.println("Exception in TorServer.main: " + re);
        }
    }

    private static void receive() {
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket(Utils.TOR_SERVER_PORT);
            byte[] buffer = new byte[1000];
            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);

                String[] data = new String(request.getData(), request.getOffset(), request.getLength()).split("\\|");
                String response = "false";
                TorDBController controller = TorDBController.getInstance();

                switch (RequestType.valueOf(data[0].trim())) {
                    case ADD_EVENT:
                        response = controller.addEvent(data[1], EventType.valueOf(data[2]),
                                Integer.parseInt(data[3].trim()));
                        break;
                    // case REMOVE_EVENT:
                    // response = controller.removeEvent(model.getEventID(), model.getEventType());
                    // break;
                    case LIST_EVENT_AVAILABILITY:
                        response = controller.listEventAvailabilityForOthers(EventType.valueOf(data[1].trim()));
                        break;
                    case BOOK_EVENT:
                        response = controller.bookEvent(data[1], data[2], EventType.valueOf(data[3].trim()));
                        break;
                    case GET_BOOKING_SCHEDULE:
                        response = controller.getBookingScheduleForOthers(data[1].trim());
                        break;
                    case CANCEL_EVENT:
                        response = controller.cancelEvent(data[1].trim(), data[2].trim(),
                                EventType.valueOf(data[3].trim()));
                        break;
                    case GET_TOTAL_EVENTS:
                        response = controller.getEventsForOneMonth(data[1].trim(), data[2].trim());
                        break;
                    case CHECK_IS_REGISTERED:
                        response = controller.getIsTheUserRegistered(data[1].trim(), data[2].trim(), EventType.valueOf(data[3].trim()));
                        break;
                    case CHECK_CAPACITY:
                        response = controller.checkCapacityForTheEvent(data[1].trim(), data[2].trim(), EventType.valueOf(data[3].trim()));
                        break;
                }

                // replay back after processing the request
                DatagramPacket reply = new DatagramPacket(response.getBytes(), response.length(), request.getAddress(),
                        request.getPort());
                aSocket.send(reply);
            }

        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (aSocket != null)
                aSocket.close();
        }
    }
    public static String sendMsg(int serverPort, String UDPMsg){
        String response = "false";
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket();
            InetAddress aHost = InetAddress.getByName("localhost");
            DatagramPacket request = new DatagramPacket(UDPMsg.getBytes(), UDPMsg.length(), aHost, serverPort);
            aSocket.send(request);
            System.out.println("Request message sent from the client to server with port number " + serverPort + " is: "
                    + new String(request.getData()));
            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);

            aSocket.receive(reply);
            System.out.println("Reply received from the server with port number " + serverPort + " is : " + new String(reply.getData()));
            response = new String(reply.getData()).trim();
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (aSocket != null)
                aSocket.close();
        }
        return response;
    }
}
