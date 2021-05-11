package com.senla.courses.autoservice.dao.interfaces;

import com.senla.courses.autoservice.model.domain.Garage;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;
import java.util.List;


@Repository
public interface IGarageDao {

    int addGarage(Garage garage) throws PersistenceException;
    int removeGarage(Garage garage) throws PersistenceException;
    Garage getGarageById(int id) throws PersistenceException;
    List<Garage> getAllGarages() throws PersistenceException;
    void setAllGarages(List<Garage> allGarages);
    int updateGarage(Garage garage) throws PersistenceException;

}
