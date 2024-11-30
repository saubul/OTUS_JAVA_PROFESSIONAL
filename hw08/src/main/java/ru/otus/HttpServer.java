package ru.otus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private final int port;
    private final ExecutorService executorService;
    private volatile boolean running = true;

    public HttpServer(int port, int threadPoolSize) {
        this.port = port;
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port: " + port);
            while (running) {
                Socket clientSocket = serverSocket.accept();
                if (running) {
                    executorService.submit(() -> handleClientRequest(clientSocket));
                } else {
                    executorService.shutdown();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClientRequest(Socket clientSocket) {
        try (InputStream inputStream = clientSocket.getInputStream(); OutputStream outputStream = clientSocket.getOutputStream()) {

            HttpRequest request = HttpRequest.parse(inputStream);
            System.out.println("Полученный запрос: " + request);
            String response;

            if ("GET".equalsIgnoreCase(request.getMethod()) && "/shutdown".equals(request.getUri())) {
                response = "HTTP/1.1 200 OK\r\n\r\nServer shutting down";
                System.out.println("Отправленный ответ: " + response);
                outputStream.write(response.getBytes());
                outputStream.flush();
                running = false;
                return;
            }

            response = "HTTP/1.1 200 OK\r\n\r\nRequest processed successfully";
            System.out.println("Отправленный ответ: " + response);
            outputStream.write(response.getBytes());
            outputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
            try (OutputStream outputStream = clientSocket.getOutputStream()) {
                String response = "HTTP/1.1 500 Internal Server Error\r\n\r\n";
                outputStream.write(response.getBytes());
                outputStream.flush();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
