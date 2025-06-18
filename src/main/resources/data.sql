insert into book_info (book_ISBN, book_language, book_name, book_author, book_publisher, book_pub_date, book_create_date, book_status, book_borrower_ID, borrow_date) values
 ('9789863988939', '1', '重啟人生：一個哈佛教授的生命領悟，給你把餘生過好的簡單建議', '亞瑟．布魯克斯', '天下雜誌', '2023-05-31', '2025-01-01',  '2', '00511111', DATEADD(DAY, -45, CURRENT_DATE)),
 ('9789576587399', '1', '蛤蟆先生去看心理師', '羅伯．狄保德', '三采', '2022-01-26', '2025-01-01',  '2', '00511111', DATEADD(DAY, -38, CURRENT_DATE)),
 ('9789861365640', '1', '發現你的天職：三大步驟，讓你選系、就業、轉職或創業不再迷惘', null, '如何', '2021-01-01','2025-01-01',  '2', '00511111', DATEADD(DAY, -12, CURRENT_DATE));


insert into borrow_log (action_date, borrow_action, borrow_book_isbn, borrower_id) values
(DATEADD(DAY, -45, CURRENT_DATE), '1', '9789863988939', '00511111'),
(DATEADD(DAY, -38, CURRENT_DATE), '1', '9789576587399', '00511111'),
(DATEADD(DAY, -12, CURRENT_DATE), '1', '9789861365640', '00511111');