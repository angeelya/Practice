use university;
create table student(
                        id bigint primary key auto_increment,
                        name varchar(250),
                        last_name varchar(250),
                        group_id bigint);
create table teacher(
                        id bigint primary key auto_increment,
                        name varchar(250),
                        last_name varchar(250),
                        discipline_id bigint);
create table group_university(
                                 id bigint primary key auto_increment,
                                 name varchar(250) unique);
create table discipline(
                           id bigint primary key auto_increment,
                           name varchar(250) unique);
create table teaching(
                         id bigint primary key auto_increment,
                         group_id bigint,
                         teacher_id bigint);

ALTER TABLE student
    ADD FOREIGN KEY (group_id) REFERENCES group_university(id) on delete cascade on update cascade;
ALTER TABLE teacher
    ADD FOREIGN KEY (discipline_id) REFERENCES discipline(id) on delete cascade on update cascade;
ALTER TABLE teaching
    ADD FOREIGN KEY (group_id) REFERENCES  group_university(id) on delete cascade on update cascade;
ALTER TABLE teaching
    ADD FOREIGN KEY (teacher_id) REFERENCES  teacher(id) on delete cascade on update cascade;
INSERT INTO group_university (name) VALUES
                                        ('M-12'),
                                        ('E-2'),
                                        ('JK-21');
INSERT INTO student (name, last_name, group_id) VALUES
                                                    ('Ivan', 'Petrov', 1),
                                                    ('Maria', 'Ivanova', 2),
                                                    ('Alexey', 'Sidorov', 1),
                                                    ('Olga', 'Smirnova', 3),
                                                    ('Dmitriy', 'Sokolov', 2);
INSERT INTO discipline (name) VALUES
                                  ('Mathematics'),
                                  ('Physics'),
                                  ('Chemistry'),
                                  ('History'),
                                  ('Literature'),
                                  ('Computer Science'),
                                  ('English Language'),
                                  ('Russian Language');
INSERT INTO teacher (name, last_name, discipline_id) VALUES
                                                         ('Ilya', 'Petrov', 1),
                                                         ('Maria', 'Ulianova', 2),
                                                         ('Danila', 'Dorbry', 3),
                                                         ('Elizaveta', 'Smirnova', 4),
                                                         ('Alexander', 'Ivanov', 5),
                                                         ('Alisa','Evtushenko',6),
                                                         ('Anna', 'Smith', 7),('Daria', 'Lim', 1);
INSERT INTO teaching (group_id, teacher_id) VALUES
                                                (1, 8),
                                                (1, 2),
                                                (3, 1),
                                                (2, 6);