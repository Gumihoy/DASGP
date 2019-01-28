CREATE TABLE `user` (
    `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(255) NOT NULL COMMENT '邮箱',
    `name` VARCHAR(255) NOT NULL COMMENT '用户名',
    `password` VARCHAR(80) NOT NULL COMMENT '密码',
    `gmt_create` DATETIME NOT NULL COMMENT '创建时间',
    `gmt_modify` DATETIME NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY uk_email(`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;