package eu.komanda30.kupra.controllers.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/error/")
public class ErrorController {
    @RequestMapping("auth")
    @ResponseBody
    public String authError() {
        return "Authentication error!";
    }

    @RequestMapping("access_denied")
    @ResponseBody
    public String accessDeniedError() {
        return "Access denied!";
    }

    @RequestMapping("notfound")
    @ResponseBody
    public String notFoundError() {
        return "Page not found!";
    }

    @RequestMapping("system")
    @ResponseBody
    public String systemError() {
        return "System error!";
    }

    @RequestMapping("unsupported")
    @ResponseBody
    public String methodNotSupportedError() {
        return "Method not supported!";
    }

    @RequestMapping("generic")
    @ResponseBody
    public String genericError() {
        return "Unknown server error!";
    }
}