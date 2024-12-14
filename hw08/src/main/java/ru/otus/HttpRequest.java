package ru.otus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private String method;

    private String uri;

    private String version;

    private Map<String, String> parameters;

    private Map<String, String> headers;

    private String body;

    public HttpRequest() {
        parameters = new HashMap<>();
        headers = new HashMap<>();
    }

    public static HttpRequest parse(InputStream inputStream) throws IOException {
        HttpRequest request = new HttpRequest();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Чтение метода
        int read;

        for (read = inputStream.read(); read != ' ' && read != -1; read = inputStream.read()) {
            baos.write(read);
        }
        request.setMethod(baos.toString());
        baos.reset();

        // Чтение URI
        for (read = inputStream.read(); read != ' ' && read != '?' && read != -1; read = inputStream.read()) {
            baos.write(read);
        }
        request.setUri(baos.toString());
        baos.reset();

        // Чтение параметров при наличии
        if (read == '?') {
            Map<String, String> parameters = new HashMap<>();
            String paramKey;

            do {
                for (read = inputStream.read(); read != '=' && read != -1; read = inputStream.read()) {
                    baos.write(read);
                }
                paramKey = baos.toString();
                baos.reset();

                for (read = inputStream.read(); read != '&' && read != ' ' && read != -1; read = inputStream.read()) {
                    baos.write(read);
                }
                parameters.put(paramKey, baos.toString());
                baos.reset();
            } while (read != -1 && read != ' ');
            baos.reset();

            request.setParameters(parameters);
        }

        // Чтение версии
        for (read = inputStream.read(); read != '\n' && read != -1; read = inputStream.read()) {
            if (read == '\r') {
                continue;
            }
            baos.write(read);
        }
        request.setVersion(baos.toString());
        baos.reset();

        Map<String, String> headers = request.getHeaders();

        // Чтение заголовков
        while (true) {
            baos.reset(); // Очищаем байтовый поток
            for (read = inputStream.read(); read != '\n' && read != -1; read = inputStream.read()) {
                if (read == '\r') {
                    continue;
                }
                baos.write(read);
            }

            String headerLine = baos.toString("UTF-8").trim();
            if (headerLine.isEmpty()) {
                break;
            }

            String[] headerParts = headerLine.split(": ", 2);
            if (headerParts.length == 2) {
                headers.put(headerParts[0], headerParts[1]);
            }
        }

        // Чтение тела запроса
        if (headers.containsKey("Content-Length")) {
            int contentLength = Integer.parseInt(headers.get("Content-Length"));

            byte[] bodyBuffer = new byte[contentLength];
            int bytesRead = inputStream.read(bodyBuffer);

            if (bytesRead != -1) {
                request.setBody(new String(bodyBuffer, "UTF-8"));
            }
        }

        return request;
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

    public void setMethod(String method) {
        this.method = method;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method='" + method + '\'' +
                ", uri='" + uri + '\'' +
                ", headers=" + headers +
                ", parameters=" + parameters +
                ", body='" + body + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
