package com.example.view;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.view.NavigationBar.ButtonType.*;

class NavigationBar extends HorizontalLayout {
    enum ButtonType {
        PREVIOUS {public String toString() { return "Prev"; }},
        NEXT {public String toString() { return "Next"; }}
    }
    private int count;
    private int current = 1;
    private int start = 1;
    private int stop = 10;
    private final DocumentViewer documentViewer;
    private List<Button> buttons;
    private final Button previousNavigation;
    private final Button nextNavigation;
    private final HorizontalLayout layout;

    public NavigationBar(DocumentViewer documentViewer) {
        this.documentViewer = documentViewer;
        setWidth(50, Unit.EM);
        layout = new HorizontalLayout();
        layout.setAlignItems(Alignment.CENTER);
        Icon left = new Icon(VaadinIcon.CHEVRON_LEFT);
        left.getElement().getStyle().set("padding-right", "0");
        previousNavigation = new Button(PREVIOUS.toString(), left, this::handleButtonClick);
        previousNavigation.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE,ButtonVariant.LUMO_SMALL);
        previousNavigation.setVisible(false);
        Icon right = new Icon(VaadinIcon.CHEVRON_RIGHT);
        right.getElement().getStyle().set("padding-left", "0");
        nextNavigation = new Button(NEXT.toString(), right, this::handleButtonClick);
        nextNavigation.setIconAfterText(true);
        nextNavigation.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE,ButtonVariant.LUMO_SMALL);
        add(layout);
    }

    public void setDocumentCount(int count) {
        this.count = count;
        buttons = new ArrayList<>();
        layout.removeAll();
        layout.add(previousNavigation);
        previousNavigation.setVisible(false);
        for (int i = 1; i <= count; ++i) {
            Button button = new Button(String.valueOf(i), this::handleButtonClick);
            button.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE,ButtonVariant.LUMO_SMALL);
            layout.add(button);
            buttons.add(button);
            if (i == 1)
                button.getStyle().set("font-weight","bold");
            if (i > stop)
                button.setVisible(false);
        }
        layout.add(nextNavigation);
        if (count <= stop)
            nextNavigation.setVisible(false);
        documentViewer.displayDocumentAtIndex(0);
        setVisible(count > 1);
    }

    private void handleButtonClick(ClickEvent<Button> e) {
        String caption = e.getSource().getText();
        Button previouslySelected = buttons.get(current - 1);
        previouslySelected.getStyle().set("font-weight","normal");
        if (caption.contains(PREVIOUS.toString()) && start > 1) {
            start -= 10;
            stop -= 10;
            current = start;
            processNavigationClick();
        }
        else if (caption.contains(NEXT.toString()) && stop < count) {
            start += 10;
            stop += 10;
            current = start;
            processNavigationClick();
        }
        else if (!caption.contains(PREVIOUS.toString()) && !caption.contains(NEXT.toString())) {
            current = Integer.parseInt(caption);
            Button selected = buttons.get(current - 1);
            selected.getStyle().set("font-weight","bold");
        }
        previousNavigation.setVisible(start != 1);
        nextNavigation.setVisible(count > stop);
        documentViewer.displayDocumentAtIndex(current - 1);
    }

    private void processNavigationClick() {
        for (int i = 1; i <= count; ++i) {
            Button button = buttons.get(i - 1);
            button.setVisible(i >= start && i <= stop);
            if (i == current)
                button.getStyle().set("font-weight","bold");
        }
    }
}
