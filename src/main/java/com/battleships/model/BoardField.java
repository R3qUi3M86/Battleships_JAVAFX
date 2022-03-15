package com.battleships.model;

public class BoardField {
    private FieldContent fieldContent = FieldContent.WATER;

    public FieldContent getFieldContent() {
        return fieldContent;
    }

    public void setFieldContent(FieldContent fieldContent) {
        this.fieldContent = fieldContent;
    }
}
