-- products

INSERT INTO `mydb`.`product` VALUES ('HP', 'Pavilion', 'laptop');
INSERT INTO `mydb`.`product` VALUES ('Dell', 'Alienware', 'laptop');
INSERT INTO `mydb`.`product` VALUES ('Dell', 'Alienware3', 'laptop');
INSERT INTO `mydb`.`product` VALUES ('Lenovo', 'Yoga', 'laptop');

INSERT INTO `mydb`.`product` VALUES ('HP', 'Laser 107a', 'printer');
INSERT INTO `mydb`.`product` VALUES ('Canon', 'i-SENSYS LBP623Cdw', 'printer');
INSERT INTO `mydb`.`product` VALUES ('Intel', 'dw688', 'printer');
INSERT INTO `mydb`.`product` VALUES ('Xerox', 'al3020', 'printer');

INSERT INTO `mydb`.`product` VALUES ('Intel', 'NUC 7', 'pc');
INSERT INTO `mydb`.`product` VALUES ('HP', 'Pavilion Gaming', 'pc');
INSERT INTO `mydb`.`product` VALUES ('Intel', 'NUC 8', 'pc');
INSERT INTO `mydb`.`product` VALUES ('Intel', 'NUC 9', 'pc');
INSERT INTO `mydb`.`product` VALUES ('Intel', 'NUC 10', 'pc');
INSERT INTO `mydb`.`product` VALUES ('HP', 'PAV 6', 'pc');
INSERT INTO `mydb`.`product` VALUES ('Dell', 'Alien', 'pc');
INSERT INTO `mydb`.`product` VALUES ('AMD', 'Xeon', 'pc');


-- laptops

INSERT INTO `mydb`.`laptop` VALUES (1, 'Pavilion', 2000, 4096, 500, 400, 17);
INSERT INTO `mydb`.`laptop` VALUES (2, 'Alienware', 3000, 16384, 2000, 1200, 19);
INSERT INTO `mydb`.`laptop` VALUES (3, 'Alienware3', 300, 2048, 2000, 450, 15);
INSERT INTO `mydb`.`laptop` VALUES (4, 'Yoga', 300, 2048, 2000, 1600, 14);

-- printers

INSERT INTO `mydb`.`printer` VALUES (1, 'Laser 107a', 'y', 'Laser', 500);
INSERT INTO `mydb`.`printer` VALUES (2, 'i-SENSYS LBP623Cdw', 'n', 'Laser', 300);
INSERT INTO `mydb`.`printer` VALUES (3, 'dw688', 'y', 'Laser', 600);
INSERT INTO `mydb`.`printer` VALUES (4, 'al3020', 'y', 'Jet', 600);

-- pc's

INSERT INTO `mydb`.`pc` VALUES (1, 'NUC 7', 400, 4096, 256, '24x', 450);
INSERT INTO `mydb`.`pc` VALUES (2, 'Pavilion Gaming', 2500, 16384, 2000, '36x', 1500);
INSERT INTO `mydb`.`pc` VALUES (3, 'NUC 8', 400, 4096, 256, '24x', 900);
INSERT INTO `mydb`.`pc` VALUES (4, 'NUC 9', 800, 4096, 256, '36x', 1200);
INSERT INTO `mydb`.`pc` VALUES (5, 'NUC 10', 800, 4096, 256, '36x', 600);
INSERT INTO `mydb`.`pc` VALUES (6, 'PAV 6', 600, 4096, 256, '12x', 700);
INSERT INTO `mydb`.`pc` VALUES (7, 'Alien', 600, 4096, 256, '12x', 700);
INSERT INTO `mydb`.`pc` VALUES (8, 'Xeon', 350, 4096, 256, '12x', 700);