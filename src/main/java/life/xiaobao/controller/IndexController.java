package life.xiaobao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yangyongli
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/")
    public String index() {
        return "forward:/main.html";
    }

    @RequestMapping(value = "/home")
    public String home() {

        return "home";
    }

    @RequestMapping(value = "/admin")
    public String management() {
        return "forward:/index.html";
    }
}
