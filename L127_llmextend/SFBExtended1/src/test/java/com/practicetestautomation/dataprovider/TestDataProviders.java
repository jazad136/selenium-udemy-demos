package com.practicetestautomation.dataprovider;

import java.lang.reflect.Method;
import org.testng.annotations.DataProvider;

/**
 *
 * @author JonathanSaddler
 */
public class TestDataProviders {
    @DataProvider
    public Object[][] dataProviderLoginSuite(Method method) {
        // separate data for each test
        System.out.println("Test method name: " + method.getName());
        
        Object data[][] = null;
        if(method.getName().toUpperCase().equals("TESTLOGINFUNCTIONALITY")) { 
            data = new Object[1][1];
            data[0][0] = "Positive Login Test";
        }
        if(method.getName().toUpperCase().equals("NEGATIVELOGINTEST")) { 
            data = new Object[2][4];
            data[0][0] = "incorrectUser";
            data[0][1] = "Password123";
            data[0][2] = "Your username is invalid!";
            data[0][3] = "Negative Login Test (Incorrect User)";
            data[1][0] = "student";
            data[1][1] = "incorrectPassword";
            data[1][2] = "Your password is invalid!";
            data[1][3] = "Negative Login Test (Incorrect Password)";
        }
        return data;
    }
    @DataProvider
    public Object[][] dataProviderTableSuite(Method method) {
        // separate data for each test
        System.out.println("Test method name: " + method.getName());
        
        Object data[][] = null;
        if(method.getName().toUpperCase().equals("TC1_LANGUAGEFILTERJAVA")) { 
            data = new Object[1][1];
            data[0][0] = "Test case 1: Language filter --> Java";
        }
        else if(method.getName().toUpperCase().equals("TC2_LEVELFILTERBEGINNERONLY")) { 
            data = new Object[1][1];
            data[0][0] = "Test case 2: Level filter --> Beginner only";
        }
        else if(method.getName().toUpperCase().equals("TC3_MINENROLLMENTS10000PLUS")) { 
            data = new Object[1][1];
            data[0][0] = "Test case 3: Min enrollments --> 10,000+";
        }
        return data;
    }
    @DataProvider
    public Object[][] dataProviderExceptionSuite(Method method) {
        // separate data for each test
        System.out.println("Test method name: " + method.getName());
        
        Object data[][] = null;
        if(method.getName().toUpperCase().equals("TESTNOSUCHELEMENTEXCEPTION")) { 
            data = new Object[1][1];
            data[0][0] = "Test case 1: NoSuchElementException";
        }
        else if(method.getName().toUpperCase().equals("TESTELEMENTNOTINTERACTIBLEEXCEPTION")) { 
            data = new Object[1][1];
            data[0][0] = "Test case 2: ElementNotInteractableException";
        }
        else if(method.getName().toUpperCase().equals("TESTTIMEOUTEXCEPTION")) { 
            data = new Object[1][1];
            data[0][0] = "Test case 2: ElementNotInteractableException";
        }
        else if(method.getName().toUpperCase().equals("TESTINVALIDELEMENTSTATEEXCEPTION")) { 
            data = new Object[1][1];
            data[0][0] = "Test case 3: Min enrollments --> 10,000+";
        }
        else if(method.getName().toUpperCase().equals("TESTSTALEELEMENTEXCEPTION")) { 
            data = new Object[1][1];
            data[0][0] = "Test case 3: Min enrollments --> 10,000+";
        }
        return data;
    }
}
