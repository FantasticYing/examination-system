CREATE DATABASE `examination_system_test` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */

CREATE TABLE `core_user` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT,
                             `username` varchar(256) NOT NULL,
                             `nickname` varchar(256) NOT NULL DEFAULT '',
                             `email` varchar(256) DEFAULT NULL,
                             `mobile` varchar(16) DEFAULT NULL,
                             `password` varchar(256) NOT NULL,
                             `role_id` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1-学生,2-老师,0-管理员',
                             `is_del` tinyint(4) NOT NULL DEFAULT '0',
                             `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `core_user_username_uindex` (`username`),
                             UNIQUE KEY `core_user_email_uindex` (`email`),
                             UNIQUE KEY `core_user_mobile_uindex` (`mobile`),
                             KEY `idx_uname_pswd_del_id` (`username`,`password`,`is_del`,`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `core_user` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT,
                             `username` varchar(256) NOT NULL,
                             `nickname` varchar(256) NOT NULL DEFAULT '',
                             `email` varchar(256) DEFAULT NULL,
                             `mobile` varchar(16) DEFAULT NULL,
                             `password` varchar(256) NOT NULL,
                             `role_id` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1-学生,2-老师,0-管理员',
                             `is_del` tinyint(4) NOT NULL DEFAULT '0',
                             `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `core_user_username_uindex` (`username`),
                             UNIQUE KEY `core_user_email_uindex` (`email`),
                             UNIQUE KEY `core_user_mobile_uindex` (`mobile`),
                             KEY `idx_uname_pswd_del_id` (`username`,`password`,`is_del`,`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `course_relation` (
                                   `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                   `course_id` bigint(20) NOT NULL,
                                   `student_id` bigint(20) NOT NULL DEFAULT '0',
                                   `teacher_id` bigint(20) NOT NULL DEFAULT '0',
                                   `user_name` varchar(256) NOT NULL DEFAULT '',
                                   `is_del` tinyint(4) NOT NULL DEFAULT '0',
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `uni_course_user` (`course_id`,`student_id`,`teacher_id`,`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=302 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `exam_plan` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT,
                             `subject_id` bigint(20) NOT NULL,
                             `subject_name` varchar(256) NOT NULL,
                             `course_id` bigint(20) NOT NULL,
                             `paper_id` bigint(20) NOT NULL,
                             `paper_title` varchar(512) NOT NULL,
                             `full_score` int(11) NOT NULL,
                             `teacher_id` bigint(20) NOT NULL,
                             `teacher_name` varchar(256) NOT NULL DEFAULT '',
                             `start_time` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
                             `end_time` timestamp NOT NULL,
                             `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             `is_del` tinyint(4) NOT NULL DEFAULT '0',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `exam_record` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT,
                               `exam_plan_id` bigint(20) NOT NULL,
                               `paper_id` bigint(20) NOT NULL,
                               `student_id` bigint(20) NOT NULL,
                               `student_name` varchar(256) NOT NULL DEFAULT '',
                               `fact_score` int(11) NOT NULL DEFAULT '0',
                               `content_json` longtext,
                               `release_status_id` tinyint(4) NOT NULL DEFAULT '1' COMMENT '''0-未答卷，1-已开考，开始计时，2-已交卷，等待判卷，4-完成;',
                               `fact_score_detail_json` varchar(1024) NOT NULL DEFAULT '{}',
                               `is_del` tinyint(4) NOT NULL DEFAULT '0',
                               `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `udx_plan_id_student_id` (`exam_plan_id`,`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `paper` (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `subject_id` int(11) NOT NULL,
                         `subject_name` varchar(256) NOT NULL,
                         `title` varchar(512) NOT NULL,
                         `teacher_id` bigint(20) NOT NULL,
                         `teacher_name` varchar(256) NOT NULL DEFAULT '',
                         `score` int(11) NOT NULL,
                         `duration` int(11) NOT NULL,
                         `context_json` longtext NOT NULL,
                         `is_del` tinyint(4) NOT NULL DEFAULT '0',
                         `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `question` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `subject_id` bigint(20) NOT NULL,
                            `subject_name` varchar(256) NOT NULL DEFAULT '',
                            `creator_id` bigint(20) NOT NULL,
                            `creator_name` varchar(256) NOT NULL DEFAULT '',
                            `type_id` int(11) NOT NULL DEFAULT '0' COMMENT '0-未分类;1-单选;2-多选;3-判断;4:简答',
                            `description_json` mediumtext NOT NULL,
                            `answer_json` mediumtext NOT NULL,
                            `is_del` tinyint(4) NOT NULL DEFAULT '0',
                            `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `subject` (
                           `id` int(11) NOT NULL AUTO_INCREMENT,
                           `name` varchar(256) NOT NULL,
                           `creator_id` bigint(20) NOT NULL,
                           `creator_name` varchar(256) NOT NULL DEFAULT '',
                           `is_del` tinyint(4) NOT NULL DEFAULT '0',
                           `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `subject_name_uindex` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=195 DEFAULT CHARSET=utf8mb4;

