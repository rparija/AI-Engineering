package com.telusko.SpringAIDemo;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class DateTimeTool {

    @Tool(description = "Get Current date and time in user's timezone")
    public  String getCurrentDateAndTime(){
        System.out.println("DateTimeTool: getCurrentDateAndTime called");
        return java.time.ZonedDateTime.now().toString();
    }

    @Tool (description = "Get Current date and time for spedcified timezone")
    public String getCurrentDateAndTimeWithTimeZone(String timezone){
        System.out.println("DateTimeTool: getCurrentDateAndTime with timezone called: "+timezone);
        return java.time.ZonedDateTime.now(java.time.ZoneId.of(timezone)).toString();
    }
}
