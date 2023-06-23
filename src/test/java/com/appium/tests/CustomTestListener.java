package com.appium.tests;

import org.testng.*;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

public class CustomTestListener implements ITestListener {

    private Map<String, String> caseIdMap = new HashMap<>();
    private Map<String, String> testcaseStatusMap = new HashMap<>();
    private ITestResult iResult;
    private String outputFilePath = "target/testng-custom-results.xml";

    @Override
    public void onTestStart(ITestResult result) {
        Method testMethod = result.getMethod().getConstructorOrMethod().getMethod();
        String methodName = testMethod.getName();
        String caseId = getCaseId(testMethod);
        caseIdMap.put(methodName, caseId);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Not used in this example
        String methodName = result.getMethod().getMethodName();
        String caseId = caseIdMap.getOrDefault(methodName, "");
        testcaseStatusMap.put(methodName, "passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String caseId = caseIdMap.getOrDefault(methodName, "");
        testcaseStatusMap.put(methodName, "failed");
        testcaseStatusMap.putIfAbsent(methodName, "");
        iResult = result;

       /* try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath, true))) {
            writer.write("<testcase classname=\"" + result.getTestClass().getName() + "\" name=\"" + methodName + "\" time=\"" + 0 + "\">\n");
            writer.write("  <properties>\n");
            writer.write("    <property name=\"test_id\" value=\"" + caseId + "\"/>\n");
            writer.write("  </properties>\n");
            writer.write("  <failure message=\"" + result.getThrowable().getMessage() + "\">\n");
            writer.write("    <![CDATA[" + getStackTrace(result.getThrowable()) + "]]>\n");
            writer.write("  </failure>\n");
            writer.write("</testcase>\n");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String caseId = caseIdMap.getOrDefault(methodName, "");
        testcaseStatusMap.put(methodName, "skipped");
/*
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath, true))) {
            writer.write("<testcase classname=\"" + result.getTestClass().getName() + "\" name=\"" + methodName + "\" time=\"" + 0 + "\">\n");
            writer.write("  <properties>\n");
            writer.write("    <property name=\"test_id\" value=\"" + caseId + "\"/>\n");
            writer.write("  </properties>\n");
            writer.write("  <skipped />\n");
            writer.write("</testcase>\n");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onFinish(ITestContext context) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write("<testsuites>\n");

            List<XmlSuite> xmlSuites = context.getSuite().getXmlSuite().getParentSuite().getChildSuites();
            System.out.println("Number of suites: " + xmlSuites.size());

            xmlSuites.stream()
                    .flatMap(xmlSuite -> xmlSuite.getTests().stream())
                    .forEach(xmlTest -> processXmlTest(xmlTest, writer));

            writer.write("</testsuites>\n");

            System.out.println("Custom JUnit XML report generated: " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processXmlTest(XmlTest xmlTest, BufferedWriter writer) {
        xmlTest.getXmlClasses().stream()
                .flatMap(xmlClass -> xmlClass.getIncludedMethods().stream())
                .forEach(include -> {
                    String methodName = include.getName();
                    String caseId = caseIdMap.getOrDefault(methodName, "");
                    String status = testcaseStatusMap.getOrDefault(methodName, "");

                    try {
                        writer.write(" <testcase classname=\"" + xmlTest.getName() + "\" name=\"" + methodName + "\" time=\"0\">\n");
                        writer.write("   <properties>\n");
                        writer.write("     <property name=\"test_id\" value=\"" + caseId + "\"/>\n");
                        writer.write("   </properties>\n");

                        System.out.println("Status - " + status + " " + methodName);

                        if (status.equals("failed")) {
                            writer.write("  <failure message=\"" + iResult.getThrowable().getMessage() + "\">\n");
                            writer.write("    <![CDATA[" + getStackTrace(iResult.getThrowable()) + "]]>\n");
                            writer.write("  </failure>\n");
                        } else if (status.equals("skipped")) {
                            writer.write("  <skipped />\n");
                        }

                        writer.write(" </testcase>\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }



    private String getCaseId(Method testMethod) {
        if (testMethod.isAnnotationPresent(Test.class)) {
            Test annotation = testMethod.getAnnotation(Test.class);
            String description = annotation.description();
            return extractCaseId(description);
        }
        return "";
    }

    private String extractCaseId(String description) {
        // Implement your logic here to extract and return the "caseid" from the description
        // This example assumes that the "caseid" is specified within square brackets in the description
        int startIndex = description.indexOf('[');
        int endIndex = description.indexOf(']');
        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            return description.substring(startIndex + 1, endIndex);
        }

        String[] parts = description.split("=");
        if (parts.length > 1 && parts[0].trim().equalsIgnoreCase("caseid")) {
            return parts[1].trim();
        }

        return "";
    }

    private ITestNGMethod getTestMethodByName(ITestContext context, String methodName) {
        for (ITestNGMethod method : context.getAllTestMethods()) {
            System.out.println("getMethod - " + method.getMethodName());
            System.out.println("getMethodName - " + methodName);
            if (method.getMethodName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }
}
