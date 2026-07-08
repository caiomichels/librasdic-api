load data local infile 'BASE_PATH/data/hands.csv'
into table hand
fields terminated by ','
OPTIONALLY ENCLOSED BY '"'
lines terminated by '\n'
(@id_str, name, image_url)
SET id = CAST(NULLIF(REPLACE(TRIM(@id_str), '"',''), '') AS int);

load data local infile 'BASE_PATH/data/classes.csv'
into table gramatical_class
fields terminated by ','
OPTIONALLY ENCLOSED BY '"'
lines terminated by '\n'
(id, name);

load data local infile 'BASE_PATH/data/origins.csv'
into table origin
fields terminated by ','
OPTIONALLY ENCLOSED BY '"'
lines terminated by '\n'
(id, name);

load data local infile 'BASE_PATH/data/subjects.csv'
into table subject
fields terminated by ','
OPTIONALLY ENCLOSED BY '"'
lines terminated by '\n'
(@id_str, name)
SET id = CAST(NULLIF(REPLACE(TRIM(@id_str), '"',''), '') AS int);

load data local infile 'BASE_PATH/data/words.csv'
into table word
fields terminated by ','
OPTIONALLY ENCLOSED BY '"'
lines terminated by '\n'
(id, word, description, libras_example, example, subject_id, image_url, class_id, @skip_col9);

load data local infile 'BASE_PATH/data/signs.csv'
into table sign
fields terminated by ','
OPTIONALLY ENCLOSED BY '"'
lines terminated by '\n'
(word_id, @skip_col2, @skip_col3, @skip_col4, @skip_col5, @skip_col6, hand_id, video_url, @skip_col9, @skip_col10, origin_id, @skip_col12);

DELETE w
FROM word w
LEFT JOIN sign s ON s.word_id = w.id
WHERE s.word_id IS NULL;

UPDATE word w
JOIN (
  SELECT word_id, MIN(id) AS first_sign_id
  FROM sign
  WHERE word_id IS NOT NULL
  GROUP BY word_id
) s ON s.word_id = w.id
SET w.main_sign_id = s.first_sign_id;

update word w
set w.subject_id = 1
where
  w.subject_id <> 31 and w.subject_id <> 3 and w.subject_id <> 7 and w.subject_id <> 21 and w.subject_id <> 22 and w.subject_id <> 2 and w.subject_id <> 27 and w.subject_id <> 8 and w.subject_id <> 23 and w.subject_id <> 5 and w.subject_id <> 28 and w.subject_id <> 15 and w.subject_id <> 28 and w.subject_id <> 1 and w.subject_id <> 6 and w.subject_id <> 4 and w.subject_id <> 20 and w.subject_id <> 16 and w.subject_id <> 30 and w.subject_id <> 24 and w.subject_id <> 25
