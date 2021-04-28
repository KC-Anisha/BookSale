package org.example.db;

import org.example.api.Book;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.util.List;

public interface BookDAO {
    @SqlQuery("select * from books")
    @RegisterConstructorMapper(Book.class)
    List<Book> getAllBooks();

    @SqlQuery("select * from books where id = ?")
    @RegisterConstructorMapper(Book.class)
    Book getBook(int id);

    @SqlQuery("select * from books where seller_id = ?")
    @RegisterConstructorMapper(Book.class)
    List<Book> getBooksByUser(int id);

    @SqlUpdate("insert into books (seller_id, title, edition, price, book_subject, book_description, author) values (?, ?, ?, ?, ?, ?, ?)")
    @GetGeneratedKeys("id")
    int addBook(int seller_id, String title, int edition, double price, String book_subject, String book_description, String author);

    @SqlUpdate("update books set title = ?, edition = ?, price = ?, book_subject = ?, author = ? where id = ?")
    boolean updateBook(String title, int edition, double price, String book_subject, String author, int id);

    @SqlUpdate("delete from books where id = ?")
    boolean deleteBook(int id);
}
