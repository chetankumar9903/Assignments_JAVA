package com.aurionpro.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.aurionpro.order.Order;

public class DeliveryService {

    private List<DeliveryAgent> agents;

    public DeliveryService() {
        agents = new ArrayList<>();
        
        agents.add(new SwiggyAgent("Swiggy"));
        agents.add(new EatClubAgent("EatClub"));
        agents.add(new ZomatoAgent("Zomato"));
        
    }

    public void assignAgent(Order order) {
        List<DeliveryAgent> availableAgents = new ArrayList<>();
        for (DeliveryAgent agent : agents) {
            if (!agent.isBusy()) {
                availableAgents.add(agent);
            }
        }

        if (availableAgents.isEmpty()) {
            System.out.println("No available delivery agents for order " + order.getOrderId());
            return;
        }

        // Random selection
        DeliveryAgent selectedAgent = availableAgents.get(new Random().nextInt(availableAgents.size()));
        selectedAgent.assignOrder(order);
        order.setAssignedAgent(selectedAgent);
        //System.out.println(selectedAgent + " assigned to Order " + order.getOrderId());
    }
    
//    public void assignAgent(Order order) {
//        for (DeliveryAgent agent : agents) {
//            if (!agent.isBusy()) {
//                agent.assignOrder(order);
//                order.setAssignedAgent(agent);
//                return;
//            }
//        }
//        System.out.println("No available delivery agents for order " + order.getOrderId());
//    }


    public void deliverOrder(Order order) {
        if (order.getAssignedAgent() != null) {
            order.getAssignedAgent().deliverOrder(order);
        } else {
            System.out.println("No delivery agent assigned for order " + order.getOrderId());
        }
    }

    public void showAvailableAgents() {
        boolean found = false;
        for (DeliveryAgent agent : agents) {
//       	 System.out.println(agent.getAgentName() + " busy? " + agent.isBusy());
            if (!agent.isBusy()) {
                System.out.println(agent);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No agents currently available.");
        }
    }

    public void showAllAgents() {
//        System.out.println("All Delivery Agents:");
        for (DeliveryAgent agent : agents) {
            System.out.println(agent + (agent.isBusy() ? " - BUSY" : " - AVAILABLE"));
        }
    }
}
