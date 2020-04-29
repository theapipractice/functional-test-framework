package flowstep.utils;

import common.utils.Log;
import org.zaproxy.clientapi.core.*;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class ZapSecurity {
    private final String ZAP_HOST = "localhost";
    private final int ZAP_PORT = 8095;
    private final String ZAP_API_KEY = "b30torovcb7rv5ehjlobvfdee8";
    private ClientApi api;
    private ApiResponse response;
    private String target;


    public ZapSecurity(String target){
        this.target = target;
        api = new ClientApi(ZAP_HOST, ZAP_PORT,ZAP_API_KEY);
    }

    // function to wait for passive scan to complete
    public  void waitForPassiveScanToComplete() {
        Log.info("--- Waiting for passive scan to complete --- ");
        System.out.println("--- Waiting for passive scan to complete --- ");
        try {
            api.pscan.enableAllScanners(); // enable passive scanner.
            // getting a response
            response = api.pscan.recordsToScan();
            //iterating till we get response as "0".
            while (!response.toString().equals("0")) {
                response = api.pscan.recordsToScan();
            }
        } catch (ClientApiException e) {
            Log.error(e.getMessage());
        }
        Log.info("--- Passive scan completed! ---");
        System.out.println("--- Passive scan completed! ---");
    }

    // Example function to start and synchronize with active scan.
    public  void startActiveScan() {
        Log.info("Active scan : " + target);
        System.out.println("Active scan : " + target);
        try {
            // initiate the active scan -  refer doc to underatand the  constructor parameters.
            response = api.ascan.scan(target, "True", "False", null, null, null);
            int progress;
            // scan response will return the scan id to support concurrent scanning.
            String scanid = ((ApiResponseElement) response).getValue();
            // Polling the status of scan until it completes
            while (true) {
                Thread.sleep(5000);
                progress = Integer.parseInt(((ApiResponseElement)
                        api.ascan.status(scanid)).getValue());
                Log.info("Active Scan progress : " + progress + "%");
                System.out.println("Active Scan progress : " + progress + "%");
                if (progress >= 100) {
                    break;
                }
            }
            Log.info("Active Scan complete");
            System.out.println("Active Scan complete");
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }

    // Example code to start and synchronize the spider scan.
    public  void startSpiderScan() {
        Log.info("Spider : " + target);
        System.out.println("Spider : " + target);
        try {
            // Start the spider scan - refer the documentation of ZAP  APIs to understand the spider scan
            response = api.spider.scan(target, null, null, null, null);
            int progress;
            // scan response will return the scan id to support concurrent scanning.
            String scanid = ((ApiResponseElement) response).getValue();
            // Polling the status until it completes
            while (true) {
                Thread.sleep(1000);
                progress = Integer.parseInt(((ApiResponseElement) api.spider.status(scanid)).getValue());
                Log.info("Spider progress : " + progress + "%");
                System.out.println("Spider progress : " + progress + "%");
                if (progress >= 100) {
                    break;
                }
            }
            Log.info("Spider complete");
            System.out.println("Spider complete");
        } catch (Exception e) {
           Log.error(e.getMessage());
        }
    }

    public  void startAjaxSpiderCan(){
        try {
            // Start spidering the target
            System.out.println("Ajax Spider target : " + target);
            response = api.ajaxSpider.scan(target, null, null, null);
            String status;

            long startTime = System.currentTimeMillis();
            long timeout = TimeUnit.MINUTES.toMillis(2); // Two minutes in milli seconds
            // Loop until the ajax spider has finished or the timeout has exceeded
            while (true) {
                Thread.sleep(2000);
                status = (((ApiResponseElement) api.ajaxSpider.status()).getValue());
                System.out.println("Spider status : " + status);
                if (!("stopped".equals(status)) || (System.currentTimeMillis() - startTime) < timeout) {
                    break;
                }
            }
            System.out.println("Ajax Spider completed");
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // example fucntion to store an html report.
    public  void getReports() {
        try {
            // calling core apis to get html report in bytes.
            byte[] bytes = api.core.htmlreport();

            // getting the alert messages and just printing those.
             response = api.core.messages(target, "0", "99999999");
            Log.info(response.toString());

            // storing the bytes in to html report.
            String str = new String(bytes, StandardCharsets.UTF_8);
            String time  = Common.getCurrentTime("HH_mm_ss");

            File newTextFile = new File("zap_reports/Zap_"+time+".html");
            FileWriter fw = new FileWriter(newTextFile);

            fw.write(str);
            fw.close();
        } catch (Exception e) {
           Log.error(e.getMessage());
        }
    }

}
