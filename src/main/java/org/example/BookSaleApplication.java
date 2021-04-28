package org.example;

import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.example.db.BookDAO;
import org.example.db.UserDAO;
import org.example.resources.BookResource;
import org.example.resources.UserResource;
import org.jdbi.v3.core.Jdbi;

public class BookSaleApplication extends Application<BookSaleConfiguration> {

    public static void main(final String[] args) throws Exception {
        new BookSaleApplication().run(args);
    }

    @Override
    public String getName() {
        return "BookSale";
    }

    @Override
    public void initialize(final Bootstrap<BookSaleConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final BookSaleConfiguration configuration,
                    final Environment environment) {
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "database");
        final UserDAO userDAO = jdbi.onDemand(UserDAO.class);
        final BookDAO bookDAO = jdbi.onDemand(BookDAO.class);
        environment.jersey().register(new UserResource(userDAO));
        environment.jersey().register(new BookResource(bookDAO));
    }

}
