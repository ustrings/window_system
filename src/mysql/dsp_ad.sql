DROP TABLE IF EXISTS `dsp_ad`;
CREATE TABLE `dsp_ad` (
  `id` int(4) unsigned NOT NULL AUTO_INCREMENT COMMENT '广告id',
  `name` varchar(255) NOT NULL COMMENT '广告标示名',
  `prio` int(4) NOT NULL DEFAULT '10' COMMENT '优先级,0-9,0为最高优先级',
  `push_status` int(1) unsigned NOT NULL DEFAULT '0' COMMENT '推送开关,1:推送,0:关闭',
  `push_method` int(1) unsigned NOT NULL DEFAULT '0' COMMENT '推送方式,1:定向,0:普推',
  `begin_time` bigint(11) NOT NULL DEFAULT '0' COMMENT '起始时间',
  `end_time` bigint(11) NOT NULL DEFAULT '0' COMMENT '结束时间',
  `set_num` int(32) unsigned NOT NULL DEFAULT '0' COMMENT '投放数量,单位个,0表示不限量',
  `url` varchar(1024) NOT NULL DEFAULT '' COMMENT '投放链接,落地页',
  `push_num` int(32) unsigned NOT NULL DEFAULT '0' COMMENT '投放统计,单位个',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

