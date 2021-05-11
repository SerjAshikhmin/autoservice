package com.senla.courses.autoservice.dao.dbqueries;

public interface DbQueries {

    String CREATE_SCHEMA_QUERY = "CREATE SCHEMA IF NOT EXISTS autoservice;";
    String CREATE_GARAGE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS autoservice.Garage (" +
                                           "id INT NOT NULL, " +
                                           "adress VARCHAR(200) NULL, " +
                                           "PRIMARY KEY (id)" +
                                       ");";
    String CREATE_GARAGE_PLACE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS autoservice.GaragePlace (" +
                                                 "id INT NOT NULL, " +
                                                 "type VARCHAR(50) NULL, " +
                                                 "area INT NULL, " +
                                                 "busy TINYINT(1) NOT NULL, " +
                                                 "garageId INT NOT NULL, " +
                                                 "PRIMARY KEY (id, garageId), " +
                                                 "CONSTRAINT fk_GaragePlace_Garage FOREIGN KEY (garageId) " +
                                                 "REFERENCES autoservice.Garage (id)" +
                                                 " ON DELETE CASCADE" +
                                                 " ON UPDATE CASCADE" +
                                             ");";
    String CREATE_ORDER_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS autoservice.Orderr (" +
                                          "id INT NOT NULL, " +
                                          "submissionDate VARCHAR(50) NULL, " +
                                          "startDate VARCHAR(50) NULL, " +
                                          "endDate VARCHAR(50) NULL, " +
                                          "kindOfWork VARCHAR(200) NULL, " +
                                          "cost INT NULL, " +
                                          "status VARCHAR(50) NULL, " +
                                          "garagePlaceId INT NULL, " +
                                          "garagePlaceGarageId INT NULL, " +
                                          "PRIMARY KEY (id), " +
                                          "CONSTRAINT fk_Order_GaragePlace1" +
                                          " FOREIGN KEY (garagePlaceId , garagePlaceGarageId)" +
                                          " REFERENCES autoservice.GaragePlace (id , garageId)" +
                                          " ON DELETE CASCADE" +
                                          " ON UPDATE CASCADE" +
                                      ");";
    String CREATE_MASTER_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS autoservice.Master (" +
                                           "id INT NOT NULL, " +
                                           "name VARCHAR(50) NOT NULL, " +
                                           "category INT NULL, " +
                                           "busy TINYINT(1) NOT NULL, " +
                                           "orderId INT NULL, " +
                                           "PRIMARY KEY (id), " +
                                           "CONSTRAINT fk_Master_Order1 " +
                                           "FOREIGN KEY (orderId) " +
                                           "REFERENCES autoservice.Orderr (id) " +
                                           "ON DELETE CASCADE " +
                                           "ON UPDATE CASCADE " +
                                        ");";

    String INSERT_MASTER_QUERY = "INSERT INTO autoservice.Master (name, category, busy, orderId, id) values (?, ?, ?, ?, ?)";
    String DELETE_MASTER_QUERY = "DELETE FROM autoservice.Master WHERE id = ?";
    String UPDATE_MASTER_QUERY = "UPDATE autoservice.Master SET name = ?, category = ?, busy = ?, orderid = ? WHERE id = ?";
    String SELECT_MASTER_QUERY = "SELECT * FROM autoservice.Master WHERE id = ?";
    String SELECT_ALL_MASTERS_QUERY = "SELECT * FROM autoservice.Master";
    String SELECT_MASTERS_BY_ORDER_QUERY = "SELECT * FROM autoservice.Master WHERE orderid = ?";

    String INSERT_GARAGE_QUERY = "INSERT INTO autoservice.Garage (adress, id) values (?, ?)";
    String DELETE_GARAGE_QUERY = "DELETE FROM autoservice.Garage WHERE id = ?";
    String UPDATE_GARAGE_QUERY = "UPDATE autoservice.Garage SET adress = ? WHERE id = ?";
    String SELECT_GARAGE_QUERY = "SELECT * FROM autoservice.Garage WHERE id = ?";
    String SELECT_ALL_GARAGES_QUERY = "SELECT * FROM autoservice.Garage";

    String INSERT_GARAGE_PLACE_QUERY = "INSERT INTO autoservice.GaragePlace (type, area, busy, id, garageId) values (?, ?, ?, ?, ?)";
    String DELETE_GARAGE_PLACE_QUERY = "DELETE FROM autoservice.GaragePlace WHERE id = ?";
    String UPDATE_GARAGE_PLACE_QUERY = "UPDATE autoservice.GaragePlace SET type = ?, area = ?, busy = ? WHERE id = ? AND garageId = ?";
    String SELECT_GARAGE_PLACE_QUERY = "SELECT * FROM autoservice.GaragePlace WHERE id = ? AND garageId = ?";
    String SELECT_ALL_GARAGE_PLACES_QUERY = "SELECT * FROM autoservice.GaragePlace";

    String INSERT_ORDER_QUERY = "INSERT INTO autoservice.Orderr (submissiondate, startdate, enddate, kindofwork, cost, status, " +
                                    "garageplaceid, garageplacegarageid, id) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String DELETE_ORDER_QUERY = "DELETE FROM autoservice.Orderr WHERE id = ?";
    String UPDATE_ORDER_QUERY = "UPDATE autoservice.Orderr SET " +
                                    "submissiondate = ?, startdate = ?, enddate = ?, kindofwork = ?, cost = ?, status = ?, " +
                                    "garageplaceid = ?, garageplacegarageid = ? WHERE id = ?";
    String SELECT_ORDER_QUERY = "SELECT * FROM autoservice.Orderr WHERE id = ?";
    String SELECT_ALL_ORDERS_QUERY = "SELECT * FROM autoservice.Orderr";

}
