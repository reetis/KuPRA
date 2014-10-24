package eu.komanda30.kupra.controllers.frontpage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class FrontpageController {
    @RequestMapping(method = RequestMethod.GET)
    public String show() {
        return "frontpage";
    }
}
