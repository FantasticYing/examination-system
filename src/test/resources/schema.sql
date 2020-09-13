create table course
(
    id bigint auto_increment
        primary key,
    subject_id bigint not null,
    subject_name varchar(256) not null,
    subtitle varchar(512) default '' not null,
    creator_id bigint not null,
    creator_name varchar(256) default '' not null,
    start_time timestamp default CURRENT_TIMESTAMP not null,
    end_time timestamp not null,
    is_del int default 0 not null,
    created_time timestamp default CURRENT_TIMESTAMP not null,
    updated_time timestamp default CURRENT_TIMESTAMP not null
);

create table course_relation
(
    id bigint auto_increment
        primary key,
    course_id bigint not null,
    student_id bigint default 0 not null,
    teacher_id bigint default 0 not null,
    user_name varchar(256) default '' not null,
    is_del tinyint default 0 not null,
    constraint uni_course_user
        unique (course_id, student_id, teacher_id, user_name)
);

