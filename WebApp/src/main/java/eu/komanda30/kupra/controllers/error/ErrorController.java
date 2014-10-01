package eu.komanda30.kupra.controllers.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error/")
public class ErrorController {
    @RequestMapping("auth")
    public String authError() {
        throw new RuntimeException("Not implemented!");
    }

    @RequestMapping("notfound")
    public String notFoundError() {
        throw new RuntimeException("Not implemented!");
    }

    @RequestMapping("system")
    public String systemError() {
        throw new RuntimeException("Not implemented!");
    }

    @RequestMapping("unsupported")
    public String methodNotSupportedError() {
        throw new RuntimeException("Not implemented!");
    }

    @RequestMapping("generic")
    public String genericError() {
        throw new RuntimeException("Not implemented!");
    }
}