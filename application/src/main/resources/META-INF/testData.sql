insert into Master (busy, category, name, orders_id, id) values (false, 3, 'Evgeniy', null, 1)
insert into Master (busy, category, name, orders_id, id) values (false, 2, 'Alex', null, 2)
insert into Master (busy, category, name, orders_id, id) values (false, 5, 'Ivan', null, 3)

insert into Garage (address, id) values ('Orel-Moskovskaya-22', 1)
insert into Garage (address, id) values ('Orel-Naugorskaya-20', 2)

insert into GaragePlace (area, busy, garage_id, type, id) values (8, false, 1, 'Car lift', 1)
insert into GaragePlace (area, busy, garage_id, type, id) values (12, false, 1, 'Pit', 2)
insert into GaragePlace (area, busy, garage_id, type, id) values (8, false, 1, 'Car lift', 3)
insert into GaragePlace (area, busy, garage_id, type, id) values (8, false, 1, 'Car lift', 4)

insert into Orders (cost, endDate, kindOfWork, startDate, status, submissionDate, id) values (1000, '2020-06-01T13:00', 'Oil change', '2020-06-01T12:00', 'ACCEPTED', '2020-06-01T11:00', 1)
update GaragePlace set area=8, busy=true, garage_id=1, type='Car lift' where id=1
update Master set busy=true, category=3, name='Evgeniy', orders_id=1 where id=1

insert into Orders (cost, endDate, kindOfWork, startDate, status, submissionDate, id) values (300, '2020-05-31T15:00', 'Tire fitting', '2020-05-31T14:00', 'ACCEPTED', '2020-05-31T13:00', 2)
update GaragePlace set area=12, busy=true, garage_id=1, type='Pit' where id=2
update Master set busy=true, category=2, name='Alex', orders_id=2 where id=2

insert into Orders (cost, endDate, kindOfWork, startDate, status, submissionDate, id) values (500, '2020-05-31T12:00', 'Diagnostics', '2020-05-31T11:00', 'ACCEPTED', '2020-05-31T10:00', 3)
update GaragePlace set area=8, busy=true, garage_id=1, type='Car lift' where id=3
update Master set busy=true, category=5, name='Ivan', orders_id=3 where id=3

insert into roles (name, id) values ('ROLE_READER', 1)
insert into users (password, userName, id) values ('pass', 'user', 1)
insert into users_roles (User_id, roles_id) values (1, 1)

insert into roles (name, id) values ('ROLE_ADMIN', 2)
insert into users (password, userName, id) values ('sa', 'admin', 2)
insert into users_roles (User_id, roles_id) values (2, 2)
insert into users_roles (User_id, roles_id) values (2, 1)