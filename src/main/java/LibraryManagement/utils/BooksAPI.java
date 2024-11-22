package LibraryManagement.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class BooksAPI {

    public ArrayList<Book> getBooksFromJson(String json) {
        ArrayList<Book> bookList = new ArrayList<>();
        try {
            JsonNode root = new ObjectMapper().readTree(json);
            if (root.has("items")) {
                JsonNode items = root.get("items");
                for (JsonNode item : items) {
                    JsonNode volumeInfo = item.get("volumeInfo");
                    Book book = new Book();

                    if (volumeInfo.has("title")) {
                        book.setTitle(volumeInfo.get("title").asText());
                    }
                    if (volumeInfo.has("authors") && volumeInfo.get("authors").isArray()) {
                        book.setAuthor(volumeInfo.get("authors").get(0).asText());
                    }

                    if (volumeInfo.has("industryIdentifiers") && volumeInfo.get("industryIdentifiers").isArray()) {
                        book.setIsbn(volumeInfo.get("industryIdentifiers").get(0).get("identifier").asText());
                    }
                    if (volumeInfo.has("publisher")) {
                        book.setPublisher(volumeInfo.get("publisher").asText());
                    }
                    if (volumeInfo.has("description")) {
                        book.setDescription(volumeInfo.get("description").asText());
                    }

                    if (volumeInfo.has("imageLinks") && volumeInfo.get("imageLinks").has("thumbnail")) {
                        String thumbnailUrl = volumeInfo.get("imageLinks").get("thumbnail").asText();
                        book.setImageLinks(thumbnailUrl);
                    }

                    bookList.add(book);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public String getHttpResponse(String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException("HTTP Error: " + response.statusCode());
            }
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
