CREATE SCHEMA IF NOT EXISTS autoservice;
USE autoservice;

CREATE TABLE IF NOT EXISTS autoservice.Garage (
  id INT NOT NULL,
  adress VARCHAR(200) NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS autoservice.GaragePlace (
  id INT NOT NULL,
  type VARCHAR(50) NULL,
  area INT NULL,
  busy TINYINT(1) NOT NULL,
  garageId INT NOT NULL,
  PRIMARY KEY (id, garageId),
  CONSTRAINT fk_GaragePlace_Garage
    FOREIGN KEY (garageId)
    REFERENCES autoservice.Garage (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS autoservice.Orderr (
  id INT NOT NULL,
  submissionDate VARCHAR(100) NULL,
  startDate VARCHAR(100) NULL,
  endDate VARCHAR(100) NULL,
  kindOfWork VARCHAR(200) NULL,
  cost INT NULL,
  status VARCHAR(50) NULL,
  garagePlaceId INT NULL,
  garagePlaceGarageId INT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_Order_GaragePlace1
    FOREIGN KEY (garagePlaceId , garagePlaceGarageId)
    REFERENCES autoservice.GaragePlace (id , garageId)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS autoservice.Master (
  id INT NOT NULL,
  name VARCHAR(50) NOT NULL,
  category INT NULL,
  busy TINYINT(1) NOT NULL,
  orderId INT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_Master_Order1
    FOREIGN KEY (orderId)
    REFERENCES autoservice.Orderr (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);