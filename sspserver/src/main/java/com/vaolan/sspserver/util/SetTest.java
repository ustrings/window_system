package com.vaolan.sspserver.util;

import redis.clients.jedis.Jedis;

public class SetTest {

	public static void main(String[] args) {
		String vl001_300x250  ="<script src=\"http://s5.cnzz.com/z_stat.php?id=1253223523&web_id=1253223523\" language=\"JavaScript\"></script>"
+"<script language='javascript' id=\"script_click_count\"></script>\n"

+"<script type=\"text/javascript\">\n"
+"var _hmProtocol = ((\"https:\" == document.location.protocol) ? \" https://\" : \" http://\");\n"
+"document.write(unescape(\"%3Cscript src='\" + _hmProtocol + \"116.252.178.229/js/tongji_v05.js%3FadvertID=10718%26targetURL=http://www.gouwubang.com/?w=zhaolan&e=&spm=1.1' type='text/javascript'%3E%3C/script%3E\"));\n"
+"</script>";
		
		
		Jedis client = new Jedis("118.26.145.26");
		
		client.set("vl001_300x250", vl001_300x250);
		
		//System.out.println(client.get("vl001_300x250"));
	}
	
	
	
	

}
