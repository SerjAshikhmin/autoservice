-- 1. Найдите номер модели, скорость и размер жесткого диска для всех ПК стоимостью менее 500 дол. Вывести: model, speed и hd

SELECT model, speed, hd FROM `mydb`.`pc` WHERE price < 500;

-- 2. Найдите производителей принтеров. Вывести: maker

SELECT maker FROM `mydb`.`product` WHERE type = 'printer';

-- 3. Найдите номер модели, объем памяти и размеры экранов ПК-блокнотов, цена которых превышает 1000 дол.

SELECT model, ram, screen FROM `mydb`.`laptop` WHERE price > 1000;

-- 4. Найдите все записи таблицы Printer для цветных принтеров.

SELECT * FROM `mydb`.`printer` WHERE color = 'y';

-- 5. Найдите номер модели, скорость и размер жесткого диска ПК, имеющих 12x или 24x CD и цену менее 600 дол.

SELECT model, speed, hd FROM `mydb`.`pc` WHERE (cd = '12x' or  cd = '24x') AND price < 600;

-- 6. Укажите производителя и скорость для тех ПК-блокнотов, которые имеют жесткий диск объемом не менее 100 Гбайт.

SELECT maker, speed FROM `mydb`.`product` JOIN `mydb`.`laptop` ON product.model = laptop.model WHERE hd >= 100 AND type = 'laptop';

-- 7. Найдите номера моделей и цены всех продуктов (любого типа), выпущенных производителем B (латинская буква).

SELECT laptop.model, laptop.price FROM `mydb`.`laptop` JOIN `mydb`.`product` ON laptop.model = product.model WHERE maker = 'HP'
UNION
SELECT pc.model, pc.price FROM `mydb`.`pc` JOIN `mydb`.`product` ON pc.model = product.model WHERE maker = 'HP'
UNION
SELECT printer.model, printer.price FROM `mydb`.`printer` JOIN `mydb`.`product` ON printer.model = product.model WHERE maker = 'HP';

-- 8. Найдите производителя, выпускающего ПК, но не ПК-блокноты.

SELECT DISTINCT maker from `mydb`.`product` WHERE product.type = 'pc'
AND product.type NOT IN (SELECT DISTINCT maker from `mydb`.`product` WHERE type = 'laptop');

-- 9. Найдите производителей ПК с процессором не менее 450 Мгц. Вывести:

SELECT DISTINCT maker from `mydb`.`product`, `mydb`.`pc` WHERE speed >= 450 AND product.model = pc.model;

-- 10. Найдите принтеры, имеющие самую высокую цену. Вывести: model, price

SELECT model, price from `mydb`.`printer` WHERE price = (SELECT MAX(price) FROM `mydb`.`printer`);

-- 11. Найдите среднюю скорость ПК.

SELECT AVG(price) from `mydb`.`pc`;

-- 12. Найдите среднюю скорость ПК-блокнотов, цена которых превышает 1000 дол.

SELECT AVG(price) from `mydb`.`laptop` WHERE price > 1000;

-- 13. Найдите среднюю скорость ПК, выпущенных производителем A.

SELECT AVG(speed) from `mydb`.`pc` JOIN `mydb`.`product` ON pc.model = product.model WHERE maker = 'HP';

-- 14. Для каждого значения скорости найдите среднюю стоимость ПК с такой же скоростью процессора. Вывести: скорость, средняя цена

SELECT speed, AVG(price) from `mydb`.`pc` GROUP BY speed;

-- 15. Найдите размеры жестких дисков, совпадающих у двух и более PC. Вывести: HD

SELECT hd FROM `mydb`.`pc` GROUP BY hd HAVING COUNT(*) > 1;

-- 16. Найдите пары моделей PC, имеющих одинаковые скорость и RAM. В результате каждая пара указывается только один раз, т.е. (i,j), но не (j,i), Порядок вывода: модель с большим номером, модель с меньшим номером, скорость и RAM.

SELECT DISTINCT
    CASE WHEN first.model > second.model THEN first.model
	ELSE second.model
    END as firstModel, 
    CASE WHEN first.model < second.model THEN first.model
	ELSE second.model
    END as secondModel, 
    first.speed, first.ram FROM `mydb`.`pc` first, `mydb`.`pc` second 
WHERE first.speed = second.speed AND first.ram = second.ram AND first.model != second.model;

-- 17. Найдите модели ПК-блокнотов, скорость которых меньше скорости любого из ПК. Вывести: type, model, speed

SELECT DISTINCT laptop.model FROM `mydb`.`laptop`, `mydb`.`pc` WHERE laptop.speed < ALL(SELECT speed FROM `mydb`.`pc`);

-- 18. Найдите производителей самых дешевых цветных принтеров. Вывести: maker, price

SELECT maker, price FROM `mydb`.`product`, `mydb`.`printer` 
	WHERE price = (SELECT MIN(price) FROM `mydb`.`printer` WHERE color = 'y') 
    AND printer.model = product.model;

-- 19. Для каждого производителя найдите средний размер экрана выпускаемых им ПК-блокнотов. Вывести: maker, средний размер экрана.

SELECT maker, AVG(screen) FROM `mydb`.`product`, `mydb`.`laptop` 
	WHERE laptop.model = product.model GROUP BY maker;

-- 20. Найдите производителей, выпускающих по меньшей мере три различных модели ПК. Вывести: Maker, число моделей

SELECT * FROM (
	SELECT maker,
		CASE WHEN COUNT(pc.model) > 2 THEN COUNT(pc.model) 
		END as modelCount FROM `mydb`.`product` JOIN `mydb`.`pc` ON pc.model = product.model GROUP BY maker
) as t1 WHERE t1.modelCount is not NULL;

-- 21. Найдите максимальную цену ПК, выпускаемых каждым производителем. Вывести: maker, максимальная цена.

SELECT maker, MAX(price) as maxPrice FROM `mydb`.`product` JOIN `mydb`.`pc` ON pc.model = product.model GROUP BY maker;

-- 22. Для каждого значения скорости ПК, превышающего 600 МГц, определите среднюю цену ПК с такой же скоростью. Вывести: speed, средняя цена.

SELECT first.speed, AVG(second.price) as averagePrict FROM `mydb`.`pc` first, `mydb`.`pc` second 
	WHERE first.speed = second.speed AND first.speed > 600 GROUP BY first.speed;

-- 23. Найдите производителей, которые производили бы как ПК со скоростью не менее 750 МГц, так и ПК-блокноты со скоростью не менее 750 МГц. Вывести: Maker

SELECT DISTINCT maker FROM `mydb`.`product`, `mydb`.`pc`, `mydb`.`laptop`
	WHERE maker IN (SELECT maker FROM `mydb`.`product` JOIN `mydb`.`pc` ON pc.model = product.model WHERE pc.speed >= 750)
    AND maker IN (SELECT maker FROM `mydb`.`product` JOIN `mydb`.`laptop` ON laptop.model = product.model WHERE laptop.speed >= 750);

-- 24. Перечислите номера моделей любых типов, имеющих самую высокую цену по всей имеющейся в базе данных продукции.

SELECT MAX(price) FROM (
	SELECT MAX(price) as price FROM `mydb`.`pc`
    UNION
    SELECT MAX(price) as price FROM `mydb`.`laptop`
    UNION
    SELECT MAX(price) as price FROM `mydb`.`printer`
) as price;

-- 25. Найдите производителей принтеров, которые производят ПК с наименьшим объемом RAM и с самым быстрым процессором среди всех ПК, имеющих наименьший объем RAM. Вывести: Maker

SELECT maker FROM `mydb`.`product` JOIN `mydb`.`printer` ON product.model = printer.model
WHERE maker IN (
	SELECT maker FROM `mydb`.`product` JOIN `mydb`.`pc` ON product.model = pc.model
    WHERE speed = 
		(SELECT MAX(speed) FROM `mydb`.`product` JOIN `mydb`.`pc` ON product.model = pc.model
		WHERE ram = 
			(SELECT MIN(ram) FROM `mydb`.`product` JOIN `mydb`.`pc` ON product.model = pc.model)
        )    
);