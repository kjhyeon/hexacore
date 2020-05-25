package hexa.core.web.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class testCtrl {

	@RequestMapping("/test.do")
	public String Test(String ckedit) {
		System.out.println(ckedit);
		
		return "test/test";
	}
}
