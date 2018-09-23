-- Init data for testing

-- table Location
insert into Location (id, unlocode, name) values (1, "SESTO", "Stockholm");
insert into Location (id, unlocode, name) values (2, "AUMEL", "Melbourne");
insert into Location (id, unlocode, name) values (3, "CNHKG", "Hongkong");
insert into Location (id, unlocode, name) values (4, "JPTOK", "Tokyo");
insert into Location (id, unlocode, name) values (5, "FIHEL", "Helsinki");
insert into Location (id, unlocode, name) values (6, "DEHAM", "Hamburg");
insert into Location (id, unlocode, name) values (7, "USCHI", "Chicago");

-- table Voyage
insert into Voyage (id, voyage_number) values (1, "0101");
insert into Voyage (id, voyage_number) values (2, "0202");
insert into Voyage (id, voyage_number) values (3, "0303");

-- table CarrierMovement
insert into CarrierMovement (id, voyage_id, departure_location_id, arrival_location_id, departure_time, arrival_time, cm_index) values (1, 1, 1, 5, '', '', 0);
