package com.ftemotheos.tasks.secret_task1;

import java.util.ListResourceBundle;

public class MessageResources_ua extends ListResourceBundle {
    protected Object[][] getContents() {
        return new Object[][] {
                {"morning", "Доброго ранку"},
                {"afternoon", "Доброго дня"},
                {"evening", "Доброго вечора"},
                {"night", "Доброї ночі"}
        };
    }
}
