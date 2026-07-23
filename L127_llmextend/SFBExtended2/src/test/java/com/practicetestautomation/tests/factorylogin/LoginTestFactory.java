/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.practicetestautomation.tests.factorylogin;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

public class LoginTestFactory extends FactoryBaseTest {
    protected String testEnd;
    
    
    public LoginTestFactory() { } 
//    @Factory(dataProviderClass=TestDataProviders.class, dataProvider="dataProviderLoginFactorySuite") 
    public LoginTestFactory(String browser, String testNamePrepend) {
        super(browser, testNamePrepend);
    }
    
    
    @DataProvider
    public Object[][] testFactoryDpMethod(ITestContext caller) { 
        String callerName = caller.getName();
        System.out.println("Test method name: " + callerName);
        Object data[][] = null;
        if(callerName.equals("TESTLOGINFUNCTIONALITY")) {
            data = new Object[1][2];
            data[0][0] = "chrome";
            data[0][1] = "Chrome";
            testEnd = "Positive Login Test";
        }
        return data;
    }
    
    public String getFullTestName() { 
        String endTestName = testEnd != null && !testEnd.equals("testName") ? testEnd : "Login Test";
        return getTestNamePrepend() + endTestName;
    }
}
