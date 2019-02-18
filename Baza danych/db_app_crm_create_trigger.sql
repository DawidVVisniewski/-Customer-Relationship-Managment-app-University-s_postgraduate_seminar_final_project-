CREATE TRIGGER addClientActivity
AFTER INSERT ON client
FOR EACH ROW 
BEGIN
	set @dateTimeActivity = NOW();
	set @titleActivity = 'Dodano nowego klienta';
	set @typeActivity = 'klient';
	set @idClient = new.idClient;

	INSERT INTO activity (dateTimeActivity, titleActivity, typeActivity, idClient) VALUES(@dateTimeActivity, @titleActivity , @typeActivity, @idClient);
END;

CREATE TRIGGER addNoteActivity
AFTER INSERT ON note
FOR EACH ROW 
BEGIN
	set @dateTimeActivity = NOW();
	set @titleActivity = 'Dodano nową notatke';
	set @typeActivity = 'notatka';
	set @idClient = new.idClient;

	INSERT INTO activity (dateTimeActivity, titleActivity, typeActivity, idClient) VALUES(@dateTimeActivity, @titleActivity , @typeActivity, @idClient);
END;

CREATE TRIGGER addTaskActivity
AFTER INSERT ON task
FOR EACH ROW 
BEGIN
	set @dateTimeActivity = NOW();
	set @titleActivity = 'Dodano nowe zadanie';
	set @typeActivity = 'zadanie';
	set @idClient = new.idClient;

	INSERT INTO activity (dateTimeActivity, titleActivity, typeActivity, idClient) VALUES(@dateTimeActivity, @titleActivity , @typeActivity, @idClient);
END;

CREATE TRIGGER addContactActivity
AFTER INSERT ON contact
FOR EACH ROW 
BEGIN
	set @dateTimeActivity = NOW();
	set @titleActivity = 'Dodano nową osobe do kontaktu';
	set @typeActivity = 'kontakt';
	set @idClient = new.idClient;

	INSERT INTO activity (dateTimeActivity, titleActivity, typeActivity, idClient) VALUES(@dateTimeActivity, @titleActivity , @typeActivity, @idClient);
END;

CREATE TRIGGER addMeetActivity
AFTER INSERT ON meet
FOR EACH ROW 
BEGIN
	set @dateTimeActivity = NOW();
	set @titleActivity = 'Dodano nowy termin spotkania';
	set @typeActivity = 'spotkanie';
	set @idClient = new.idClient;

	INSERT INTO activity (dateTimeActivity, titleActivity, typeActivity, idClient) VALUES(@dateTimeActivity, @titleActivity , @typeActivity, @idClient);
END;