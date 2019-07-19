package server.service;

import server.database.MessageModel;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)

public interface WebInterface{
    String processRequest(MessageModel data);
}
