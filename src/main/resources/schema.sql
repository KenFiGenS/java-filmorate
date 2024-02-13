CREATE schema "filmorate";

CREATE type "filmorate"."friendship_stutus" AS ENUM (
  'unconfirmed',
  'confirmed'
);

CREATE type "filmorate"."name" AS ENUM (
  '0+',
  '4+',
  '6+',
  '12+',
  '16+',
  '18+'
);

create  type "filmorate"."genre_name" AS ENUM (
  'dorama',
  'drama',
  'comedy',
  'parody',
  'action',
  'documentary',
  'military',
  'fantastic',
  'fantasy'
);

create  type "filmorate"."genre_id" AS ENUM (
  '1',
  '2',
  '3',
  '4',
  '5',
  '6',
  '7',
  '8',
  '9'
);

create  type "filmorate"."mpa_id" AS ENUM (
  '1',
  '2',
  '3',
  '4',
  '5',
  '6'
);

CREATE table if not exists "genre" (
  "genre_id" filmorate.genre_id PRIMARY key,
  "name" filmorate.genre_name
);

CREATE table if not exists "mpa" (
  "mpa_id" filmorate.mpa_id PRIMARY key,
  "name" filmorate.name
);

CREATE table if not exists "film" (
  "film_id" INTEGER GENERATED BY DEFAULT AS identity not null PRIMARY KEY,
  "name" varchar(55) not null,
  "description" varchar (255),
  "releaseDate" date not null,
  "duration" int not null,
  "genre_id" filmorate.genre_id references genre(genre_id) on delete cascade,
  "mpa_id" filmorate.mpa_id references mpa(mpa_id) on delete cascade
);

CREATE table if not exists "likes" (
  "film_id" int not null,
  "user_id" int not null,
  PRIMARY key ("film_id", "user_id")
);

CREATE table if not exists "user" (
  "user_id" INTEGER GENERATED BY DEFAULT AS identity not null PRIMARY KEY,
  "email" varchar (55) not null,
  "login" varchar (55) not null,
  "name" varchar (55) not null,
  "birthday" date not null
);

CREATE table if not exists "friendship_list" (
  "user1_id" int not null,
  "user2_id" int not null,
  "friendship_status" filmorate.friendship_stutus,
  PRIMARY key ("user1_id", "user2_id")
);

ALTER TABLE "likes" ADD FOREIGN KEY ("film_id") REFERENCES "film" ("film_id") on delete cascade;

ALTER TABLE "likes" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("user_id") on delete cascade;

ALTER TABLE "friendship_list" ADD FOREIGN KEY ("user1_id") REFERENCES "user" ("user_id") on delete cascade;

ALTER TABLE "friendship_list" ADD FOREIGN KEY ("user2_id") REFERENCES "user" ("user_id") on delete cascade;
