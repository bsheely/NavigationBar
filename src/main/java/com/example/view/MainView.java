package com.example.view;

import com.example.pojo.Document;
import com.vaadin.exampledata.ChanceIntegerType;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Random;

@Route("")
public class MainView extends VerticalLayout implements DocumentViewer {
    private final Image image;
    private final TextField title;
    private final TextField author;
    private final TextField genre;
    private final TextField price;
    private final TextField pages;
    private final TextField published;
    private final TextField itemCode;
    private final List<Document> documents;

    public MainView() {
        setHeightFull();
        image = new Image();
        image.setHeight("500px");
        title = new TextField("Title");
        title.setReadOnly(true);
        author = new TextField("Author");
        author.setReadOnly(true);
        genre = new TextField("Genre");
        genre.setReadOnly(true);
        price = new TextField("Price");
        price.setReadOnly(true);
        pages = new TextField("Pages");
        pages.setReadOnly(true);
        published = new TextField("Date published");
        published.setReadOnly(true);
        itemCode = new TextField("Item code");
        itemCode.setReadOnly(true);
        FormLayout formLayout = new FormLayout();
        formLayout.setWidth("70%");
        formLayout.add(image, title, author, genre, price, pages, published, itemCode);
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

        // Create the navigation bar
        NavigationBar navigationBar = new NavigationBar(this);
        navigationBar.setJustifyContentMode(JustifyContentMode.CENTER);
        navigationBar.setDocumentCount(documents.size());

        setJustifyContentMode(JustifyContentMode.CENTER);
        setHorizontalComponentAlignment(Alignment.CENTER, formLayout, navigationBar);
        add(formLayout, navigationBar);
    }

    @Override
    public void displayDocumentAtIndex(int index) {
        Document selected = documents.get(index);
        image.setSrc(selected.getImageData());
        title.setValue(selected.getTitle());
        author.setValue(selected.getAuthor());
        genre.setValue(selected.getGenre());
        price.setValue("$" + selected.getPrice());
        pages.setValue(String.valueOf(selected.getPages()));
        published.setValue(selected.getPublishDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        itemCode.setValue(selected.getItemCode());
    }
}
