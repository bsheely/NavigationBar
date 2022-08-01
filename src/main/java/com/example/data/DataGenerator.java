package com.example.data;

import com.vaadin.exampledata.ChanceIntegerType;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
public class DataGenerator implements CommandLineRunner {
    private List<Document> documents;

    public List<Document> getDocuments() {
        return documents;
    }

    @Override
    public void run(String... args) throws Exception {
        var generator = new ExampleDataGenerator<>(Document.class, LocalDateTime.now());
        generator.setData(Document::setImageData, DataType.BOOK_IMAGE_URL);
        generator.setData(Document::setTitle, DataType.BOOK_TITLE);
        generator.setData(Document::setAuthor, DataType.FULL_NAME);
        generator.setData(Document::setGenre, DataType.BOOK_GENRE);
        generator.setData(Document::setPrice, new ChanceIntegerType("integer", "{min: 10, max: 24}"));
        generator.setData(Document::setPublishDate, DataType.DATE_LAST_10_YEARS);
        generator.setData(Document::setPages, new ChanceIntegerType("integer", "{min: 70, max: 1000}"));
        generator.setData(Document::setItemCode, DataType.IBAN);
        documents = generator.create(50, new Random().nextInt());
    }
}
