drop  DATABASE WebMovie;
CREATE DATABASE WebMovie;
use WebMovie;
 
CREATE TABLE User(
    Id int primary key AUTO_INCREMENT,
	Username varchar(10),
	Password varchar(10),
	Email varchar(50),
	IsAdmin bit,
	IsActive bit
);

CREATE TABLE Video(
    Id int primary key AUTO_INCREMENT,
	Tilte nvarchar(255),
	Href varchar(50),
	Poster varchar(255),
	Views int,
	Description nvarchar(255),
	Shares int,
	IsActive bit
);

CREATE TABLE Favorite(
	Id int primary key AUTO_INCREMENT,
	UserId int,
	VideoId int,
	ViewDate datetime,
	IsLiked bit,
	LikedDate datetime,
    FOREIGN KEY (UserId) REFERENCES User (Id),
    FOREIGN KEY (VideoId) REFERENCES Video (Id)
);
