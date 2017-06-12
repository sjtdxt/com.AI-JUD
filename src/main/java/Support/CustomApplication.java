package Support;

import javax.json.stream.JsonGenerator;

import org.apache.naming.resources.Resource;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jsonp.JsonProcessingFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class CustomApplication extends ResourceConfig 
{
    public CustomApplication() 
    {
        register(JsonProcessingFeature.class);
        packages("Support");
        packages("org.glassfish.jersey.examples.jsonp");
        register(LoggingFilter.class);
        property(JsonGenerator.PRETTY_PRINTING, true);
 
    }
}