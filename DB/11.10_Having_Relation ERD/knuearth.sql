drop database knuearth;
create database knuearth;
use knuearth;

DROP TABLE Member;
DROP TABLE Flea_Market;
DROP TABLE Bulletin_Board;
DROP TABLE Massage;
DROP TABLE Notification;

SET foreign_key_checks=0;

set character set euckr;

CREATE TABLE Member(
	id		VARCHAR(20)		NOT NULL,
	Name		VARCHAR(20)		NOT NULL,
	Student_id	VARCHAR(20)		NOT NULL,
	Major		VARCHAR(20)		NOT NULL,
	PRIMARY KEY (id)
)default charset=utf8 collate utf8_general_ci;

CREATE TABLE Flea_Market(
	Category		VARCHAR(20)		NOT NULL,
	Posting_Number	INTEGER			NOT NULL,	
	Writer_ID		VARCHAR(20)		NOT NULL,
	Title		VARCHAR(20)		NOT NULL,
	Content		VARCHAR(300)		NOT NULL,
	Foreword		VARCHAR(10)		NOT NULL,
	Date		DATETIME		NOT NULL,
	Comment	VARCHAR(50),
	PRIMARY KEY(Posting_Number)
)default charset=utf8 collate utf8_general_ci;

CREATE TABLE Bulletin_Board(
	Posting_Number	INTEGER			NOT NULL,
	Foreword		VARCHAR(10)		NOT NULL,
	Writer_ID		VARCHAR(20)		NOT NULL,
	Title		VARCHAR(20)		NOT NULL,
	Content		VARCHAR(300)		NOT NULL,
	Comment	VARCHAR(50),
	Date		DATETIME		NOT NULL,
	PRIMARY KEY(Posting_Number)
)default charset=utf8 collate utf8_general_ci;

CREATE TABLE Massage(
	Writer_ID		VARCHAR(20)		NOT NULL,
	Receiver_ID	VARCHAR(20)		NOT NULL,
	Content		VARCHAR(100)		NOT NULL,
	Massage_Number	INTEGER			NOT NULL,
	Date		DATETIME		NOT NULL,
	PRIMARY KEY(Massage_Number)
)default charset=utf8 collate utf8_general_ci;

CREATE TABLE Notification(
	Notice_Number	INTEGER			NOT NULL,
	Title		VARCHAR(20)		NOT NULL,
	Content		VARCHAR(100)		NOT NULL,
	Date		DATETIME		NOT NULL,
	Receiver_ID	VARCHAR(20)		NOT NULL,
	PRIMARY KEY(Notice_Number)
)default charset=utf8 collate utf8_general_ci;

ALTER TABLE Flea_Market ADD CONSTRAINT fk_Flea_Mem FOREIGN KEY(Writer_ID) REFERENCES Member(id);
ALTER TABLE Bulletin_Board ADD CONSTRAINT fk_Bul_Mem FOREIGN KEY(Writer_ID) REFERENCES Member(id);
ALTER TABLE Massage ADD CONSTRAINT fk_Mas_Mem1 FOREIGN KEY(Writer_ID) REFERENCES Member(id);
ALTER TABLE Massage ADD CONSTRAINT fk_Mas_Mem2 FOREIGN KEY(Receiver_ID) REFERENCES Member(id);
ALTER TABLE Notification ADD CONSTRAINT fk_Not_Mem FOREIGN KEY(Receiver_ID) REFERENCES Member(id);

insert into Member(id,Name,Student_Id,Major) values("makeastir","Hwang Kyu Min","2016118035","CSE");
insert into Flea_Market(Category,Posting_Number,Writer_ID,Title,Content,Foreword,Date) values("Electronic",1,"makeastir","Galaxy S8","Sell : Galaxy S8, Cost : \600,000","Sell",'2018-11-10');




