package config;

import com.senla.courses.autoservice.dao.GarageDao;
import com.senla.courses.autoservice.dao.GaragePlaceDao;
import com.senla.courses.autoservice.dao.MasterDao;
import com.senla.courses.autoservice.dao.OrderDao;
import com.senla.courses.autoservice.dao.jpadao.DbJpaConnector;
import com.senla.courses.autoservice.dto.mappers.*;
import com.senla.courses.autoservice.exceptions.masterexceptions.MasterNotFoundException;
import com.senla.courses.autoservice.exceptions.orderexceptions.OrderNotFoundException;
import com.senla.courses.autoservice.service.GarageService;
import com.senla.courses.autoservice.service.MasterService;
import com.senla.courses.autoservice.service.OrderService;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import tests.TestData;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;


@Configuration
@PropertySource("classpath:config.properties")
public class TestConfig {

    @Autowired
    private TestData testData;

    @Bean
    public MasterService masterService() {
        return new MasterService();
    }

    @Bean
    public GarageService garageService() {
        return new GarageService();
    }

    @Bean
    public OrderService orderService() {
        return new OrderService();
    }

    @Bean
    public TestData testData() {
        return new TestData();
    }

    @Bean
    public MasterDao masterDao() throws MasterNotFoundException {
        return mock(MasterDao.class);
    }

    @Bean
    public GarageDao garageDao() {
        return mock(GarageDao.class);
    }

    @Bean
    public GaragePlaceDao garagePlaceDao() {
        return mock(GaragePlaceDao.class);
    }

    @Bean
    public OrderDao orderDao() throws OrderNotFoundException {
        return mock(OrderDao.class);
    }

    @Bean
    public DbJpaConnector dbJpaConnector() {
        DbJpaConnector dbJpaConnectorMock = mock(DbJpaConnector.class);
        when(dbJpaConnectorMock.getTransaction()).thenReturn(new EntityTransaction() {
            @Override
            public void begin() {
            }

            @Override
            public void commit() {
            }

            @Override
            public void rollback() {
            }

            @Override
            public void setRollbackOnly() {
            }

            @Override
            public boolean getRollbackOnly() {
                return false;
            }

            @Override
            public boolean isActive() {
                return false;
            }
        });

        return dbJpaConnectorMock;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        return mock(EntityManagerFactory.class);
    }

    @Bean
    public MasterMapper masterMapper() {
        return new MasterMapperImpl();
    }

    @Bean
    public OrderMapper orderMapper() {
        return new OrderMapperImpl();
    }

    @Bean
    public GarageMapper garageMapper() {
        return new GarageMapperImpl();
    }

    @Bean
    public GaragePlaceMapper garagePlaceMapper() {
        return new GaragePlaceMapperImpl();
    }
}
