package LibraryManagement.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class BooksAPI {
    private static class Book {
        private String title;
        private String author;
        private String publisher;
        private String description;
        private ImageLinks imageLinks;

        public Book() {
            this.title = "";
            this.author = "";
            this.publisher = "";
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title.replaceAll("^\"|\"$", "");
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author.replaceAll("^\"|\"$", "");
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher.replaceAll("^\"|\"$", "");
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description.replaceAll("^\"|\"$", "");
        }

        public void setImageLinks(ImageLinks imageLinks) {
            this.imageLinks = imageLinks;
        }

        public static class ImageLinks {
            private String smallThumbnail;
            private String thumbnail;

            public String getSmallThumbnail() {
                return smallThumbnail;
            }

            public void setSmallThumbnail(String smallThumbnail) {
                this.smallThumbnail = smallThumbnail.replaceAll("^\"|\"$", "");
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail.replaceAll("^\"|\"$", "");
            }
        }
    }

    private Book getBookFromJson(String json) {
        try {
            Book result = new Book();
            JsonNode node = new ObjectMapper().readTree(json);
            if (node.has("items")) {
                JsonNode items = node.get("items");
                JsonNode volumeInfo = items.get(0).get("volumeInfo");
                result.setTitle(String.valueOf(volumeInfo.get("title")));
                result.setAuthor(String.valueOf(volumeInfo.get("authors").get(0)));
                result.setPublisher(String.valueOf(volumeInfo.get("publisher").get(0).get("identifier")));
                result.setDescription(String.valueOf(volumeInfo.get("description")));
                Book.ImageLinks imageLinks = new Book.ImageLinks();
                JsonNode imageLinksNode = volumeInfo.get("imageLinks");

                imageLinks.setSmallThumbnail(imageLinksNode.get("smallThumbnail").asText());
                imageLinks.setThumbnail(imageLinksNode.get("thumbnail").asText());

                result.setImageLinks(imageLinks);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getHttpResponse(String url) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            System.out.println("Getting " + url);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();
            if (status != 200) {
                throw new RuntimeException("Failed with HTTP error code : " + status);
            }
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Book ggBooksAPI(String isbn) {
        String api = "https://www.googleapis.com/books/v1/volumes?q=isbn%3D";
        return getBookFromJson(getHttpResponse(api + isbn));

    }
}
