DROP TABLE IF EXISTS `dsp_users`;
CREATE TABLE `dsp_users` (
  `userid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `companyid` mediumint(9) NOT NULL DEFAULT '0' COMMENT '公司id',
  `pid` mediumint(9) NOT NULL DEFAULT '0' COMMENT '父id',
  `username` char(20) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` char(32) NOT NULL DEFAULT '' COMMENT '密码',
  `nickname` char(20) NOT NULL DEFAULT '' COMMENT '昵称',
  `regdate` bigint(10) unsigned NOT NULL COMMENT '注册时间',
  `lastdate` bigint(11) NOT NULL DEFAULT '0' COMMENT '最后一次登录时间',
  `regip` char(15) NOT NULL DEFAULT '' COMMENT '注册ip',
  `lastip` char(15) NOT NULL DEFAULT '' COMMENT '最后一次登录ip',
  `loginnum` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '登录次数',
  `email` char(32) NOT NULL DEFAULT '' COMMENT '邮箱',
  `mobile` char(11) NOT NULL DEFAULT '' COMMENT '手机号码',
  `islock` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否锁定',
  `vip` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否会员',
  `overduedate` int(11) NOT NULL DEFAULT '0' COMMENT '账户过期时间',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '状态-用于软删除',
  PRIMARY KEY (`userid`),
  UNIQUE KEY `username` (`username`) USING BTREE,
  KEY `email` (`email`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

INSERT INTO `dsp_users` VALUES ('5', '5', '0', 'xd', 'dc483e80a7a0bd9ef71d8cf973673924', '冬哥', '1468038500', '1470124752', '127.0.0.1', '192.168.0.128', '24', 'kk@haiyaotech.com', '15915492613', '0', '0', '0', '0');
