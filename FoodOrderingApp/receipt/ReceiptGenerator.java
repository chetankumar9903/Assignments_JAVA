package com.aurionpro.receipt;

import com.aurionpro.order.Order;

public interface ReceiptGenerator {
    void generateReceipt(Order order);
}
