package mg.itu.ticketing;

import mg.itu.prom16.annotations.Controller;
import mg.itu.prom16.annotations.Get;

@Controller
public class Welcome {
    @Get("/")
    public String hello() {
        return "Hello World";
    }
}