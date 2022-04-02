create schema if not exists tinder_db;

create table profile
(
    id          int8 not null,
    description varchar(255),
    name        varchar(255),
    sex_type    int4,
    primary key (id)
);
create table profile_likes
(
    to_profile   int8 not null,
    from_profile int8 not null,
    primary key (from_profile, to_profile)
);
create table sex_type
(
    profile_id  int8 not null,
    looking_for int4
);
create table usr
(
    id         int8 not null,
    password   varchar(255),
    profile_id int8,
    primary key (id)
);

create sequence if not exists hibernate_sequence start 12 increment 1;

alter table if exists profile_likes
    add constraint FK_FROM_PROFILE_TO_PROFILE foreign key (from_profile) references profile;
alter table if exists profile_likes
    add constraint FK_TO_PROFILE_PROFILE foreign key (to_profile) references profile;
alter table if exists sex_type
    add constraint FK_PROFILE_ID_PROFILE foreign key (profile_id) references profile;
alter table if exists usr
    add constraint FK_PROFILE_ID_PROFILE foreign key (profile_id) references profile;