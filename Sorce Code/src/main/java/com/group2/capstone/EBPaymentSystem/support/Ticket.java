package com.group2.capstone.EBPaymentSystem.support;

import com.group2.capstone.EBPaymentSystem.authentication.models.User;

public class Ticket {
    private final int id;
    private final User user;
    private final String issue;
    private TicketStatus status;

    public Ticket(int id, User user, String issue) {
        this.id = id;
        this.user = user;
        this.issue = issue;
        this.status = TicketStatus.OPEN;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }


    // getters and setters
}
