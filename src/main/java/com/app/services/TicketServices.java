package com.app.services;

import com.app.dao.TicketsDAO;
import com.app.model.Ticket;
import com.app.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServices {
    @Autowired
    private TicketsDAO ticketsDAO;

    public void storeNewTicket(Ticket ticket) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUser = (CustomUserDetails) authentication.getPrincipal();

        ticket.setReporterId(customUser.getUser().getId());
        ticketsDAO.storeNewTicket(ticket);
    }

    public List<Ticket> getTicketsByProject(long projectId) {
        return ticketsDAO.getTicketsByProject(projectId);
    }
}
