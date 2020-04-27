package rga.utils;

import common.utils.Log;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.SetupThreadGroup;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static rga.utils.ControllerJMeter.*;


public class RunProcess {

    private String jmxFile;
    private String jMeterPath;
    private String resultFolder;
    private String rawXMLDataFile;
    private String xslFile;
    private String moduleName;
    private String reportDateTime;
    private String envProp;
    private String resourceDir;
    private String baseUrl = "";
    private String email = "";
    private String pwd = "";
    private String clientId = "";

    public RunProcess(String jMeterPth, String moduleName, String jmxFile, String resultFolder, String xslFile,
                      String reportDateTime) {
        try {
            this.jMeterPath = jMeterPth;
            this.jmxFile = jmxFile;
            this.resultFolder = resultFolder;
            this.xslFile = xslFile;
            this.moduleName = moduleName;
            this.rawXMLDataFile = resultFolder + moduleName + ".xml";
            this.reportDateTime = reportDateTime;

            if (System.getProperty("os.name").toLowerCase().contains("window"))
                jMeterPath = "\"" + jMeterPath + "jmeter.bat" + "\"";
            else
                jMeterPath = "sh " + jMeterPath + "jmeter";

            resourceDir = Common.resourceDirPath();
            if (resourceDir == null)
                resourceDir = "";

            String targetEnv = "";
            baseUrl = System.getProperty("baseUrl");
            /*if (baseUrl.contains("staging"))
                targetEnv = "staging";
            else
                targetEnv = "prod";*/
            targetEnv="staging";
            envProp = resourceDir + "configuration/automation_" + targetEnv + ".properties";//"JMX_Modules/properties/automation_" + targetEnv + ".properties";
            email = System.getProperty("email");
            pwd = System.getProperty("pwd");
        } catch (Exception ex) {
            Log.error("Error while setting up JMX file configuration. Exception:" + ex.getMessage());
        }
    }

    public String[] executeJMeterAndWriteResults() {
        String[] moduleWiseReport = {"", "", ""};
        try {
            String command = "";
            APIReportProcessing.apiResponseList.clear();
            moduleWiseReport[0] = moduleName;
            moduleWiseReport[1] = moduleName + "_" + reportDateTime + ".html";
            if (System.getProperty("os.name").toLowerCase().contains("window"))
                command = jMeterPath
                        + " -Jjmeter.save.saveservice.output_format=xml -Jjmeter.save.saveservice.assertion_results=all -Jjmeter.save.saveservice.response_code=true -Jjmeter.save.saveservice.response_data=true -Jjmeter.save.saveservice.response_message=true -Jjmeter.save.saveservice.subresults=true -Jjmeter.save.saveservice.assertions=true -Jjmeter.save.saveservice.samplerData=true -Jjmeter.save.saveservice.responseHeaders=true -Jjmeter.save.saveservice.requestHeaders=true -Jjmeter.save.saveservice.url=true -n -t "
                        + "\"" + jmxFile +  "\"" + " -l " + "\"" + resultFolder + moduleName + ".xml" + "\"" + " --addprop " + "\"" + envProp + "\"" + " -JresourceDir=" + "\"" + resourceDir + "\"" + " -Jdemo=50";//+ " -JbaseUrl=" + baseUrl + " -Jemail=" + email + " -Jpwd=" + pwd;
            else
                command = jMeterPath
                        + " -Jjmeter.save.saveservice.output_format=xml -Jjmeter.save.saveservice.assertion_results=all -Jjmeter.save.saveservice.response_code=true -Jjmeter.save.saveservice.response_data=true -Jjmeter.save.saveservice.response_message=true -Jjmeter.save.saveservice.subresults=true -Jjmeter.save.saveservice.assertions=true -Jjmeter.save.saveservice.samplerData=true -Jjmeter.save.saveservice.responseHeaders=true -Jjmeter.save.saveservice.requestHeaders=true -Jjmeter.save.saveservice.url=true -n -t "
                        + jmxFile + " -l " + resultFolder + moduleName + ".xml" + " --addprop " + envProp + " -JresourceDir=" + resourceDir  ;//" -JbaseUrl=" + baseUrl + " -Jemail=" + email + " -Jpwd=" + pwd;
            System.out.println(command);
            Process pro = Runtime.getRuntime().exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            byte[] bytes = new byte[4096];
            String line, output = "";
            while ((line = input.readLine()) != null) {
                output = output + line;
                System.out.println(line);
            }
            input.close();
            prepareFinalResults(moduleWiseReport[1]);
            if (APIReportProcessing.apiResponseList.size() > 0)
                moduleWiseReport[2] = "true";
            else
                moduleWiseReport[2] = "false";
        } catch (Exception e) {
            Log.error("Error while running JMX file through JMeter Non GUI mode. Error occurred in JMX File:"
                    + jmxFile + ". Exception:" + e.getMessage());
        }
        return moduleWiseReport;
    }

    private void prepareFinalResults(String moduleReport) throws IOException {
        try {
            String currentDateTimeReadable = Helper.getCurrentDateInReadableFormat();
            HashMap<String, String> globalParams = new HashMap<String, String>();
            globalParams.put("titleReport", moduleName);
            globalParams.put("dateReport", currentDateTimeReadable);
            APIReportProcessing.generatModuleWiseReportFile(rawXMLDataFile, xslFile, resultFolder + moduleReport,
                    globalParams);
            APIReportProcessing.fetchAPIReportDetail(rawXMLDataFile);
            APIReportProcessing.generateDetailedAPIReport(resultFolder, moduleName, currentDateTimeReadable);
        } catch (Exception e) {
            Log.error("Error while setting up parameters for final result preparation. Exception:" + e.getMessage());
        }
    }

    public static HashTree createJmxFile(String testPlanName, String method, String domain, long port, String path, int loops, int numThreads, int ramUp, long duration, String nameThreadGroup) throws IOException {
        HashTree projectTree = new HashTree();
        HTTPSampler googleGetSampler = ControllerJMeter.createHttpSampler(method, domain, port, path);
        googleGetSampler.addArgument("UserName","Admin");
        googleGetSampler.addArgument("Password","ADMIN1");
        LoopController loopCtrl = createLoopController((long) loops);
        loopCtrl.addTestElement(googleGetSampler);
        SetupThreadGroup setupThreadGroup = createSetupThreadGroup(loopCtrl, numThreads, ramUp, duration, nameThreadGroup);
        TestPlan testPlan = createTestPlan(testPlanName);
        testPlan.addThreadGroup(setupThreadGroup);

        /** The tree is needed if you save project as jmx file **/
        HashTree testPlanTree = projectTree.add(testPlan);
        HashTree setupThreadGroupTree = testPlanTree.add(setupThreadGroup);
        HashTree loopCtrlTree = setupThreadGroupTree.add(loopCtrl);
        loopCtrlTree.add(googleGetSampler);
        return projectTree;
    }

    public static HashTree createJmxFileWithMultiThreadGroup(String testPlanName, String method, Object domain, Long port, String path, Object loops, Object numThreads, Object ramUp, Object duration, String nameThreadGroup, long numberOfThreadGroup) throws IOException {
        HashTree projectTree = new HashTree();
        TestPlan testPlan = createTestPlan(testPlanName);
        Map<String,String>  mDomain = (Map) domain;
        Map<Integer, Integer>  mLoops = (Map) loops;
        Map<Integer, Integer>  mNumThreads = (Map) numThreads;
        Map<Integer, Integer>  mRamUp = (Map) ramUp;
        Map<Integer, Integer>  mDuration = (Map) duration;

        HashTree testPlanTree;
        HashTree setupThreadGroupTree;
        HashTree loopCtrlTree;

        for (int i = 1; i <= numberOfThreadGroup; i++) {
            HTTPSampler googleGetSampler = ControllerJMeter.createHttpSampler(method, mDomain.get("link"+i), port, path);
            LoopController loopCtrl = createLoopController(mLoops.get("loops"+1));
            loopCtrl.addTestElement(googleGetSampler);

            SetupThreadGroup setupThreadGroup = createSetupThreadGroup(loopCtrl, mNumThreads.get("numThread"+i), mRamUp.get("ramUp"+i), mDuration.get("numThread"+i),nameThreadGroup + "_" + i);
            testPlan.addThreadGroup(setupThreadGroup);

            /** The tree is needed if you save project as jmx file **/
            testPlanTree = projectTree.add(testPlan);
            setupThreadGroupTree = testPlanTree.add(setupThreadGroup);
            loopCtrlTree = setupThreadGroupTree.add(loopCtrl);
            loopCtrlTree.add(googleGetSampler);
        }

        return projectTree;
    }

    public static HashTree createJmxFileModel(String testPlanName, String method, String domain, int port, String path, int loops, int numThreads, int ramUp, long duration, String nameThreadGroup) throws IOException {
        HashTree projectTree = new HashTree();
        HTTPSampler googleGetSampler = ControllerJMeter.createHttpSampler(method, domain, port, path);
        LoopController loopCtrl = createLoopController(loops);
        loopCtrl.addTestElement(googleGetSampler);
        SetupThreadGroup setupThreadGroup = createSetupThreadGroup(loopCtrl, numThreads, ramUp, duration, nameThreadGroup);
        TestPlan testPlan = createTestPlan(testPlanName);
        testPlan.addThreadGroup(setupThreadGroup);

        /** The tree is needed if you save project as jmx file **/
        HashTree testPlanTree = projectTree.add(testPlan);
        HashTree setupThreadGroupTree = testPlanTree.add(setupThreadGroup);
        HashTree loopCtrlTree = setupThreadGroupTree.add(loopCtrl);
        loopCtrlTree.add(googleGetSampler);
        return projectTree;
    }

    public static String saveAsJmxFile(HashTree projectTree, String folderContainerName, String fileName) throws Exception {
        String dirLocation = Common.jmxFolderPath() + folderContainerName;
        File file = new File(dirLocation);
        if (!file.exists())
            Helper.createDir(dirLocation);

        //Save JMeter file-----------------------------------------------------------------------------------------
        SaveService.saveTree(projectTree, new FileOutputStream(dirLocation +"/"+ fileName));

        //Rewirte to JMX_Mapping property file---------------------------------------------------------------------
        HashMap<String, String> jmxExec;
        File jmxModuleConfigFile = new File(Common.resourceDirPath() + "Configuration/JMX_Mapping.properties");
        jmxExec = getAllJmxMapping();
        jmxExec.put(folderContainerName, folderContainerName+"/"+fileName);
        Helper.writeToFile(jmxExec,jmxModuleConfigFile.getAbsolutePath());

        //Rewirte to General property file-------------------------------------------------------------------------
        HashMap<String, String> jmxExecGeneral;
        HashMap<String, String> globalConfig = ConfigReader.generalProperties().getConfig();
        File jmxGeneralConfigFile = new File(Common.resourceDirPath() + "Configuration/General.properties");
        jmxExecGeneral = getAllGeneralMapping();
        if (globalConfig.get("Modules").contains(folderContainerName)){
            jmxExecGeneral.put("Modules",globalConfig.get("Modules"));
        }else{
            jmxExecGeneral.put("Modules",globalConfig.get("Modules") + "," + folderContainerName +"|true");
        }

        Helper.writeToFile(jmxExecGeneral,jmxGeneralConfigFile.getAbsolutePath());
        return dirLocation +"/"+ fileName;
    }

    public static void loadJmeterPropertiesFromFiles() {
        JMeterUtils.loadJMeterProperties(Common.resourceDirPath() + "apache-jmeter-5.2.1/bin/jmeter.properties");
        JMeterUtils.loadProperties(Common.resourceDirPath() + "apache-jmeter-5.2.1/bin/user.properties");
        JMeterUtils.setJMeterHome(Common.resourceDirPath() + "apache-jmeter-5.2.1/bin");
        JMeterUtils.setProperty("saveservice_properties", "/saveservice.properties");
    }

    private static HashMap<String, String> getAllJmxMapping(){
        HashMap<String, String> jmxExec = new HashMap<String, String>();
        HashMap<String, String> globalConfig = ConfigReader.generalProperties().getConfig();
        HashMap<String, String> jmxModuleMapping = ConfigReader.jmxMappingProperties().getConfig();
        String[] modules = globalConfig.get("Modules").split(",");
        if (modules.length > 0) {
            for (String mod : modules) {
                String[] modNameValue = mod.split("=");
                if (jmxModuleMapping.containsKey(modNameValue[0]))
                    jmxExec.put(modNameValue[0], jmxModuleMapping.get(modNameValue[0]));
            }
        }
        return  jmxExec;
    }

    private static HashMap<String, String> getAllGeneralMapping(){
        HashMap<String, String> jmxExec = new HashMap<String, String>();
        HashMap<String, String> globalConfig = ConfigReader.generalProperties().getConfig();
        jmxExec.put("Jmeter_Path",globalConfig.get("Jmeter_Path"));
        jmxExec.put("Result_Path",globalConfig.get("Result_Path"));
        jmxExec.put("TestStatusQualifier",globalConfig.get("TestStatusQualifier"));

        return  jmxExec;
    }

    public static void main(String args[]) throws Throwable {

        /*loadJmeterPropertiesFromFiles();
        List<JmeterFile> jmeterFiles = JsonDataReader.getJmeterData();
        HashTree projectTree = createJmxFileWithMultiThreadGroup(jmeterFiles.get(1).testPlanName, jmeterFiles.get(1).method, jmeterFiles.get(1).url, jmeterFiles.get(1).port, jmeterFiles.get(1).path, jmeterFiles.get(1).loops, jmeterFiles.get(1).numThreads, jmeterFiles.get(1).ramUps, jmeterFiles.get(1).nameOfThreadGroup, jmeterFiles.get(1).numberOfThreadGroup);
        saveAsJmxFile(projectTree, jmeterFiles.get(0).folderContainerName, jmeterFiles.get(0).fileName);*/


        loadJmeterPropertiesFromFiles();
        //HashTree projectTree = createJmxFile(JMeterProfile.page(0).getTestPlanName(), JMeterProfile.page(0).getMethod(), JMeterProfile.page(0).getUrl(), JMeterProfile.page(0).getPort(), JMeterProfile.page(0).getPath(), JMeterProfile.page(0).getLoops(), JMeterProfile.page(0).getNumThreads(), JMeterProfile.page(0).getRamUps(), JMeterProfile.page(0).getNameOfThreadGroup());
        //saveAsJmxFile(projectTree, JMeterProfile.page(0).getFolderContainerName(), JMeterProfile.page(0).getFileName());

        //HashTree multiProjectTree = createJmxFile(JMeterProfile.page(0).getTestPlanName(), JMeterProfile.page(0).getMethod(), JMeterProfile.page(0).getUrl(), JMeterProfile.page(0).getPort(), JMeterProfile.page(0).getPath(), JMeterProfile.page(0).getLoops(), JMeterProfile.page(0).getNumThreads(), JMeterProfile.page(0).getRamUps(), JMeterProfile.page(0).getNameOfThreadGroup());
        //saveAsJmxFile(multiProjectTree, JMeterProfile.page(0).getFolderContainerName(), JMeterProfile.page(0).getFileName());

    }


}