package com.ftemotheos.tasks.secret_task1;

import java.util.ListResourceBundle;

public class MessageResources extends ListResourceBundle {
    protected Object[][] getContents() {
        return new Object[][] {
                {"morning", "Good morning"},
                {"afternoon", "Good afternoon"},
                {"evening", "Good evening"},
                {"night", "Good night"}
        };
    }
}
