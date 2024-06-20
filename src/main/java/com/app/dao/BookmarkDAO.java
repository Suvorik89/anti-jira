package com.app.dao;

import com.app.model.Bookmark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookmarkDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void storeBookmark(Bookmark bookmark) {
        jdbcTemplate.update("INSERT INTO bookmarks (ticket_id, user_id) VALUES (?, ?)",
            bookmark.getTicketId(), bookmark.getUserId());
    }

    public void deleteBookmark(long bookmarkId) {
        jdbcTemplate.update(" DELETE FROM bookmarks WHERE id = ?", bookmarkId);
    }

    public List<Bookmark> getUserBookmarks (long userId) {
        RowMapper<Bookmark> rowMapper = (rs, rowNumber) -> mapBookmark(rs);
        return jdbcTemplate.query("SELECT * FROM bookmarks WHERE user_id = ?", rowMapper, userId);
    }

    public int getBookmarksCount(long userId){
        return jdbcTemplate.queryForObject("SELECT COUNT(id) FROM bookmarks WHERE id = ?", Integer.class, userId);
    }

    private Bookmark mapBookmark (ResultSet rs) throws SQLException {
        Bookmark bookmark = new Bookmark();

        bookmark.setId(rs.getLong("id"));
        bookmark.setTicketId(rs.getLong("ticket_id"));
        bookmark.setUserId(rs.getLong("user_id"));

        return bookmark;
    }
}
