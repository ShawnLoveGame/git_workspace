alter table bottom_ma_user add bind_time datetime DEFAULT NULL COMMENT '绑定时间';

update bottom_ma_user set bind_time = create_time;
commit;


CREATE TABLE `bottom_activity_user_rel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `parent_user_id` bigint(20) NOT NULL COMMENT '上级用户id',
  `status` int(2) NOT NULL COMMENT '状态 0：待生效  1：已生效 2：已失效',
  `bind_time` datetime NOT NULL COMMENT '绑定时间',
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`user_id`),
  KEY `index_parent_user_id` (`parent_user_id`),
  KEY `index_bind_time` (`bind_time`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;


CREATE TABLE `bottom_activity_so` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `ncode` varchar(100) NOT NULL COMMENT '办理套餐的ncode',
  `amount` double(5,2) NOT NULL COMMENT '红包奖励金额',
  `create_time` datetime NOT NULL COMMENT '红包时间',
  `serial_number` varchar(20) NOT NULL COMMENT '手机号码',
  `status` int(2) NOT NULL COMMENT '状态 0：初始化 1：赠送成功',
  `remark` text COMMENT '上账结果返回',
  `sub_user_id` bigint(20) DEFAULT NULL COMMENT '分享办理的用户id',
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`user_id`),
  KEY `index_sub_user_id` (`sub_user_id`),
  KEY `index_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

alter table bottom_meal_so  add ncode varchar(80) DEFAULT NULL COMMENT '套餐编码';

alter table bottom_meal_so  add remark text DEFAULT NULL COMMENT '备注';


CREATE TABLE `bottom_user_qr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `user_qr_url` longtext COMMENT '世界杯活动个人二维码A',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;



