package life.xiaobao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yangyongli
 */
@Controller
public class EditorController {

    @RequestMapping(value = "/editor")
    public String editor() {
        return "editor";
    }
}
