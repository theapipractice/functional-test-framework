package flowstep.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExcelFileReader {

    public static List<HashMap<String,String>> readExelFile(String filePath, String sheetName)
    {
        List<HashMap<String,String>> mydata = new ArrayList<HashMap<String, String>>();
        try {
            FileInputStream fs = new FileInputStream(filePath);
            XSSFWorkbook workbook = new XSSFWorkbook(fs);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            Row HeaderRow = sheet.getRow(0);

            for(int i=1;i<sheet.getPhysicalNumberOfRows();i++)
            {
                Row currentRow = sheet.getRow(i);
                HashMap<String,String> currentHash = new HashMap<String,String>();
                for(int j=0;j<currentRow.getPhysicalNumberOfCells();j++)
                {
                    Cell currentCell = currentRow.getCell(j);
                    switch (currentCell.getCellType())
                    {
                        case Cell.CELL_TYPE_NUMERIC:
                            currentHash.put(HeaderRow.getCell(j).getStringCellValue(), String.valueOf(currentCell.getNumericCellValue()));
                            break;
                        case Cell.CELL_TYPE_STRING:
                            currentHash.put(HeaderRow.getCell(j).getStringCellValue(), currentCell.getStringCellValue());
                            break;
                    }
                }
                mydata.add(currentHash);
            }
            fs.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return mydata;
    }

    public static void main(String args[]) throws Throwable {

        List<HashMap<String,String>> mydata = readExelFile(Common.xslDataFileLocation(),"Login");
        for (int i = 0; i < mydata.size(); i ++){
            System.out.println(mydata.get(i).get("FILE_NAME"));
            System.out.println(mydata.get(i).get("URL"));
            System.out.println(str2Int(mydata.get(i).get("PORT")));
        }

    }

    static public Integer str2Int(String str) {
        Integer result = null;
        if (null == str || 0 == str.length()) {
            return null;
        }
        try {
            result = Integer.parseInt(str);
        }
        catch (NumberFormatException e) {
            String negativeMode = "";
            if(str.indexOf('-') != -1)
                negativeMode = "-";
            str = str.replaceAll("-", "" );
            if (str.indexOf('.') != -1) {
                str = str.substring(0, str.indexOf('.'));
                if (str.length() == 0) {
                    return (Integer)0;
                }
            }
            String strNum = str.replaceAll("[^\\d]", "" );
            if (0 == strNum.length()) {
                return null;
            }
            result = Integer.parseInt(negativeMode + strNum);
        }
        return result;
    }
}
