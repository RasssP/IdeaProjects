package advisor;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static advisor.Main.accessServer;

public class Handler {

    private boolean access = false;
    String redirectUri;
    public static final String clientID = "afda4923dcc0462ca4a068838b3400e0";
    public static final String clientSecret = "5b87bdb13f2247478ed5296fc70019c2";
    public static final String uriPort = "http://localhost:8080";
    // "https://accounts.spotify.com"
    // variable to hold String query
    static String query;

    void auth() {

        redirectUri = String.format(accessServer +
                "/authorize?client_id=%s" +
                "&redirect_uri=%s&response_type=code", clientID, uriPort);

        // Set up HttpServer
        try {
            HttpServer server = HttpServer.create();
            server.bind(new InetSocketAddress(8080),1);
            System.out.println("use this link to request the access code:");
            server.start();
            System.out.println(redirectUri);
            Thread.sleep(2000);
            server.createContext("/", new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) throws IOException {
                    System.out.println("waiting for code...");
                    query = exchange.getRequestURI().getQuery();

                    query = query.replaceAll("error=|code=", "");
                    String responseBody;
                    if ((!"access_denied".equals(query)) && query != "") {
                        System.out.println("code received");
                        responseBody = "Got the code. Return back to your program.";
                        exchange.sendResponseHeaders(200, responseBody.getBytes().length);
                        exchange.getResponseBody().write(responseBody.getBytes());
                        exchange.getResponseBody().close();
                        server.stop(1);
                    }
                    // Fix else code
                    else {
                        responseBody = "Authorization code not found. Try again.";
                        // "Authorization code not found. Try again.";
                        // "Not found authorization code. Try again."
                        exchange.sendResponseHeaders(200, responseBody.getBytes().length);
                        exchange.getResponseBody().write(responseBody.getBytes());
                        exchange.getResponseBody().close();
                        return;
                    }

                    System.out.println("making http request for access_token...");
                    System.out.println("response:");
                    getToken();
                    access = true;
                    System.out.println("---SUCCESS---");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void getToken() {

        HttpClient client = HttpClient.newBuilder().build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(accessServer + "/api/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(String.format("grant_type=authorization_code" +
                                "&code=%s&redirect_uri=%s&client_id=%s&client_secret=%s"
                        ,query, uriPort,clientID, clientSecret)))
                .build();
        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    boolean getAccess() {

        return access;
    }
}
