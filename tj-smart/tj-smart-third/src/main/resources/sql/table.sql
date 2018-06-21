CREATE TABLE `third_sms_log` (
  `id` bigint(20) NOT NULL auto_increment,
  `serial_number` varchar(20) NOT NULL COMMENT '手机号码',
  `create_by` bigint(20) DEFAULT NULL COMMENT '用户id',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `sms_content` text NOT NULL COMMENT '发送内容',
  PRIMARY KEY  (`id`),
  KEY `index_serial_number` (`serial_number`),
  KEY `index_create_by` (`create_by`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



