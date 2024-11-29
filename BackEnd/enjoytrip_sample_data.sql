USE enjoytrip_6;

-- Insert sample data into 'sido' table
INSERT INTO `sido` (`sido_code`, `sido_name`) VALUES
(1, 'Seoul'),
(2, 'Busan');

-- Insert sample data into 'gugun' table
INSERT INTO `gugun` (`gugun_code`, `gugun_name`, `sido_code`) VALUES
(1, 'Gangnam-gu', 1),
(2, 'Jung-gu', 1),
(3, 'Haeundae-gu', 2),
(4, 'Suyeong-gu', 2);

-- Insert sample data into 'content_type' table
INSERT INTO `content_type` (`content_type_id`, `type_name`) VALUES
(1, 'Museum'),
(2, 'Park');

-- Insert sample data into 'group' table
INSERT INTO `group` (`groupId`, `groupName`) VALUES
(1, 'Group One'),
(2, 'Group Two'),
(3, 'Group Three');


-- Insert sample data into 'user' table
INSERT INTO `user` (`userId`, `name`, `password`, `isAdmin`, `email`, `address`, `token`) VALUES
('user1', 'User One', 'password1', 0, 'user1@example.com', 'Seoul', NULL),
('user2', 'User Two', 'password2', 0, 'user2@example.com', 'Seoul', NULL),
('user3', 'User Three', 'password3', 0, 'user3@example.com', 'Busan', NULL),
('user4', 'User Four', 'password4', 0, 'user4@example.com', 'Busan', NULL),
('user5', 'User Five', 'password5', 0, 'user5@example.com', 'Seoul', NULL),
('user6', 'User Six', 'password6', 0, 'user6@example.com', 'Busan', NULL),
('user7', 'User Seven', 'password7', 0, 'user7@example.com', 'Seoul', NULL),
('user8', 'User Eight', 'password8', 0, 'user8@example.com', 'Busan', NULL),
('user9', 'User Nine', 'password9', 0, 'user9@example.com', 'Seoul', NULL),
('user10', 'User Ten', 'password10', 0, 'user10@example.com', 'Busan', NULL);

-- Insert sample data into 'usergrouprelationship' table
INSERT INTO `usergrouprelationship` (`userId`, `groupId`) VALUES
('user1', 1),
('user2', 1),
('user3', 1),
('user1', 3),
('user2', 3),
('user1', 2),
('user6', 2),
('user7', 2),
('user8', 2),
('user9', 2),
('user10', 2);

-- Insert sample data into 'tour' table
INSERT INTO `tour` (`tourId`, `groupId`, `userId`) VALUES
(1, 1, null),
(2, 1, null), 
(3, 2, null), 
(4, null, 'user1'), 
(5, null, 'user1'),
(6, null, 'user1'),
(7, null, 'user1');


-- Insert sample data into 'checklist' table
INSERT INTO `checklist` (`checkListId`, `tourId`, `isGroupCheckList`) VALUES
(1, 1, 1),
(2, 2, 1);

-- Insert sample data into 'checklistitems' table
INSERT INTO `checklistitems` (`checkListItemId`, `checkListId`, `isChecked`) VALUES
(1, 1, 0),
(2, 1, 1),
(3, 1, 0),
(4, 2, 1),
(5, 2, 0),
(6, 2, 1),
(7, 1, 0),
(8, 1, 1),
(9, 2, 0),
(10, 2, 1);

-- Insert sample data into 'post' table
INSERT INTO `post` (`content`, `registerTime`, `groupId`, `userId`, `title`, `hit`) VALUES
('Content 1', NOW(), 1, 'user1', 'Title 1', 10),
('Content 2', NOW(), 1, 'user2', 'Title 2', 20),
('Content 3', NOW(), 1, 'user3', 'Title 3', 30),
('Content 4', NOW(), 1, 'user4', 'Title 4', 40),
('Content 5', NOW(), 1, 'user5', 'Title 5', 50),
('Content 6', NOW(), 2, 'user6', 'Title 6', 60),
('Content 7', NOW(), 2, 'user7', 'Title 7', 70),
('Content 8', NOW(), 2, 'user8', 'Title 8', 80),
('Content 9', NOW(), 2, 'user9', 'Title 9', 90),
('Content 10', NOW(), 2, 'user10', 'Title 10', 100);

-- Insert sample data into 'comments' table
INSERT INTO `comments` (`comment`, `registerTime`, `postId`, `userId`) VALUES
('Comment 1', NOW(), 20, 'user1'),
('Comment 2', NOW(), 21, 'user2'),
('Comment 3', NOW(), 22, 'user3'),
('Comment 4', NOW(), 23, 'user4'),
('Comment 5', NOW(), 24, 'user5'),
('Comment 6', NOW(), 25, 'user6'),
('Comment 7', NOW(), 26, 'user7'),
('Comment 8', NOW(), 27, 'user8'),
('Comment 9', NOW(), 28, 'user9'),
('Comment 10', NOW(), 29, 'user10');

-- Insert sample data into 'attraction_info' table
INSERT INTO `attraction_info` (`content_id`, `content_type_id`, `title`, `addr1`, `addr2`, `zipcode`, `tel`, `first_image`, `first_image2`, `readcount`, `sido_code`, `gugun_code`, `latitude`, `longitude`, `mlevel`) VALUES
(1, 1, 'Attraction 1', 'Address 1', 'Addr2 1', 'Zip1', 'Tel1', 'Image1', 'Image2_1', 100, 1, 1, 37.5665, 126.9780, 'A'),
(2, 1, 'Attraction 2', 'Address 2', 'Addr2 2', 'Zip2', 'Tel2', 'Image2', 'Image2_2', 200, 1, 2, 37.5678, 126.9790, 'B'),
(3, 2, 'Attraction 3', 'Address 3', 'Addr2 3', 'Zip3', 'Tel3', 'Image3', 'Image2_3', 300, 2, 3, 35.1587, 129.1603, 'C'),
(4, 2, 'Attraction 4', 'Address 4', 'Addr2 4', 'Zip4', 'Tel4', 'Image4', 'Image2_4', 400, 2, 4, 35.1598, 129.1614, 'D');

-- Insert sample data into 'attraction_detail' table
INSERT INTO `attraction_detail` (`content_id`, `cat1`, `cat2`, `cat3`, `created_time`, `modified_time`, `booktour`) VALUES
(1, 'A01', 'A0101', 'A01010100', '20220101120000', '20230101120000', 'Y'),
(2, 'A02', 'A0202', 'A02020200', '20220201120000', '20230201120000', 'N'),
(3, 'B01', 'B0101', 'B01010100', '20220301120000', '20230301120000', 'Y'),
(4, 'B02', 'B0202', 'B02020200', '20220401120000', '20230401120000', 'N');

-- Insert sample data into 'attraction_description' table
INSERT INTO `attraction_description` (`content_id`, `homepage`, `overview`, `telname`) VALUES
(1, 'http://attraction1.com', 'Overview 1', 'Telname 1'),
(2, 'http://attraction2.com', 'Overview 2', 'Telname 2'),
(3, 'http://attraction3.com', 'Overview 3', 'Telname 3'),
(4, 'http://attraction4.com', 'Overview 4', 'Telname 4');



-- Insert sample data into 'tourplan' table
INSERT INTO `tourPlan` (`day`, `order`, `tourId`, `content_id`) VALUES
(1, 1, 4, 1),
(1, 2, 4, 2);

