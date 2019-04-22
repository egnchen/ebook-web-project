package com.eyek.ebook.controller.dto;

import javax.validation.constraints.Positive;

public class OrderItemDto {
    @Positive
    private int bookId;

    @Positive
    private int amount = 1;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
