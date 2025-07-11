package com.aurionpro.receipt;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import com.aurionpro.order.Order;
import com.aurionpro.order.OrderedItem;
import com.aurionpro.payment.PaymentDetails;

public class TextReceiptGenerator implements ReceiptGenerator {

    private static final String FILE_NAME = "order_invoice.txt";

    @Override
    public void generateReceipt(Order order) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) { // true for append
            writer.write("\n================== ORDER INVOICE ==================\n");

            // Customer Info
            writer.write("Customer: " + order.getCustomer().getName() + "\n");
            writer.write("Phone   : " + order.getCustomer().getPhone() + "\n");
            writer.write("Address : " + order.getCustomer().getAddress() + "\n");
            writer.write("Email   : " + order.getCustomer().getEmail() + "\n\n");

            // Order Info
            writer.write("Order ID    : " + order.getOrderId() + "\n");
            writer.write("Order Time  : " + order.getFormattedOrderTime() + "\n");
            writer.write("Delivery Time: " + order.getFormattedDeliveryTime() + "\n");
            writer.write("Status      : " + order.getStatus() + "\n");
            writer.write("Instructions: " + (order.getSpecialInstructions() == null ? "-" : order.getSpecialInstructions()) + "\n\n");

            //  Item Details
            writer.write("Ordered Items:\n");
            writer.write(String.format("%-20s %-10s %-10s %-10s\n", "Item", "Qty", "Unit ₹", "Total ₹"));
            for (OrderedItem item : order.getItems()) {
                double itemTotal = item.getFood().getPrice() * item.getQuantity();
                writer.write(String.format("%-20s %-10d ₹%-9.2f ₹%-9.2f\n",
                        item.getFood().getName(),
                        item.getQuantity(),
                        item.getFood().getPrice(),
                        itemTotal));
            }

            // Payment Info
            writer.write("\n Payment Info:\n");
            PaymentDetails payment = order.getPaymentDetails();
            if (payment != null) {
                writer.write("Mode     : " + payment.getMode() + (payment.getMode().name().equals("CASH") ? " (Due)" : "") + "\n");
                writer.write("Ref ID   : " + payment.getPaymentReferenceId() + "\n");
                writer.write("Time     : " + payment.getPaymentTime().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")) + "\n");
                writer.write(String.format("Subtotal : ₹%.2f\n", payment.getAmount()));
                writer.write(String.format("Discount : ₹%.2f\n", payment.getDiscount()));
                writer.write(String.format("Delivery Fee : ₹%.2f\n", PaymentDetails.getDeliveryFee()));
                writer.write(String.format("Total    : ₹%.2f\n", payment.getFinalAmount()));
            } else {
                writer.write("Payment:  Not Paid\n");
            }

            // Delivery Partner
            writer.write("\n Delivered By: " +
                    (order.getAssignedAgent() != null
                            ? order.getAssignedAgent().getAgentName() 
                            : "-") + "\n");

            writer.write("======================================================\n\n");

            System.out.println("Invoice appended to: " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error writing receipt: " + e.getMessage());
        }
    }
}
