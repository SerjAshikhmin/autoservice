package com.senla.courses.autoservice.dao;

import com.senla.courses.autoservice.dao.interfaces.IGaragePlaceDao;
import com.senla.courses.autoservice.dao.jpadao.AbstractJpaDao;
import com.senla.courses.autoservice.dao.jpadao.DbJpaConnector;
import com.senla.courses.autoservice.model.domain.GaragePlace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


@Repository
public class GaragePlaceDao extends AbstractJpaDao<GaragePlace> implements IGaragePlaceDao {

    @Autowired
    DbJpaConnector dbJpaConnector;

    @Override
    public int addGaragePlace(GaragePlace garagePlace) throws PersistenceException {
        return insert(garagePlace);
    }

    @Override
    public int removeGaragePlace(GaragePlace garagePlace) throws PersistenceException {
        if (garagePlace.getOrder() != null) {
            garagePlace.getOrder().setGaragePlace(null);
        }
        return delete(garagePlace);
    }

    @Override
    public int updateGaragePlace(GaragePlace garagePlace) throws PersistenceException {
        return update(garagePlace);
    }

    @Override
    public GaragePlace getGaragePlaceById(int garageId, int garagePlaceId) throws PersistenceException {
        GaragePlace garagePlace;
        EntityManager entityManager = dbJpaConnector.openSession();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GaragePlace> garagePlaceCriteria = criteriaBuilder.createQuery(GaragePlace.class);
        Root<GaragePlace> garagePlaceRoot = garagePlaceCriteria.from(GaragePlace.class);
        garagePlaceCriteria.select(garagePlaceRoot);
        garagePlaceCriteria.where(criteriaBuilder.equal(garagePlaceRoot.get("id"), garagePlaceId),
                                  criteriaBuilder.equal(garagePlaceRoot.get("garage").get("id"), garageId));
        garagePlace = entityManager.createQuery(garagePlaceCriteria).getSingleResult();

        return garagePlace;
    }

}
