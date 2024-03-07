package com.app.dao;

import com.app.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TicketsDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void storeNewTicket(Ticket ticket) {
        jdbcTemplate.update("INSERT INTO tickets (summary, description, reporter_id, project_id) " +
                "VALUES (?, ?, ?, ?)",
                ticket.getSummary(), ticket.getDescription(), ticket.getReporterId(), ticket.getProjectId());
    }
     public List<Ticket> getTicketsByProject(long projectId) {
         RowMapper<Ticket> rowMapper = (rs, rowNumber) -> mapTicket(rs);
         return jdbcTemplate.query("SELECT * FROM tickets WHERE project_id = ?", rowMapper, projectId);
     }

    private Ticket mapTicket(ResultSet rs) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(rs.getLong("id"));
        ticket.setSummary(rs.getString("summary"));
        ticket.setDescription(rs.getString("description"));
        ticket.setReporterId(rs.getLong("reporter_id"));
        ticket.setProjectId(rs.getLong("project_id"));
        return ticket;
    }


}
