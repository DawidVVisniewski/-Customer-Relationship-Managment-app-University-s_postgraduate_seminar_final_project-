INSERT INTO account (loginUser, passwordUser, nameUser, surnameUser) VALUES('tester@gmail.com', 'zaq12wsx', 'Jan', 'Kowalski');

INSERT INTO client (nameClient, typeClient, descriptionClient, loginUser) VALUES('Atos', 'sprzedawca', 'Technologia', 'tester@gmail.com');
INSERT INTO client (nameClient, typeClient, descriptionClient, loginUser) VALUES('Google', 'sprzedawca', 'Technologia', 'tester@gmail.com');
INSERT INTO client (nameClient, typeClient, descriptionClient, loginUser) VALUES('Apple', 'sprzedawca', 'Technologia', 'tester@gmail.com');
INSERT INTO client (nameClient, typeClient, descriptionClient, loginUser) VALUES('Infocomp', 'klient', 'Technologia', 'tester@gmail.com');
INSERT INTO client (nameClient, typeClient, descriptionClient, loginUser) VALUES('Euro Bank SA', 'perspektywa', 'Usługi finansowe', 'tester@gmail.com');
INSERT INTO client (nameClient, typeClient, descriptionClient, loginUser) VALUES('Alior Bank SA', 'perspektywa', 'Usługi finansowe', 'tester@gmail.com');
INSERT INTO client (nameClient, typeClient, descriptionClient, loginUser) VALUES('Wielton', 'klient', 'Produkcja', 'tester@gmail.com');
INSERT INTO client (nameClient, typeClient, descriptionClient, loginUser) VALUES('Neapco', 'klient', 'Produkcja', 'tester@gmail.com');
INSERT INTO client (nameClient, typeClient, descriptionClient, loginUser) VALUES('PGE', 'klient', 'Energia', 'tester@gmail.com');
INSERT INTO client (nameClient, typeClient, descriptionClient, loginUser) VALUES('Tauron', 'klient', 'Energia', 'tester@gmail.com');

INSERT INTO note (titleNote, descriptionNote, idClient) VALUES('Integracja systemu', 'Propozycja uruchomienia', 4);
INSERT INTO note (titleNote, descriptionNote, idClient) VALUES('Uzupełnienie kontraktu', 'Specjelne notatki sekcji', 7);
INSERT INTO note (titleNote, descriptionNote, idClient) VALUES('Płatność', 'Informacje o rachunkowości dotyczące płatności za połączenia telefoniczne', 5);
INSERT INTO note (titleNote, descriptionNote, idClient) VALUES('Uzupełnienie kontraktu', 'Specjelne notatki sekcji', 4);
INSERT INTO note (titleNote, descriptionNote, idClient) VALUES('Płatność', 'Informacje o rachunkowości dotyczące płatności za połączenia telefoniczne', 4);

INSERT INTO task (titleTask, descriptionTask, idClient) VALUES('Wyślij katalog produktów', null, 1);
INSERT INTO task (titleTask, descriptionTask, idClient) VALUES('Zbudować prototyp', null, 1);
INSERT INTO task (titleTask, descriptionTask, idClient) VALUES('Złożyć propozycje', null, 4);
INSERT INTO task (titleTask, descriptionTask, idClient) VALUES('Wyślij katalog produktów', null, 2);
INSERT INTO task (titleTask, descriptionTask, idClient) VALUES('Wyślij katalog produktów', null, 3);
INSERT INTO task (titleTask, descriptionTask, idClient) VALUES('Wyślij katalog produktów', null, 4);
INSERT INTO task (titleTask, descriptionTask, idClient) VALUES('Zbudować prototyp', null, 7);

INSERT INTO contact (nameContact, surnameContact, phoneContact, idClient) VALUES('Piotr', 'Wiśniewski', '725965423', 4);
INSERT INTO contact (nameContact, surnameContact, phoneContact, idClient) VALUES('Julia', 'Kowalczyk', '698532456', 4);
INSERT INTO contact (nameContact, surnameContact, phoneContact, idClient) VALUES('Agnieszka', 'Nowakowska', '520366002', 4);
INSERT INTO contact (nameContact, surnameContact, phoneContact, idClient) VALUES('Antoni', 'Krawczyk', '669524312', 1);
INSERT INTO contact (nameContact, surnameContact, phoneContact, idClient) VALUES('Ludwik', 'Mazur', '500265433', 2);
INSERT INTO contact (nameContact, surnameContact, phoneContact, idClient) VALUES('Zbigniew', 'Kwiatkowski', '859652348', 3);
INSERT INTO contact (nameContact, surnameContact, phoneContact, idClient) VALUES('Patryk', 'Piotrowski', '704589666', 5);
INSERT INTO contact (nameContact, surnameContact, phoneContact, idClient) VALUES('Anna', 'Kaczmarek', '606320002', 6);
INSERT INTO contact (nameContact, surnameContact, phoneContact, idClient) VALUES('Tomasz', 'Wieczorek', '505665223', 7);

INSERT INTO meet (dateMeet, timeMeet, locationMeet, idClient) VALUES('2017-09-12', '14:00:00', 'Wrocław, Galeria Dominikańska', 4);
INSERT INTO meet (dateMeet, timeMeet, locationMeet, idClient) VALUES('2017-09-20', '11:15:00', 'Wrocław, Centrum Handlowe', 1);
INSERT INTO meet (dateMeet, timeMeet, locationMeet, idClient) VALUES('2017-10-11', '12:30:00', 'Kraków, Rynek', 2);
INSERT INTO meet (dateMeet, timeMeet, locationMeet, idClient) VALUES('2017-09-20', '15:15:00', 'Wrocław, Pasaż Grunwaldzki', 4);
INSERT INTO meet (dateMeet, timeMeet, locationMeet, idClient) VALUES('2017-11-08', '16:45:00', 'Poznań, Dworzec PKP', 5);