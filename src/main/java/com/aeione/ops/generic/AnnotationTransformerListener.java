package com.aeione.ops.generic;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.aeione.ops.generic.IAutoConst.*;

public class AnnotationTransformerListener implements IAnnotationTransformer {
    public GoogleSheetAPI sheetAPI() throws IOException
    {
        return new GoogleSheetAPI();
    }

    ArrayList<String> totalTabs =null;
    String overViewSheet=null;
    String tab1Range =null;
    List<List<Object>> OverViewTabRows=null;
    List<List<Object>> Tab1Rows=null;
    String overViewTabRange =null;
    Map<String, String> mapping=null;

    public AnnotationTransformerListener() throws IOException {
       try {
           GoogleSheetAPI.getSheetsService();
           GoogleDriveAPI.getDriveService();

           //Get the Total number of tab's in the Sheet
           totalTabs = sheetAPI().getSheetsName(TEST_EXECUTION_SHEET);
           overViewSheet = totalTabs.get(0);
           tab1Range = ""+ totalTabs.get(1)+"!A1:ZZ";
           //Get overview of Modules
           overViewTabRange = ""+overViewSheet+"!A1:ZZ";

           OverViewTabRows = sheetAPI().getSpreadSheetRowValuesAndArrange(TEST_EXECUTION_SHEET, overViewTabRange);
           Tab1Rows= sheetAPI().getSpreadSheetRowValuesAndArrange(TEST_EXECUTION_SHEET, tab1Range);

       }catch(Exception e)
       {
           e.fillInStackTrace();
       }
    }

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        String currentTestCaseName = testMethod.getName();
        try {

            for(int i=0 ; i<=Tab1Rows.size()-1; i++)
            {
                if(Tab1Rows.get(i).contains(currentTestCaseName))
                {
                    System.out.println(Tab1Rows.get(i));

                    mapping = new HashMap<String, String>();

                    for(int a=0; a<=Tab1Rows.get(0).size()-1;a++)
                    {
                        mapping.put(Tab1Rows.get(0).get(a).toString(),Tab1Rows.get(i).get(a).toString());
                    }
                   break;
                }
            }

            String testModule = mapping.get("Module").trim();
            String testCaseName = mapping.get("TestCaseName").trim();
            String priority = mapping.get("Priority").trim();
            String enabled = mapping.get("Enabled").trim();

            System.out.println(testModule+ "," +testCaseName + "," + priority + "," + enabled);

            for(int i=0 ; i<=OverViewTabRows.size()-1; i++)
            {
                if(OverViewTabRows.get(i).contains(testModule))
                {
                    System.out.println(OverViewTabRows.get(i));

                    mapping = new HashMap<String, String>();

                    for(int a=0; a<=OverViewTabRows.get(0).size()-1;a++)
                    {
                        mapping.put(OverViewTabRows.get(0).get(a).toString(),OverViewTabRows.get(i).get(a).toString());
                    }
                    break;
                }
            }

            String module = mapping.get("Module").trim();
            String executionMode = mapping.get("Execution Mode").trim();
            String suiteType= mapping.get("Suite Type").trim();

            System.out.println(module + "," + executionMode + "," + suiteType);

            switch(executionMode) {
                case ENABLED:
                    annotation.setEnabled(true);
                    break;

                case DISABLED:
                    annotation.setEnabled(false);
                    break;

                case ENABLED_SPECIFIC_TEST_CASES:
                    annotation.setEnabled(Boolean.valueOf(enabled.toLowerCase()) == true && !enabled.trim().equals(""));
                    break;

                case DISABLED_SPECIFIC_TEST_CASES:
                    annotation.setEnabled(Boolean.valueOf(enabled.toLowerCase()) != false || enabled.trim().equals(""));
                    break;

            }

            if(!priority.equals(""))
            {
                annotation.setPriority(Integer.valueOf(priority));
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void TestCaseLevelModification()
    {

    }

}
