INSERT INTO `user` VALUES
('user1', 'a722c63db8ec8625af6cf71cb8c2d939'),
('user2', 'c1572d05424d0ecb2a65ec6a82aeacbf'),
('user3', '3afc79b597f88a72528e864cf81856d2'),
('user4', 'fc2921d9057ac44e549efaf0048b2512'),
('user5', 'd35f6fa9a79434bcd17f8049714ebfcb');

INSERT INTO `event` VALUES
(null, 'Event 1', 'Description of this event 1', 'Foo street number 1', 20, '2016-10-1 14:00:00', '2016-10-1 14:30:00', 'user1'),
(null, 'Event 2', 'Description of this event 2', 'John Smith Av number 7', 10, '2016-09-20 16:00:00', '2016-09-20 18:00:00', 'user2'),
(null, 'Event 3', 'Description of this event 3', 'John Smith Av number 7', 10, '2016-06-10 16:00:00', '2016-06-10 18:00:00', 'user2'),
(null, 'Event 4', 'Description of this event 4', 'Bart Simpson Street 87', 17, '2016-01-10 12:00:00', '2016-01-10 13:30:00', 'user3'),
(null, 'Event 5', 'Description of this event 5', 'ESEI', 500, '2016-08-20 10:00:00', '2016-08-20 20:00:00', 'user3'),
(null, 'Event 6', 'Description of this event 6', 'White House', 5, '2017-06-10 16:00:00', '2017-06-10 18:00:00', 'user5'),
(null, 'Event 7', 'Description of this event 7', 'Boulevard du Clichy', 10, '2016-07-10 16:00:00', '2016-07-10 18:00:00', 'user5'),
(null, 'Event 8', 'Description of this event 8', 'Bar Ace of Spades', 30, '2016-06-10 16:00:00', '2016-06-10 18:00:00', 'user5'),
(null, 'Event 9', 'Description of this event 9', 'Markus Phoenix Plaza', 70, '2016-12-31 22:00:00', '2017-12-31 01:00:00', 'user5'),
(null, 'Event 10', 'Description of this event 10', 'Doomsday Park', 10, '2016-06-18 16:00:00', '2016-06-18 18:00:00', 'user5');

INSERT INTO `assists` VALUES
('user1',2),
('user1',3),
('user1',5),
('user2',1),
('user2',4),
('user2',5),
('user2',10),
('user4',5),
('user5',2),
('user5',5);