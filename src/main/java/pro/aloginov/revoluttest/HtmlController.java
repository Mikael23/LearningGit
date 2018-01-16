package pro.aloginov.revoluttest;

import org.apache.commons.io.FileUtils;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

@Singleton
@Path("page")
@Produces(MediaType.TEXT_HTML)
public class HtmlController {


    @GET
    @Path("{page}")
    public String getAccountDetails(@PathParam("page") String page) throws IOException {
        String content = FileUtils.readFileToString(
                new File("/Users/mikhailpovolotskiy/OneDrive/revoluttest/src/main/resources/web/" + page),
                Charset.defaultCharset()
        );
        return content;
       // return "hello world";
    }


}
