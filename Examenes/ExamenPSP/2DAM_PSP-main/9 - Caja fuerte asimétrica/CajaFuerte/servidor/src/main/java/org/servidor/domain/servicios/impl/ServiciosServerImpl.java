package org.servidor.domain.servicios.impl;

import jakarta.inject.Inject;
import org.servidor.dao.impl.ServerDaoImpl;
import org.servidor.domain.servicios.ServiciosServer;

public class ServiciosServerImpl implements ServiciosServer {

        private final ServerDaoImpl serverDao;

        @Inject
        public ServiciosServerImpl(ServerDaoImpl serverDaoImpl) {
            this.serverDao = serverDaoImpl;
        }

        @Override
        public String getPublic() {
            return serverDao.getPublic();
        }

}
