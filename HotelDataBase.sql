drop database hotel;
create database hotel;
create table employee (
  eid int primary key,
  firstName varchar(32),
  lastName varchar(32),
  salary real
);
create table services(
  serviceid int primary key,
  sertype varchar(32),
  itemName varchar(32)
);
create table roomType(
  room_id int,
  bedNumber int,
  PersonNumber int,
  room_number int,
  primary key (room_id, room_number),
  foreign key (room_number) references Book_room (room_number) on delete cascade
);
create table Book_room (
  room_number int primary key,
  floorNumber int,
  room_price real,
  Booking_id int,
  foreign key (Booking_id) references Booking (Booking_id)
);
create table Booking(
  Booking_id int primary key,
  start_date date,
  end_date date
);
create table Pay_Payment (
  payment_id int primary key,
  Payment_way varchar(32),
  Payment_Day varchar (32),
  Booking_id int,
  foreign key (Booking_id) references Booking (Booking_id)
);
create table Guest(
  Guest_SSN int primary key,
  Guest_Name varchar(32),
  Guest_email varchar(32),
  payment_id int,
  Booking_id int,
  foreign key (Booking_id) references Booking(Booking_id),
  foreign key (payment_id) references pay_payment (payment_id)
);
create table provides(
  Guest_SSN int,
  eid int,
  serviceid int,
  primary key (Guest_SSN, eid, serviceid),
  foreign key (Guest_SSN) references guest (Guest_SSN),
  foreign key (eid) references employee (eid),
  foreign key (serviceid) references services(serviceid)
);
