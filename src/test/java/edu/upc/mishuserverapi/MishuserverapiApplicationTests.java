package edu.upc.mishuserverapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.upc.mishuserverapi.service.MailService;

@SpringBootTest
class MishuserverapiApplicationTests {

	@Autowired
	private MailService mailService;

	// @Test
	void contextLoads() {
		mailService.sendHtmlMail("1440699292@qq.com","欢迎注册《密书》帐号服务","<h1>你可以点击下方链接验证帐户。</h1><a href='"+"https://upccaishu.top/api/user/verify?email=1440699292@qq.com&token=verifyString'>点击链接验证帐户</a>");
	}

}
