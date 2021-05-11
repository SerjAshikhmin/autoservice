-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`product` (
  `maker` VARCHAR(10) NOT NULL,
  `model` VARCHAR(50) NOT NULL,
  `type` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`model`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`laptop`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`laptop` (
  `code` INT NOT NULL,
  `model` VARCHAR(50) NOT NULL,
  `speed` SMALLINT NOT NULL,
  `ram` SMALLINT NOT NULL,
  `hd` REAL NOT NULL,
  `price` INT NULL,
  `screen` TINYINT NOT NULL,
  PRIMARY KEY (`code`, `model`),
  INDEX `fk_laptop_product1_idx` (`model` ASC) VISIBLE,
  CONSTRAINT `fk_laptop_product1`
    FOREIGN KEY (`model`)
    REFERENCES `mydb`.`product` (`model`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`pc`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`pc` (
  `code` INT NOT NULL,
  `model` VARCHAR(50) NOT NULL,
  `speed` SMALLINT NOT NULL,
  `ram` SMALLINT NOT NULL,
  `hd` REAL NOT NULL,
  `cd` VARCHAR(10) NOT NULL,
  `price` INT NULL,
  PRIMARY KEY (`code`, `model`),
  INDEX `fk_pc_product_idx` (`model` ASC) VISIBLE,
  CONSTRAINT `fk_pc_product`
    FOREIGN KEY (`model`)
    REFERENCES `mydb`.`product` (`model`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`printer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`printer` (
  `code` INT NOT NULL,
  `model` VARCHAR(50) NOT NULL,
  `color` CHAR(1) NOT NULL,
  `type` VARCHAR(10) NOT NULL,
  `price` INT NULL,
  PRIMARY KEY (`code`, `model`),
  INDEX `fk_printer_product1_idx` (`model` ASC) VISIBLE,
  CONSTRAINT `fk_printer_product1`
    FOREIGN KEY (`model`)
    REFERENCES `mydb`.`product` (`model`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
