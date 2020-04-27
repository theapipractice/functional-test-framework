package suite;

import common.utils.Log;
import org.apache.jorphan.collections.HashTree;
import rga.business.entities.JMeterProfile;
import rga.utils.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import static rga.utils.RunProcess.*;

public class JMeterRunner {

    public static void main(String[] args) {
        try {
            Log.info("API functional test Started  ...");
            HashMap<String, String> globalConfig = new HashMap<String, String>();
            HashMap<String, String> jmeterExecConfig = new HashMap<String, String>();

            String jmeterExecutablePath = "";
            String resultFolder = "";
            String xslFileLocation = "";
            String resourceDirPath = Common.resourceDirPath();
            String baseFolder = Common.baseFolder();
            String jmxFolderPath = "";

            boolean mailerEnabled = false;
            try {
                mailerEnabled = Boolean.valueOf(System.getProperty("mailerEnabled"));
            } catch (Exception e) {
                mailerEnabled = false;
            }
            String receipientAddresses = System.getProperty("sendTo");
            if (!resourceDirPath.isEmpty()) {
                globalConfig = ConfigReader.generalProperties().getConfig();
                Helper.generateJmeterProperyFile(resourceDirPath);
                jmeterExecConfig = ConfigReader.jmeterTestExecutionProperties().getConfig();
                jmeterExecutablePath = Helper.checkForValidDirectory(resourceDirPath + globalConfig.get("Jmeter_Path"));
                resultFolder = Helper.checkForValidDirectory(Common.getReportPath());
                xslFileLocation = Common.xslFileLocation();
                jmxFolderPath = Common.jmxFolderPath();
                File file = new File(resultFolder);
                if (file.exists())
                    Helper.deleteDir(file);
                Helper.createDir(resultFolder);
                Helper.loadResources(resourceDirPath, resultFolder);
            } else {
                Log.error("Resource directory path is empty. Please check maven configuration.");
                return;
            }

            // Setting global runtime params...
            String reportDateTime = Helper.getCurrentDate();
            Set<String> APIs = jmeterExecConfig.keySet();
            ArrayList<String[]> moduleReport = new ArrayList<String[]>();
            for (String api : APIs) {
                String apiFile = "";
                apiFile = jmeterExecConfig.get(api);
                if (!apiFile.endsWith(".jmx"))
                    apiFile = apiFile + ".jmx";
                apiFile = jmxFolderPath + apiFile;
                File jmxFile = new File(apiFile);
                String[] moduleReportData = {"", "", ""};

                if (!jmxFile.exists()) {
                    loadJmeterPropertiesFromFiles();
                    HashTree projectTree = createJmxFile(JMeterProfile.page(0).getTestPlanName(), JMeterProfile.page(0).getMethod(), JMeterProfile.page(0).getUrl(), JMeterProfile.page(0).getPort(), JMeterProfile.page(0).getPath(), JMeterProfile.page(0).getLoops(), JMeterProfile.page(0).getNumThreads(), JMeterProfile.page(0).getRamUps(), JMeterProfile.page(0).getDuration(), JMeterProfile.page(0).getNameOfThreadGroup());
                    String path = saveAsJmxFile(projectTree, JMeterProfile.page(0).getFolderContainerName(), JMeterProfile.page(0).getFileName());

                    //HashTree multiProjectTree = createJmxFileWithMultiThreadGroup(JMeterProfile.page(1).getTestPlanName(), JMeterProfile.page(1).getMethod(), JMeterProfile.page(1).getUrl(), JMeterProfile.page(1).getPort(), JMeterProfile.page(1).getPath(), JMeterProfile.page(1).getLoops(), JMeterProfile.page(1).getNumThreads(), JMeterProfile.page(1).getRamUps(), JMeterProfile.page(1).getNameOfThreadGroup(), JMeterProfile.page(1).getNumberOfThreadGroup());
                    //saveAsJmxFile(multiProjectTree, JMeterProfile.page(1).getFolderContainerName(), JMeterProfile.page(1).getFileName());

                    //HashTree multiProjectTree = createJmxFile(JMeterProfile.page(0).getTestPlanName(), JMeterProfile.page(0).getMethod(), JMeterProfile.page(0).getUrl(), JMeterProfile.page(0).getPort(), JMeterProfile.page(0).getPath(), JMeterProfile.page(0).getLoops(), JMeterProfile.page(0).getNumThreads(), JMeterProfile.page(0).getRamUps(), JMeterProfile.page(0).getNameOfThreadGroup());
                    //saveAsJmxFile(multiProjectTree, JMeterProfile.page(0).getFolderContainerName(), JMeterProfile.page(0).getFileName());
                    jmxFile = new File(path);
                }

                if (jmxFile.exists()) {
                    RunProcess runProcess = new RunProcess(jmeterExecutablePath, api, apiFile, resultFolder,
                            xslFileLocation, reportDateTime);
                    moduleReportData = runProcess.executeJMeterAndWriteResults();
                    moduleReport.add(moduleReportData);
                } else {
                    Log.info(apiFile + " JMX file does not exist.");
                }

                if (mailerEnabled) {
                    SendMail sendMail = new SendMail(receipientAddresses);
                    sendMail.sendMailNotification(baseFolder + globalConfig.get("Result_Path"), moduleReportData);
                }
            }
            if (moduleReport.size() > 0) {
                String successRate = globalConfig.get("TestStatusQualifier");
                if (successRate.isEmpty())
                    successRate = "99";
                APIReportProcessing.generateSummaryReportFile_New(resultFolder, moduleReport, successRate);
            }

            Helper.archiveReports(resultFolder, resultFolder + "archived_" + Helper.getCurrentDate() + ".zip");
            Log.info("Executed Jmeter Functional TestCase(s).");
        } catch (Exception e) {
            Log.error("Some error occurred. Exception:" + e.getMessage());
        }
    }
}