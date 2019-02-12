package io.pine.examples.petstore.interfaces;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author frank.liu
 * @since 2019/2/11 0011.
 */
@Controller
public class HelpController {

    @GetMapping("/help")
    public String viewHelp(){
        return "help";
    }
}
