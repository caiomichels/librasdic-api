create database if not exists librasdic;
use librasdic;

create table if not exists gramatical_class (
  id int primary key auto_increment,
  name varchar(255) unique
);

create table if not exists origin (
  id int primary key auto_increment,
  name varchar(255) unique
);

create table if not exists hand (
  id int primary key auto_increment,
  name varchar(255) unique,
  image_url varchar(255)
);

create table if not exists subject (
  id int primary key auto_increment,
  name varchar(255) unique,
  description text
);

create table if not exists variation (
  id int primary key auto_increment,
  name varchar(255) unique,
  description text not null
);

create table if not exists word (
  id int primary key auto_increment,
  word varchar(255) unique,
  description text not null,
  example text not null,
  libras_example text not null,
  image_url varchar(255) not null,
  main_sign_id int,
  subject_id int not null,
  constraint `fk_word_subject` foreign key (subject_id) references subject (id),
  class_id int not null,
  constraint `fk_word_class` foreign key (class_id) references gramatical_class (id)
);

create table if not exists sign (
  id int primary key auto_increment,
  video_url varchar(255) not null,
  variation_id int,

  articulation_point varchar(255),
  hand_configuration varchar(255),
  contact_region varchar(255),

  other_description text,
  type enum("one_hand", "two_different", "two_same", "face"),

  constraint `fk_sign_variation` foreign key (variation_id) references variation (id),
  word_id int not null,
  constraint `fk_sign_word` foreign key (word_id) references word (id),
  hand_id int not null,
  constraint `fk_sign_hand` foreign key (hand_id) references hand (id),
  origin_id int not null,
  constraint `fk_sign_origin` foreign key (origin_id) references origin (id)
);

alter table word add constraint `fk_word_main_sign` foreign key (main_sign_id) references sign (id);
