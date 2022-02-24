package advisor.server;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

import static advisor.util.GlobalVariables.*;


/**
 * A singleton class implement a Http Server where the user will be redirected after providing access to the application
 * @author Beauclair Dongmo Ngnintedem
 */
public class Server {

    private static final Server serverInstance = new Server();

    private String code;
    private HttpServer server;

    private Server() {
    }

    public static Server getServerInstance() {

        if (serverInstance == null) {
            return new Server();
        }

        return serverInstance;
    }

    public void start() {
        try {
            this.server = HttpServer.create();
            this.server.bind(new InetSocketAddress(PORT), IP);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createContext() {
        this.server.createContext("/", (exchange) -> {
            String msg = AUTHORIZATION_CODE_NOT_FOUND_TRY_AGAIN_TEXT;
            String query = exchange.getRequestURI().getQuery();
            if (query != null && query.matches("^code=.+")) {
                msg = GOT_THE_CODE_RETURN_BACK_TEXT;
                this.code = query.replaceFirst("^code=", "");
            }
            exchange.sendResponseHeaders(200, msg.length());
            exchange.getResponseBody().write(msg.getBytes());
            exchange.close();
        });
    }

    public void stop() {
        this.server.stop(MAX_DELAY);
    }

    public String getCode() {
        return this.code;
    }
}
