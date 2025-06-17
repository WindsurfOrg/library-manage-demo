insert into book_info (book_ISBN, book_language, book_name, book_author, book_publisher, book_pub_date, book_create_date, book_status) values
 ('9789863988939', '1', '重啟人生：一個哈佛教授的生命領悟，給你把餘生過好的簡單建議', '亞瑟．布魯克斯', '天下雜誌', '2023-05-31', now(),  '1'),
 ('9789576587399', '1', '蛤蟆先生去看心理師', '羅伯．狄保德', '三采', '2022-01-26', now(),  '1'),
 ('9789861365640', '1', '發現你的天職：三大步驟，讓你選系、就業、轉職或創業不再迷惘', null, '如何', '2021-01-01', now(),  '3');


insert into borrow_log (action_date, borrow_action, borrow_book_isbn, borrower_ID) values
('2024-05-01', '1', '9789863988939', '00597825');

update book_info set book_status = '2', borrow_date = '2024-05-01' where book_ISBN = '9789863988939';