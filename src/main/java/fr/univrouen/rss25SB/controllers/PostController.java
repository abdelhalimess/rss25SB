package fr.univrouen.rss25SB.controllers;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.univrouen.rss25SB.model.Item;
import fr.univrouen.rss25SB.model.TestRSS;
@RestController
public class PostController {
    @PostMapping(value = "/testpost", consumes = "application/xml")
    public String postTest(@RequestBody String flux) {
        return ("<result><response>Message reçu : </response>" + flux + "</result>");
    }
    
    @PostMapping(value = "/postrss", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public String postRSS() throws IOException {
    TestRSS rss = new TestRSS();
    return rss.loadFileXML();
    }
    
    @RequestMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Item getXML() {
    Item it = new Item("12345678","Test item","2022-05-01T11:22:33");
    return it;
    }
}
