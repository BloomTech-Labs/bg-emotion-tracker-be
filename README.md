# Lambda Labs Boys & Girls Club: Experience Tracking Spring Boot Backend

## Project Overview


### Database layout

![Image of Database Layout](labs34_DBLayout.png)

### Entities

1. Roles
- For the login accounts, we have three types of roles
   - Administrator (ADMIN)
   - Club Director (CD)
   - Youth Development Professional (YDP)
2. Users
- This is the entity that interfaces with the Frontend and Okta
- The backend stores User entity primarily to serve frontend about the role so that they can use it for protected routing on the frontend.
- User is authenticated with the Okta token.

3. Clubs
- A Club would have a list of Users, Activities, and Members
- It has join tables with the above entities.

4. Activities
- The Activities table stores a list of different activity names.

5. Members 
- A Member is a table in the database with a memberID (username). These are passive objects instead of accounts.
- The frontend can generate the memberID via using the creating QR code function and add the record to the backend.
- Members have nominal Many-to-Many relationship to Clubs, but this relationship doesn't have any real effect regarding the member submission.

6. Reactions
- The Reactions table stores a list of truncated unicode points for emojis

7. MemberReaction
- The table that stores all submission of feedbacks from the frontend scanning loop.
- Each row stores foreign keys from
   - a Club, 
   - an Activity (that is in the Club)
   - a Member
   - a Reaction
   


Primary Endpoints Documentation: <a href="https://github.com/Lambda-School-Labs/bg-emotion-tracker-be-b/blob/main/FrequentlyUsedEndpoints.md"> HERE </a>


Auto Generated Swagger Endpoints Documentations: <a href="https://bg-emotion-tracker-be-b.herokuapp.com/swagger-ui.html"> HERE </a>

Latest snapshots of Postman Collections, import these to your postman for a headstart:
- Note that the auth token expires every 3 hours, you need to resupply those by getting the tokens manually, otherwise the requests would not work.

- Local: <a href="https://www.getpostman.com/collections/1bfc89534d979bca9940"> HERE </a>
- Remote: <a href="https://www.getpostman.com/collections/6c605856f40c73c3b199"> HERE </a>



## Functionalities


### MemberReaction

Submission

- The most critical part of the backend of concern is the endpoint handling the submission of emojis.
- The submission is a POST via URL params, the format of which is documented in the primary endpoints documentation above
- Behavior:
   - If the memberID in the feedback submission is not associated with the club yet, the relationship will be automatically added.
   - The endpoint would reject on nonexistent club-activity provided as well as nonexistent memberID or reaction unicode point.
   - Any checkin/out activity would be able to receive the full list of existing emojis. On the contrary, regular activity can only be submitted with a positivity indicating emoji from a set of 5.

Search

- We have a currently unused search functionality that filters against
   - date range
   - Club Activities
   - Member
- It returns a list of MemberReactions such that any MemberReaction in the list 
   - has its Club-activity pair in the set of the given Club Activities params
   - and has its member in the set of the given Members params
   - and has its creation date within the given date range params


### Report
- See the report charts in the frontend app first for an initial look of these features.

#### Get Counts

1. By Activities
- This endpoint would count all memberReactions received by a Club-Activity for all Club-Activities, and it would be organized by clubs.

2. By Members
- This endpoint would count all memberReactions given by a member for all members, and it would be organized by clubs.

#### Get Averages
- We map the emojis into an integer in [-2,2]
   - Only the regular activity emojis (5 of them) currently are mapped into values.
   - Extra emojis that can be submitted in checkin/out default to 0 currently.
1. By Activities
- This endpoint would calculate the memberReactions average values received by a Club-Activity for all Club-Activities , and it would be organized by clubs.

2. By Members
- This endpoint would calculate the memberReactions average values given by a member for all members, and it would be organized by clubs.

### CSV

- We currently support adding clubname,memberid pairs as csv file
  - This endpoint shall be extended to receive other types of upload if needed by stakeholders.
  
- The download endpoint, as of now, returns a csv file containing all clubname,memberid pairs.
  - This endpoint shall be extended to generate other fields with given frontend parameters, if needed by stakeholders.
   


## Testing

- Currently there is only a few boilerplate unit tests.
- A full test suite would be a great addition to this project.


## Setup Notes

### For PostgreSQL add the dependency

```
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
</dependency>
```

Add the DataSourceConfig class to use PostgreSQL
Add lines to `application.properties`

```
# Which db to run
local.run.db=H2
# local.run.db=POSTGRESQL
```

****************************

### To use PostgreSQL / OKTA locally

Add `@PropertySource("file:/Users/lambdajohn/oktafoundationconf.properties")` to the `OktaFoundationApplication` class

Create a file called `/Users/lambdajohn/oktafoundationconf.properties` with the following line. Note fill in `<private>` with real values:

```
spring.security.oauth2.resourceserver.jwt.issuer-uri=<private>
okta.oauth2.client-id=<private>
okta.oauth2.issuer=<private>
SPRING.DATASOURCE.URL=jdbc:postgresql://localhost:5432/oktafoundationdb?password=password&sslmode=prefer&user=postgres`
```

The Hierarchy of where the values are found
OS environment variables.
Application properties packaged inside your jar
@PropertySource annotation on your Main Class

Use pgAdmin to create the database only before running your application!

****************************

### Connect H2 Console to Local Postgres

Following this example fill in the fields on the h2console

```
Saved Settings: Generic PostgreSQL
Setting Name:   Generic PostgreSQL

Driver Class: org.postgresql.Driver
JDBC URL:  jdbc:postgresql://localhost:5432/oktafoundationdb
User Name: postgres
Password:  password
```

****************************

### Deploy to Heroku using Maven

#### Setup Heroku

Note: fill in `<private>` with real values

```
heroku login
heroku create bg-emotion-tracker-be-b
heroku addons:create heroku-postgresql:hobby-dev -a bg-emotion-tracker-be-b
heroku config -a bg-emotion-tracker-be-b

heroku config:set spring.security.oauth2.resourceserver.jwt.issuer-uri=<private>
heroku config:set okta.oauth2.client-id=<private>
heroku config:set okta.oauth2.issuer=<private>
```

Use the Heroku GUI to access log

#### Now In Java

##### Add the plugin for heroku

```
<finalName>bg-emotion-tracker-be-b</finalName>

<!-- Heroku Maven Plugin Configuration -->
<plugin>
    <groupId>com.heroku.sdk</groupId>
    <artifactId>heroku-maven-plugin</artifactId>
    <version>3.0.2</version>
    <configuration>
        <appName>${project.build.finalName}</appName>
        <includeTarget>false</includeTarget>
        <includes>
            <include>${project.build.directory}/${project.build.finalName}.jar</include>
        </includes>
        <jdkVersion>${java.version}</jdkVersion>
        <processTypes>
            <web>java $JAVA_OPTS -Dserver.port=$PORT -jar target/${project.build.finalName}.jar</web>
        </processTypes>
    </configuration>
</plugin>
```

Add `SimpleCorsFilter.java` to handle deploying and CORS issues

In `application.properties` add `spring.h2.console.settings.web-allow-others=true`

##### To actually deploy, go to

Note You need to do this each time code is changed!

- Maven Tab
- Turn on Skip Test Mode
- Maven Goals
- mvn clean heroku:deploy -X

****************************

### Connect H2 Console to Heroku Postgres

### Note:
-  The below example serves as an illustration only, actual DB address and username/password assigned to the current project should be used (should be given by TL).


jdbc:postgresql://ec2-52-4-177-4.compute-1.amazonaws.com/d3bgb040dnfiod

On Heroku
Find data from Heroku Config Var for DATABASE_URL. For example

```
postgres://username      :password                                                        @hostname                                 :5432/database name
postgres://knpiphpcrmdjaf:faf7655d32f2361fb57a5e9d96d1e38a11c532c1070960f49fb70bf080a3b409@ec2-54-242-43-231.compute-1.amazonaws.com:5432/dc61i9dr7csv7k
H2Console - http://jrmmba-foundation.herokuapp.com/h2-console
```

Following this example fill in the fields on the h2console

```
Saved Settings: Generic PostgreSQL
Setting Name:   Generic PostgreSQL

Driver Class: org.postgresql.Driver
JDBC URL:  jdbc:postgresql://ec2-54-242-43-231.compute-1.amazonaws.com:5432/dc61i9dr7csv7k
User Name: knpiphpcrmdjaf
Password:  faf7655d32f2361fb57a5e9d96d1e38a11c532c1070960f49fb70bf080a3b409
```

*****************************

### Deploy to Heroku using GitHub and Continuous Integration (CI)
### Note that once this is set up, each time you push to the chosen branch on GitHub,
### your application will get updated on Heroku and restart

see: https://stackoverflow.com/questions/39197334/automated-heroku-deploy-from-subfolder

In the root folder of your application you will find a Procfile. Change the name of your JAR file to match the application's name of Heroku application

In the Web Interface of Heroku

Under Deploy -> Connect to GitHub
select repository (must have admin rights to repo)

Now only if not deploying from the root folder!

```
Now under Settings -> Reveal Config Vars
    Create a config var PROJECT_PATH = foundation
        The subdirectory where your application lives. If the application lives at the root, skip this step.
        The application lives in the folder where POM.XML file is.
```

```
Under Settings -> BuildPack
    add heroku/java by clicking on Java Button
    add https://github.com/timanovsky/subdir-heroku-buildpack.git. Move it to the top buildpack
        You may wish to fork the subdir-heroku-buildpack to your own repositories
```

Now for everyone

```
Back under Deploy -> Connect to GitHub -> Automatic deploys
    pick the branch you want to deploy
    Click on enable automatic deploys
```

Next time you deploy to your chosen branch, Heroku will get automatically updated!

****************************

Problem:

Heroku restarts your application once every 24 hours. It does this automatically; you have no control over it. This is a good thing and is similar to my mantra of restart your computer at least once a day!

However, if you leave your code as originally deployed (with creating the database turned on seeddata active), when Heroku restarts your application, any data changes made in the last 24 hours get wiped out. So much for presient data.

Solution:

Deploy your application as normal using postgresql

When you first deploy your application, in application.properties, you have the following set

```
spring.jpa.hibernate.ddl-auto=create
spring.datasource.initialization-mode=always
```

And you may have a SeedData class.

Make sure your application is running properly.

Now add the following config vars to your Heroku app

Config Var                            |  Value
--------------------------------------|---------
spring_jpa_hibernate_ddl-auto         |  update
spring_datasource_initialization-mode |  never
command_line_runner_enabled           |  false

So, now when your application is restarted by Heroku, the values will be read from the config vars overriding what is in application.properties and so seed data is not active and you can keep your old data!


