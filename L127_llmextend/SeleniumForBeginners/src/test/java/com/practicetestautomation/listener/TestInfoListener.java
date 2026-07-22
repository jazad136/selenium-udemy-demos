/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.practicetestautomation.listener;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestInfoListener implements ITestListener {
    
    public void onTestFailure(ITestResult result) { 
        System.out.println("******** TEST FAILURE ********");
        System.out.println("Test Name: " + result.getName());
        System.out.println("Error : " + result.getThrowable().getMessage());
        System.out.println("Attribute Value : " + result.getAttribute("Author"));
        ExtentTest test = (ExtentTest) result.getAttribute("reporterObject");
        test.log(Status.INFO, "Test Case Name : " + result.getName());
        test.fail(result.getThrowable().getMessage());
    }
       
    public void onTestSuccess(ITestResult result) { 
        System.out.println("******** TEST SUCCESS ********");
        ExtentTest test = (ExtentTest) result.getAttribute("reporterObject");
        test.log(Status.INFO, "Test Case Name : " + result.getName());
        test.pass("Test Status is Success");
    }
}
