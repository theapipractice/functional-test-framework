package rga.utils;

import common.utils.Log;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FlowChart {

    public static String flowChartName = "";
    public static String featureName = "";
    static StringBuilder htmlStringBuilder = new StringBuilder();
    static boolean isHeader = false;
    static boolean isFooter = false;
    static boolean isWrite = false;
    static List<String> list = new ArrayList<>();
    public static StringBuilder addFlowChart(boolean isAdd, String featureName, boolean isLast){

        try {
            //define a HTML String Builder
            if (!isHeader) {
                //append html header and title
                htmlStringBuilder.append("<html><head> <link rel=\"stylesheet\" href='css/chart.css'/> <title>Selenium Test </title></head>");
                htmlStringBuilder.append("<b style=\"color:#35f53b;margin-left: 326px;\">Green</b> is step passed. <b style=\"color:red;\">Red</b> is step failed");
                htmlStringBuilder.append("<div id=\"flowchart\">");
                htmlStringBuilder.append("<div class=\"no1\"><a href=\"#\">Start</a></div>");
                htmlStringBuilder.append("<div class=\"line1\"><i class=\"arrow down\"></i></div>");
                isHeader = true;
            }

            if (isAdd && !featureName.isEmpty()){
                if (!checkFeatureName(list, featureName)) {
                    htmlStringBuilder.append(String.format("<div class=\"rectangle1\"><a href=\"#\">%s</a></div>", featureName));
                    htmlStringBuilder.append("<div class=\"line1\"><i class=\"arrow down\"></i></div>");
                    if (!isLast) {
                        htmlStringBuilder.append("<div class=\"diamond1\"><a style='margin: 10px -30px !important;'>Pass</a></div>");
                        htmlStringBuilder.append("<div class=\"line1\"><i class=\"arrow down\"></i></div>");
                    }
                }
                 list.add(featureName);
            }else if(isAdd == false && !featureName.isEmpty()) {
                htmlStringBuilder.append(String.format("<div class=\"rectangle2\"><a href=\"#\">%s</a></div>", featureName));
                htmlStringBuilder.append("<div class=\"line1\"><i class=\"arrow down\"></i></div>");
                htmlStringBuilder.append("<div class=\"diamond2\"><a style='margin: 10px -30px !important;'>Fail</a></div>");
                htmlStringBuilder.append("<div class=\"line1\"><i class=\"arrow down\"></i></div>");
                isLast = true;
            }

            if (!isFooter && isLast) {
                htmlStringBuilder.append("<div class=\"no1\"><a href=\"#\">End</a></div>");
                htmlStringBuilder.append("</div>");
                isFooter = true;
            }

            if (isLast) {
                    WriteToFile(htmlStringBuilder.toString(), flowChartName + ".html");
                    htmlStringBuilder = new StringBuilder();;
                    list = new ArrayList<>();
                    isHeader = false;
                    isFooter = false;
            }
        } catch (IOException e) {
            Log.error(e.getMessage());
        }

        return null;
    }

    private static void WriteToFile(String fileContent, String fileName) throws IOException {
        String projectPath = System.getProperty("user.dir") + File.separator + "flowchart" ;
        String tempFile = projectPath + File.separator + fileName;
        File file = new File(tempFile);
        // if file does exists, then delete and create a new file
        if (file.exists()) {
            try {
                File newFileName = new File(projectPath + File.separator+ "backup_"+fileName);
                file.renameTo(newFileName);
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //write to file with OutputStreamWriter
        OutputStream outputStream = new FileOutputStream(file.getAbsoluteFile());
        Writer writer=new OutputStreamWriter(outputStream);
        writer.write(fileContent);
        writer.close();

    }

    private static boolean checkFeatureName(List<String> list,String featureName){
        if (list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equalsIgnoreCase(featureName))
                    return true;
            }
        }
        return false;
    }
}
