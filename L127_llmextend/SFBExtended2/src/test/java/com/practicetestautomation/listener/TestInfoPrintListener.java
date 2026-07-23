/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.practicetestautomation.listener;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestInfoPrintListener implements ITestListener {
    
    public void onTestFailure(ITestResult result) { 
        System.out.println("******** TEST FAILURE ********");
        ExtentTest test = (ExtentTest) result.getAttribute("reporterObject");
        test.log(Status.INFO, "Test Case Name : " + result.getName());
        test.fail("Error: " + result.getThrowable().getMessage());
    }
       
    public void onTestSuccess(ITestResult result) { 
        System.out.println("******** TEST SUCCESS ********");
        ExtentTest test = (ExtentTest) result.getAttribute("reporterObject");
        test.log(Status.INFO, "Test Case Name : " + result.getName());
        test.pass("Test Status is Success");
    }
}
