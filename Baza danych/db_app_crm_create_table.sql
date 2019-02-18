CREATE DATABASE db_app_crm;

CREATE TABLE account
(
	loginUser VARCHAR(50) NOT NULL PRIMARY KEY,
	passwordUser VARCHAR(50) NOT NULL,
	nameUser VARCHAR(50) NOT NULL,
	surnameUser VARCHAR(50) NOT NULL
);

CREATE TABLE client
(
	idClient INT(11) NOT NULL AUTO_INCREMENT,
    nameClient VARCHAR(50) NOT NULL,
	typeClient VARCHAR(20) NOT NULL CHECK(typeClient IN('perspektywa', 'klient', 'sprzedawca')),
	descriptionClient VARCHAR(255),
	loginUser VARCHAR(50) NOT NULL,
	
	FOREIGN KEY (loginUser) REFERENCES account (loginUser) ON DELETE CASCADE ON UPDATE CASCADE,
	
	PRIMARY KEY(idClient)
);

CREATE TABLE note
(
	idNote INT(11) NOT NULL AUTO_INCREMENT,
	titleNote VARCHAR(50) NOT NULL,
	descriptionNote VARCHAR(255) NOT NULL,
	idClient INT(11) NOT NULL,
	
	FOREIGN KEY (idClient) REFERENCES client (idClient) ON DELETE CASCADE ON UPDATE CASCADE,

	PRIMARY KEY(idNote)
);

CREATE TABLE task
(
	idTask INT(11) NOT NULL AUTO_INCREMENT,
	titleTask VARCHAR(50) NOT NULL,
	descriptionTask VARCHAR(255),
	flagTask CHAR(1) NOT NULL CHECK(flagTask IN('0', '1')),
	idClient INT(11) NOT NULL,
	
	FOREIGN KEY (idClient) REFERENCES client (idClient) ON DELETE CASCADE ON UPDATE CASCADE,
	
	PRIMARY KEY(idTask)
);

CREATE TABLE contact
(
	idContact INT(11) NOT NULL AUTO_INCREMENT,
	nameContact VARCHAR(50) NOT NULL,
	surnameContact VARCHAR(50) NOT NULL,
	phoneContact CHAR(9) NOT NULL,
	idClient INT(11) NOT NULL,
	
	FOREIGN KEY (idClient) REFERENCES client (idClient) ON DELETE CASCADE ON UPDATE CASCADE,
	
	PRIMARY KEY(idContact)
);

CREATE TABLE meet
(
	idMeet INT(11) NOT NULL AUTO_INCREMENT,
	dateMeet DATE NOT NULL,
	timeMeet TIME NOT NULL,
	locationMeet VARCHAR(255) NOT NULL,
	idClient INT(11) NOT NULL,
	
	FOREIGN KEY (idClient) REFERENCES client (idClient) ON DELETE CASCADE ON UPDATE CASCADE,
	
	PRIMARY KEY(idMeet)
);

CREATE TABLE activity
(
	idActivity INT(11) NOT NULL AUTO_INCREMENT,
	dateTimeActivity DATETIME NOT NULL,
	titleActivity VARCHAR(50) NOT NULL,
	typeActivity VARCHAR(50) NOT NULL CHECK(typeActivity IN('klient', 'notatka', 'zadanie', 'kontakt', 'spotkanie')),
	idClient INT(11) NOT NULL,
	
	FOREIGN KEY (idClient) REFERENCES client (idClient) ON DELETE CASCADE ON UPDATE CASCADE,
	
	PRIMARY KEY(idActivity)
);