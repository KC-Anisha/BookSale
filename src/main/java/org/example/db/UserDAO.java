package org.example.db;

import org.example.api.User;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface UserDAO {
    @SqlQuery("select * from users")
    @RegisterConstructorMapper(User.class)
    List<User> getAllUsers();

    @SqlQuery("select * from users where id = ?")
    @RegisterConstructorMapper(User.class)
    User getUser(int id);

    @SqlUpdate("insert into users (first_name, username, last_name, email) values (?, ?, ?, ?)")
    @GetGeneratedKeys("id")
    int addUser(String first_name, String username, String last_name, String email);

    @SqlUpdate("update users set first_name = ?, username = ?, last_name = ? where id = ?")
    boolean updateUser(String first_name, String username, String last_name, int id);

    @SqlUpdate("delete from users where id = ?")
    boolean deleteUser(int id);
}
