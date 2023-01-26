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
INSERT INTO EMPLOYEE VALUES(1,1,"root",'root','root','root@gmail.com','PS',CURRENT_DATE(), 999.9,'password');

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
-- 
