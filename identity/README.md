# MAKERSHARKS - IDENTITY SERVICE

SCENARIOS HANDLED :
1. User Registration
2. User Login
3. User Data Fetching

DAO and Header Data Instances mentioned on Swagger Documentation
URL :
http://localhost:8005/identity/swagger-ui/index.html

Running Application :

JAVA
1. Set up environment variable as - SPRING.PROFILES.ACTIVE = dev
2. Unit Tests to be run with variable - SPRING.PROFILES.ACTIVE = test

DATABASE
1. Setup MySQL Server on local instance with Below Details
Username - root
password - root
port - 3305
2. Queries to be run after connecting to MYSQL Database
   create database identity;
   
   use identity;
   
   create table User(
   ID varchar(255),
   USERNAME varchar(255),
   EMAIL varchar(255),
   FIRST_NAME varchar(255),
   MIDDLE_NAME varchar(255),
   LAST_NAME varchar(255),
   PASSWORD varchar(255),
   PRIMARY KEY(ID)
   );

   ALTER TABLE USER ADD COLUMN(DATE_CREATED varchar(255));

   commit;

POSTMAN
1. Sample API Curl for register
   curl --location 'http://localhost:8005/identity/api/user/register' \
   --header 'Content-Type: application/json' \
   --data-raw '{
   "username":"abhishek_mitra",
   "email":"abhishekmitra@gmail.com",
   "password":"Abhishek@123",
   "confirmPassword":"Abhishek@123",
   "firstName":"Abhishek",
   "middleName":"Deep",
   "lastName":"Mitra"
   }'
2. Sample API Curl for login
   curl --location 'http://localhost:8005/identity/api/user/login' \
   --header 'Content-Type: application/json' \
   --data-raw '{
   "username":"abhishek_mitra",
   "password":"Abhishek@123"
   }'
3. Sample API Curl for fetch
   curl --location 'http://localhost:8005/identity/api/user/fetch?username=abhishek_mitra' \
   --header 'token: eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJlZWJkY2IyOC03NGIxLTRiMzMtOGY3NC01ZmFhMDQ0NWY0NGYiLCJuYW1lIjoiQWthc2ggTWl0cmEiLCJlbWFpbCI6ImFrYXNoLm1pdHJhMTYxMTk0QGdtYWlsLmNvbSIsInVzZXJuYW1lIjoic2tpbXB5X3N0b2ljIiwicm9sZXMiOlsiVVNFUiJdLCJpYXQiOjE3MTg2NDI0MzF9.ZPdlYEm7h8pIarEfknburBhLiqTWTAaRmOVZRuag3D8'
NOTE : Header token for authorisation of fetch API is received as response from the 2nd API.
