package ru.otus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private final String method;
    private final String uri;
    private final Map<String, String> headers;
    private final Map<String, String> parameters;
    private String body;

    public HttpRequest(String method, String uri, Map<String, String> headers, Map<String, String> parameters, String body) {
        this.method = method;
        this.uri = uri;
        this.headers = headers;
        this.parameters = parameters;
        this.body = body;
    }


    public static HttpRequest parse(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String requestLine = reader.readLine();

        if (requestLine == null) {
            throw new IOException("Empty request");
        }

        String[] requestParts = requestLine.split(" ");
        String method = requestParts[0];
        String uri = requestParts[1];

        Map<String, String> parameters = new HashMap<>();
        if (uri.contains("?")) {
            String[] uriParts = uri.split("\\?");
            uri = uriParts[0];

            String[] paramParts = uriParts[1].split("&");
            for (String param : paramParts) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    parameters.put(keyValue[0], keyValue[1]);
                }
            }
        }

        Map<String, String> headers = new HashMap<>();
        String headerLine;
        while ((headerLine = reader.readLine()) != null && !headerLine.isEmpty()) {
            String[] headerParts = headerLine.split(": ", 2);
            if (headerParts.length == 2) {
                headers.put(headerParts[0], headerParts[1]);
            }
        }

        // Считываем тело запроса
        StringBuilder bodyBuilder = new StringBuilder();
        // Если есть заголовок Content-Length, считываем тело
        if (headers.containsKey("Content-Length")) {
            int contentLength = Integer.parseInt(headers.get("Content-Length"));
            char[] bodyBuffer = new char[contentLength];
            reader.read(bodyBuffer, 0, contentLength);
            bodyBuilder.append(bodyBuffer);
        }

        return new HttpRequest(method, uri, headers, parameters, bodyBuilder.toString());
    }


    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method='" + method + '\'' +
                ", uri='" + uri + '\'' +
                ", headers=" + headers +
                ", parameters=" + parameters +
                ", body='" + body + '\'' +
                '}';
    }
}
