package ru.otus;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        Properties properties = readProperties();

        int port = Integer.parseInt(properties.getProperty("server.port"));
        int threadPoolSize = Integer.parseInt(properties.getProperty("server.threadPoolSize"));

        new HttpServer(port, threadPoolSize).start();
    }

    private static Properties readProperties() {

        Properties properties = new Properties();
        try (InputStream input = Main.class.getResourceAsStream("/server.properties")) {
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return properties;
    }

}
