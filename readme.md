##  Description
Ce microservice gère les destinataires ainsi que leur commande.

Il attaque une base de donnée relationnelle géré par hsqldb.

Les interactions se font via des api rest.

## API Rest

| description                          | endpoint                                  | parametres - url                      | body                                   | returns                                                                                                                     |
|--------------------------------------|-------------------------------------------|---------------------------------------|----------------------------------------|-----------------------------------------------------------------------------------------------------------------------------|
| creation d'un destinataire           | /rest/v1/dest  (POST)                     |                                       | {id: number, nom: string, adresse: string, codePostal: string, ville: string, pays: string} | 200 - le destinataire                                                  |

### Exemple d'appel aux api :
http(s):/hostname/rest/v1/

## Configuration :
Il y a deux fichiers de configuration : un pour le programme, un un autre pour les logs applicative.

###  exemple du fichier de configuration

```java
hibernate.dialect=org.hibernate.dialect.HSQLDialect
hibernate.connection.username=SA
hibernate.connection.password=
hibernate.connection.autocommit=false
hibernate.show_sql=false
hibernate.format_sql=true
hibernate.jdbc.batch_size=30

hibernate.agroal.minSize=15
hibernate.agroal.maxSize=50

 
hibernate.hikari.connectionTimeout=10000
hibernate.hikari.idleTimeout=300000
hibernate.hikari.maxLifetime=900000
hibernate.hikari.maximumPoolSize=20
hibernate.hikari.minimumIdle=10
hibernate.hikariPoolName=yaya-pool

hsql.path = db
hsql.database.name = app-test-banane

spring.jackson.date-format=yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
app.lock.file=D:/dvp/projets/app-test-banane/lock/app.lock


server.address=0.0.0.0
server.port=9091

```

###  exemple du fichier du logback
```xml
<configuration scan="true" scanPeriod="5000">
  <property name="CONTEXT_NAME" value="app-test-banane" />
  
  <appender name="syncFichier" class="ch.qos.logback.core.rolling.RollingFileAppender">
    <file>${LOG_PATH}/${CONTEXT_NAME}.log</file>
    <append>true</append>

    <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
      <fileNamePattern>${LOG_PATH}/${CONTEXT_NAME}.%d{yyyyMMdd}.%i.log</fileNamePattern>
      <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
        <maxFileSize>200MB</maxFileSize>
	  </timeBasedFileNamingAndTriggeringPolicy>
	  <maxHistory>20</maxHistory>
    </rollingPolicy>

    <encoder>
      <charset>UTF-8</charset>
      <pattern>%date{"yyyyMMdd' 'HH:mm:ss','SSS' 'XX"} %contextName [%thread] %-5level %logger{10}.%method:%line - %msg%n%ex{full}</pattern>
    </encoder>
  </appender>
  
  <appender name="syncConsole" class="ch.qos.logback.core.ConsoleAppender">
    <encoder>
      <charset>UTF-8</charset>
      <pattern>%date{"yyyyMMdd' 'HH:mm:ss','SSS' 'XX"} %contextName [%thread] %-5level %logger{10}.%method:%line - %msg%n%ex{full}</pattern>
    </encoder>
  </appender>
  
  <appender name="console" class="ch.qos.logback.classic.AsyncAppender">
    <discardingThreshold>0</discardingThreshold>
    <includeCallerData>true</includeCallerData>
    <neverBlock>true</neverBlock>
    <appender-ref ref="syncConsole" />
  </appender>
  
  <appender name="fichier" class="ch.qos.logback.classic.AsyncAppender">
    <discardingThreshold>0</discardingThreshold>
    <includeCallerData>true</includeCallerData>
    <neverBlock>true</neverBlock>
    <appender-ref ref="syncFichier" />
  </appender>

  <logger name="org.yal.test.banane.app" additivity="false" level="DEBUG">
    <appender-ref ref="console" />
    <appender-ref ref="fichier" />
  </logger>
  
  <logger name="org.apache.http.wire" additivity="false" level="ERROR">
    <appender-ref ref="console" />
    <appender-ref ref="fichier" />
  </logger>
  
  <root level="INFO">
    <appender-ref ref="console" />
    <appender-ref ref="fichier" />
  </root>

</configuration>
```

## description du programme

### les packages
Le programme est divisé en couches (par package)

| package    | description               |
|------------|---------------------------|
|            |                           |
| business   | the business layer        |
| db         | persistence layer         |
| exception  | all exceptions used       |
| executable | la classe d'éxécution     |
| hsqldb     | hsqldb management         |
| modele     | le modele de données      |
| rest       | les API rest              |


### frameworks ou librairies
Le prgramme est sous le formalisme de maven ; pour le compiler => mvn package

 - **Spring** (MVC) 
 - **Hsqldb** pour la persistence des données.
 - **Hibernate** pour l'accès aux données
 - **logback** 
 - **Java** version : **11**.


Le chemin complet du fichier de configuration doit être passé en ligne de commande lors de l'exécution du programme.

→ -Dfichier.config=/chemin/vers/le/fichier/de/properties

Le chemin complet du fichier de configuration des log applicatives (logback) doit être passé en ligne de commande lors de l'exécution du programme.

→ -Dchemin.log=/chemin/vers/logback.xml

A l'intérieur de cette configuration, une variable est utilisée pour le chemin des logs : LOG_PATH. Il est possible de la définir également en ligne de commande.

→ -DLOG_PATH=/chemin/vers/le/repertoire/des/logs

### example d'appel 

%JAVA_PATH% -Dfichier.config=/chemin/vers/le/fichier/de/properties -Dchemin.log=/chemin/vers/logback.xml -DLOG_PATH=/chemin/vers/le/repertoire/des/logs -jar app-test-banane.jar

