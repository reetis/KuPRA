package eu.komanda30.kupra.controllers.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/error/")
public class ErrorController {
    @RequestMapping("auth")
    //@ResponseBody
    public String authError() {
        return "authenticationError";
    }

    @RequestMapping("access_denied")
    public String accessDeniedError() {
        return "accessDeniedError";
    }

    @RequestMapping("notfound")
    //@ResponseBody
    public String notFoundError() {
        return "pageNotFoundError";
    }

    @RequestMapping("system")
    public String systemError() {
        return "systemError";
    }

    @RequestMapping("unsupported")
    @ResponseBody
    public String methodNotSupportedError() {
        return "unsupprotedError";
    }

    @RequestMapping("generic")
    @ResponseBody
    public String genericError() {
        return "genericError";
    }
}