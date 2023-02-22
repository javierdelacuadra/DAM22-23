package config;

import dao.impl.DBConnection;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener()
public class ConfigListener implements ServletContextListener {

    DBConnection pool;

    @Inject
    public ConfigListener(DBConnection pool) {
        this.pool = pool;
    }

    // Public constructor is required by servlet spec
    public ConfigListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context
         (the Web application) is undeployed or
         Application Server shuts down.
      */
        pool.closePool();
    }

}
