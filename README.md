## Autoservice

Учебный Java-проект - прототип автосервиса в виде REST-интерфейса с авторизацией и правами доступа
на основе ролей (ADMIN, READER). Администратор может создавать/удалять/отменять/закрывать
заказы, добавлять/удалять/редактировать мастеров, добавлять/удалять место в гараже.
Пользователь может просматривать следующую информацию:
- о конкретном заказе;
- список заказов за определенный период времени;
- список заказов, отсортированных по стоимости работ, дате начала, окончания или подтвреждения заказа;
- все заказы, выполняющиеся в данный момент;
- ближайшая свободная дата;
- список мастеров, выполняющих конкретный заказ.

### Стек технологий:

Spring MVC, Spring Security, Hibernate ORM, JPA, Json Jackson, Spring Validation, Mapstruct, SLF4J, Lombok, Apache Tomcat, H2QL, JUnit, Mockito, Spring MVC Test, Gradle.