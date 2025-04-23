package fr.univrouen.rss25SB.model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;
public class TestRSS {
    public String loadFileXML() throws IOException {
    	Resource resource = new DefaultResourceLoader().getResource("classpath:xml/item.xml");
    	return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }
}
