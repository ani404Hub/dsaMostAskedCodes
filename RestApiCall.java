package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class RestApiCall {
    public static void main(String[] args) throws IOException, InterruptedException {

        int id = 1;
        String urlRP = "https://jsonplaceholder.typicode.com/posts?userId=" + id; //Request Param input
        String urlPP =  "https://jsonplaceholder.typicode.com/posts/" +id; //Path Param input

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(urlRP)).GET().build();
        //HttpRequest request1 = HttpRequest.newBuilder().uri(URI.create(urlRP)).headers("username", "Param1","Param2").POST(new bodyPublisher("abc")).build();
        //HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); //HttpResponse convert to string
        String arr = response.body();

            if(response.statusCode() == 200){
                System.out.println("Response Body" + response.body());
            }
            else {
                System.out.println("Error Response " + response.statusCode());
            }
    }
}