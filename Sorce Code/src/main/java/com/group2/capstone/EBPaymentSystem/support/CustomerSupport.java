package com.group2.capstone.EBPaymentSystem.support;

import com.group2.capstone.EBPaymentSystem.authentication.models.User;

public class CustomerSupport {
    public Ticket createTicket(User user, String issue) {
        // implementation
        return null;
    }

    public void updateTicket(Ticket ticket, TicketStatus status) {
        ticket.setStatus(status);
    }
}

