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

create table service(
  service_id int primary key AUTO_INCREMENT,
  service_type varchar(32),
  service_price real DEFAULT 0.00
  -- serving_date_time DATETIME DEFAULT NOW() WILL BE MOVED TO SERVICE_TO_ROOM TABLE
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
  isPaid bool DEFAULT(FALSE),
  service_date DATE DEFAULT ( current_date()),
  primary key (room_number, eid, service_id),
  foreign key (room_number) references room (room_number),
  foreign key (eid) references employee (eid),
  foreign key (service_id) references service(service_id)

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
Payment_way varchar(32),
Payment_Date DATE,
Guest_SSN int not null, 
amountPaid double,
foreign key (Guest_SSN) references Guest (Guest_SSN)
);
alter table booking auto_increment =1;
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
VALUES (1001, '0595110186');

INSERT INTO employee_phone (eid, phone_num)
VALUES (1001, '0569157426');


-- services GUESS SHOULD BE CHANGED

INSERT INTO service (service_type, service_price)
VALUES ('Cleaning', 0);

-- INSERTIONS TO SERVICE TO ROOM TABLE
INSERT INTO service_to_room (room_number, eid, service_id)
VALUES (101, 1000, 1);
insert INTO Guest (guest_ssn,Guest_first_Name,Guest_family_Name,Guest_father_Name,Guest_email,Guest_nationality,phone_num)
values (12345,'ramez','yazeed','ibdah','ahmad@gmail.com','PS','059927444'),
(1283,'amro','ahmad','saif','amro@gmail.com','PS','05983781344') ,
(1344,'narmeen','ibdah','ibdah','narmeen@gmail.com','PS','0259390233'),
(1256,'tala' , 'mosheer' , 'sharif', 'tala@outlook.com','ps','0596349522'),
(1239,'mohammed','sh','husni','moh@gmail.com','ps','0599999999'),
(1033,'ahmed','mohammed','hasan','ahmad@gmail.com','ps','0599999999');


insert into Booking (room_number,guest_ssn,Starting_date , end_date)
values (301,1256,'2023-1-1','2023-1-3'),
	   (303,1344,'2022-2-9','2022-2-19'),
	   (302,1239,'2023-1-1','2023-1-3'),
	   (301,1239,'2023-1-1','2023-1-3'),
       (202,1033,'2023-1-1','2023-1-3'),
	   (204,1344,'2023-1-1','2023-1-3')
       
;
select * from Guest;
select * from booking;
-- 
#Name  of all guest who book room number 301
select G.Guest_first_Name 
From guest G , booking B
where G.Guest_SSN = B.guest_ssn
 and B.room_number = 301;
-- 
select  b.Booking_id
from guest g , Booking b
where g.Guest_nationality = 'ps' and
g.Guest_SSN = b.Guest_SSN;
--
select  b.Booking_id
from guest g , Booking b
where g.Guest_first_Name = 'mohammed' and
g.Guest_SSN = b.Guest_SSN;
-- name of guest how reserved room 302 and 301
select  G.Guest_first_Name
from Guest G , booking B 
where G.Guest_SSN = B.guest_ssn 
and B.room_number = 302 and G.guest_ssn IN(
select  B.guest_ssn
from  booking B 
where  B.room_number = 301
);
#find name all guest who is room_type = "sweet"

select g.Guest_first_Name
from guest g ,Booking b , room r
where b.room_number = r.room_number and b.guest_ssn = g.guest_ssn
and  r.room_type = 'sweet';
-- the employee with max salary
select employee.employee_first_Name
from employee
where employee.salary  = (
select max(employee.salary)
from employee
);
