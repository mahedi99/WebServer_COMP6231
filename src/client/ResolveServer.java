package client;

import server.service.WebInterface;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

public class ResolveServer {

    public static ResolveServer instance;

    private ResolveServer(){
    }

    public static ResolveServer getInstance() {
        if (instance == null){
            instance = new ResolveServer();
        }
        return instance;
    }

    public WebInterface revokeServer(String url, String localPort) {
        URL addURL = null;
        try {
            addURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        QName addQName = new QName("http://impl.service.server/", localPort);

        Service addition = Service.create(addURL, addQName);
        return addition.getPort(WebInterface.class);
    }
}
