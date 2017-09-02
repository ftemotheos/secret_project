package com.ftemotheos.tasks.secret_task1;

import java.util.ListResourceBundle;

public class MessageResources_ru extends ListResourceBundle {
    protected Object[][] getContents() {
        return new Object[][] {
                {"morning", "Доброе утро"},
                {"afternoon", "Добрый день"},
                {"evening", "Добрый вечер"},
                {"night", "Доброй ночи"}
        };
    }
}
