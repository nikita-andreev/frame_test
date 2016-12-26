package com.applitools.app;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.StdoutLogHandler;
import com.applitools.eyes.*;

import com.applitools.eyes.Eyes;



public class FrameTest 
    extends TestCase
{
	public WebDriver driver;
	public Eyes eyes;

    public FrameTest( String testName )
    
    {
        super( testName );
        driver = new ChromeDriver();
        eyes = new Eyes();
        
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));
        eyes.setLogHandler(new StdoutLogHandler(true));
    }

    public static Test suite()
    {
        return new TestSuite( FrameTest.class );
    }

    public void testFrame() 
	{
    	WebDriver drv = eyes.open(driver, "frames_111", "frames_test_222", new RectangleSize(1024, 440));
    	try {
    		
    		drv.get("https://aqueous-shore-68375.herokuapp.com/frames");
    		
    		eyes.checkWindow("First page with all frames");
    		
    		((EyesWebDriver) drv).executeScript("window.scrollBy(0,30)", "");
    		
    		eyes.checkFrame("frame_to_test", "test frame0");
    		eyes.checkFrame(new String[] {"frame_to_test", "frame_to_test"}, "test frame1");
    		
    		eyes.close();
    		
    	} finally {
            eyes.abortIfNotClosed();
            drv.close();    		
    	}
	}
}
