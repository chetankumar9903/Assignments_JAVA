package com.aurionpro.payment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.aurionpro.order.OrderPaymentMode;


public class PaymentDetails {
    private OrderPaymentMode mode;
    private double amount;
    private String paymentReferenceId;
    private LocalDateTime paymentTime;
    private double discount;
    private double finalAmount; 
//    private double deliveryFee;
    private static double deliveryFee = 30.0;
    



    public PaymentDetails(OrderPaymentMode mode, double originalAmount, double discount, double finalAmount, String referenceId,double deliveryFee) {
        this.mode = mode;
        this.amount = originalAmount;
        this.discount = discount;
       // this.finalAmount = originalAmount - discount;
        this.finalAmount = finalAmount;
        this.paymentReferenceId = referenceId;
        this.paymentTime = LocalDateTime.now();
        PaymentDetails.deliveryFee = deliveryFee;
        
    }


    public OrderPaymentMode getMode() {
        return mode;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentReferenceId() {
        return paymentReferenceId;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }
    public double getDiscount() {
    	return discount;
    }
    
    public double getFinalAmount() {
    	return finalAmount;
    }

    public static void setDeliveryFee(double fee) {
        deliveryFee = fee;
    }

    public static double getDeliveryFee() {
        return deliveryFee;
    }

  
    
   
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");
//        String statusNote = (mode == OrderPaymentMode.CASH && orderStatus != OrderStatus.DELIVERED)
//                ? " (Payment Due)" : "";


        String discountLine = (discount > 0)
                ? String.format("Discount       : ₹%.2f\n", discount)
                : "";  

        return String.format("""
                Payment Mode   : %s
                Ref ID         : %s
                Time           : %s
                Original Amount: ₹%.2f
                %sDelivery Fee   : ₹%.2f
                Final Amount   : ₹%.2f
                """,
                mode, 
                paymentReferenceId,
                paymentTime.format(formatter),
                amount,
                discountLine,
                deliveryFee,
                finalAmount
        );
    }


}
