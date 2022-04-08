CREATE TABLE Error (
	Id INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(20) NOT NULL UNIQUE,
	Descr VARCHAR(1000) NOT NULL,
	AuthorId INT,
	ErrorDate Date NOT NULL,
	FOREIGN KEY (AuthorId) references Users(Id) ON DELETE SET NULL
);