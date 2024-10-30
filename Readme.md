## Процедура запуска автотестов
### ПО, необходимое для запуска автотестов
1. Docker Desktop (должна быть создана учетная запись)
2. IntellijIdea (SDK temurin-11 Eclipse Temurin)
3. Plugins в IntellijIdea: Docker, Lombok, Gradle
4. VPN для скачивания плагинов

### Процедура запуска автотестов:
1. Установить ПО и скачать плагины
2. Создать локальный репозиторий на ПК
3. Скопировать код проекта в GitHub
4. Выполнить команду git clone в локальный репозиторий
5. Открыть проект в IntellijIdea
6. Запустить Docker Desktop

### Подключение SUT к MySQL
1. В терминале в корне проекта запустить контейнер:
  
docker-compose up -d

2. Запустить приложение 

java -jar aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:3306/app

3. Запустить автотесты

.\gradlew clean test -DdbUrl=jdbc:mysql://localhost:3306/app

4. Создать отчеты Allure

.\gradlew allureServe

### Подключение SUT к PostgreSQL
1. В терминале в корне проекта запустить контейнер:

docker-compose up -d

2. Запустить приложение

java -jar aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/app

3. Запустить автотесты

.\gradlew clean test -DdbUrl=jdbc:postgresql://localhost:5432/app

4. Создать отчеты Allure

.\gradlew allureServe