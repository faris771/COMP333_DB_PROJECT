DROP DATABASE IF EXISTS HOTEL_COMP333;
CREATE DATABASE HOTEL_COMP333;
USE HOTEL_COMP333;

create table employee (

  eid int primary key AUTO_INCREMENT, -- 1 2 3 
  employee_SSN int UNIQUE,  
  employee_first_Name varchar(32),
  employee_father_Name varchar(32),
  employee_family_Name varchar(32),
  employee_email varchar(32),
  employee_nationality varchar(32),
  starting_date DATE DEFAULT(CURRENT_DATE()) ,
  salary real,
  password VARCHAR(32) NOT NULL

);
ALTER TABLE employee AUTO_INCREMENT = 1000;

create table services(
  service_id int primary key AUTO_INCREMENT,
  service_type varchar(32),
  service_cost real DEFAULT 0.00,
  serving_date_time DATETIME DEFAULT NOW()
);

create table Guest(
  Guest_SSN int primary key,
  Guest_first_Name varchar(32),
  Guest_father_Name varchar(32),
  Guest_family_Name varchar(32),

  Guest_email varchar(32),
  Guest_nationality varchar(32),
  phone_num VARCHAR(32) -- ONE VALUE
	-- phone is multivalued so it's a table by itself 
);

CREATE TABLE  employee_phone(

	phone_num VARCHAR(32) ,
  eid INT,
  PRIMARY KEY(phone_num,eid),
  FOREIGN KEY(eid) REFERENCES EMPLOYEE(eid)
);


create table room (
  room_number int primary key,
  room_price real,
  number_of_beds INT,
  room_type VARCHAR(32),
  room_status VARCHAR(32) -- OCCUPIED OR NOT
);


create table service_to_room( -- SAME AS 'PROVIDE'
	-- suggestion : provides service to a room not a guest
  room_number int,
  eid int,
  service_id int,
  primary key (room_number, eid, service_id),
  foreign key (room_number) references room (room_number),
  foreign key (eid) references employee (eid),
  foreign key (service_id) references services(service_id)

);



create table Booking(
  Booking_id int primary key AUTO_INCREMENT,
  room_number INT,
  guest_ssn INT,
  STARTING_DATE date,
  end_date date,
  FOREIGN KEY(room_number) REFERENCES room (room_number),
  FOREIGN KEY(guest_ssn) REFERENCES guest (guest_ssn)
);


CREATE TABLE PAYMENT(

	Payment_id INT PRIMARY KEY AUTO_INCREMENT,
  guest_ssn int,
  Booking_id int,
  Payment_way varchar(32),
  Payment_Day varchar (32),
  foreign key (Booking_id) references Booking (Booking_id),
  foreign key (guest_ssn) references Booking (guest_ssn)

);
-- DEFAULT INSERTIONS:

-- EMP TABLE:
INSERT INTO EMPLOYEE (employee_SSN, employee_first_Name, employee_father_Name, employee_family_Name,
                      employee_email, employee_nationality, salary, password)
VALUES (1, 'root', 'root', 'root', 'root@gmail.com', 'PS', 999.9, 'password');

INSERT INTO EMPLOYEE (employee_SSN, employee_first_Name, employee_father_Name, employee_family_Name,
                      employee_email, employee_nationality, salary, password)
VALUES (2, 'Faris', 'Amer', 'Abufarha', 'farisabufarha33@gmail.com', 'PS', 1299.9, 'faris');

INSERT INTO EMPLOYEE (employee_SSN, employee_first_Name, employee_father_Name, employee_family_Name,
                      employee_email, employee_nationality, salary, password)
VALUES (3, 'Saja', 'Musheer', 'Shareef', 'saja@gmail.com', 'PS', 999.9, 'saja');

INSERT INTO EMPLOYEE (employee_SSN, employee_first_Name, employee_father_Name, employee_family_Name,
                      employee_email, employee_nationality, salary, password)
VALUES (4, 'Shereen', 'Khaled', 'Ibdah', 'shereen@gmail.com', 'PS', 999.9, 'shereen');

INSERT INTO EMPLOYEE (employee_SSN, employee_first_Name, employee_father_Name, employee_family_Name,
                      employee_email, employee_nationality, salary, password)
VALUES (5, 'Hamza', 'hamza father', 'Awashra', 'hamza@gmail.com', 'PS', 999.9, 'hamza');


-- ROOM table

INSERT INTO room
VALUES (101, 99.9, 2, 'sweet', 'FREE');
INSERT INTO room
VALUES (102, 99.9, 2, 'sweet', 'FREE');
INSERT INTO room
VALUES (103, 99.9, 2, 'sweet', 'FREE');
INSERT INTO room
VALUES (104, 99.9, 2, 'sweet', 'FREE');

-- SECOND FLOOR

INSERT INTO room
VALUES (201, 199.9, 2, 'sweet', 'FREE');
INSERT INTO room
VALUES (202, 199.9, 2, 'sweet', 'FREE');
INSERT INTO room
VALUES (203, 199.9, 2, 'sweet', 'FREE');
INSERT INTO room
VALUES (204, 199.9, 2, 'sweet', 'FREE');

-- 3RD

INSERT INTO room
VALUES (301, 170.9, 1, 'single', 'FREE');
INSERT INTO room
VALUES (302, 170.9, 1, 'single', 'FREE');
INSERT INTO room
VALUES (303, 170.9, 1, 'single', 'FREE');
INSERT INTO room
VALUES (304, 170.9, 1, 'single', 'FREE');

-- PHONES
INSERT INTO employee_phone (eid, phone_num)
VALUES (1001, 0595110186);

INSERT INTO employee_phone (eid, phone_num)
VALUES (1001, 0569157426);


-- services GUESS SHOULD BE CHANGED

INSERT INTO services (service_type, service_cost)
VALUES ('Cleaning', 0);


-- 
