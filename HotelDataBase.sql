DROP DATABASE IF EXISTS HOTEL_COMP333;
CREATE DATABASE HOTEL_COMP333;
USE HOTEL_COMP333;

create table employee (

  eid int primary key AUTO_INCREMENT,
  employee_SSN int UNIQUE,
  employee_first_Name varchar(32),
  employee_father_Name varchar(32),
  employee_grandfather_Name varchar(32),
  employee_family_Name varchar(32),
  employee_email varchar(32),
  employee_nationality varchar(32)
  salary real

);

create table services(
  service_id int primary key AUTO_INCREMENT,
  service_type varchar(32),
  service_cost real DEFAULT 0.00,
  item_name varchar(32) -- ???
);

create table Guest(
  Guest_SSN int primary key,
  Guest_first_Name varchar(32),
  Guest_father_Name varchar(32),
  Guest_grandfather_Name varchar(32),
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
  FOREIGN KEY(eid) REFERENCES EMPLOYEE(eid) ON DELETE CASCADE
);


create table room (
  room_number int primary key,
  room_price real,
  number_of_beds INT,
  beds_type VARCHAR(32)
  room_status VARCHAR(32) -- OCCUPIED OR NOT
);


create table service_to_room( -- SAME AS 'PROVIDE'
	-- suggestion : provides service to a room not a guest
  room_id int,
  eid int,
  service_id int,
  primary key (room_id, eid, serviceid),
  foreign key (room_id) references room (room_id),
  foreign key (eid) references employee (eid),
  foreign key (service_id) references services(service_id)

);




create table Booking(
  Booking_id int primary key AUTO_INCREMENT,
  room_number INT,
  guest_ssn INT,
  start_date date,
  end_date date
  FOREIGN KEY(room_number) REFERENCES room (room_number)
  FOREIGN KEY(guest_ssn) REFERENCES room (guest_ssn)
);


CREATE TABLE PAYMENT(
	Payment_id INT PRIMARY KEY AUTO_INCREMENT,
  Payment_way varchar(32),
	Payment_Day varchar (32),

);


create table Pay_Payment (
  payment_id int primary key AUTO_INCREMENT,
  Payment_way varchar(32),
  Payment_Day varchar (32),
  Booking_id int,
  foreign key (Booking_id) references Booking (Booking_id)
);



-- 
