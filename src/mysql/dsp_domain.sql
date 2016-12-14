DROP TABLE IF EXISTS `dsp_domain`;
CREATE TABLE `dsp_domain` (
  `id` tinyint(4) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `ad_id` tinyint(4) unsigned NOT NULL COMMENT '广告id,和dsp_ad中id映射',
  `domain` varchar(1024) NOT NULL DEFAULT '' COMMENT '域名',
  `push_num` int(32) unsigned NOT NULL DEFAULT '0' COMMENT '投放统计,单位个',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;