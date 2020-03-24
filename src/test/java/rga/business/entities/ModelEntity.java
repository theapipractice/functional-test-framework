package rga.business.entities;

import java.util.List;

public class ModelEntity {
    public String modelName;
    public String type;
    public String defaultModelKey;
    public String modelType;
    public String description;
    public ModelDescribeData modelDescribeData;
    public Properties properties;
    public ModelDescribeDataStringType modelDescribeDataStringType;
    public ModelDescribeDataIntegerType modelDescribeDataIntegerType;
    public ModelDescribeDataNumberType modelDescribeDataNumberType;



    public class ModelDescribeData {
        public String minProperties;
        public String maxProperties;
        public boolean additionalProperties;
        public boolean uniqueItem;
        public boolean allowNull;
        public boolean defaultIsNull;
    }

   public class Properties {
       public List<String> propertiesValue;
   }

    public class ModelDescribeDataStringType{
        //Format Line
        public String format;
        public boolean personallyIdentifiableInformation;
        public boolean sensitiveInformation;

        //String's minimum length Line
        public String stringMinimumLength;
        public boolean uniqueIdentifier;
        public boolean allowNull;

        //String's maximum length Line
        public String stringMaximumLength;
        public String defaultDesData;
        public boolean defaultIsNull;

        //Pattern - Regular expression line
        public String patternRegularExpression;
        public String description;
        public String faker;
        public List<String> enumValue;
    }

    public class ModelDescribeDataIntegerType{
        //Format Line
        public String format;
        public boolean personallyIdentifiableInformation;
        public boolean sensitiveInformation;

        //Minimum Value
        public String minimumValue;
        public boolean valueMustBeGreaterThanMinimum;
        public boolean uniqueIdentifier;
        public boolean allowNull;

        //Maximum Value
        public String maximumValue;
        public boolean valueMustBeLessThanMaximum;
        public String defaultDesData;
        public boolean defaultIsNull;

        //Value Is A Multiple Of
        public String valueIsAMultipleOf;
        public String faker;
        public List<String> enumValue;
    }

    public class ModelDescribeDataNumberType{
        //Format Line
        public String format;
        public boolean personallyIdentifiableInformation;
        public boolean sensitiveInformation;

        //Minimum Value
        public String minimumValue;
        public boolean valueMustBeGreaterThanMinimum;
        public boolean uniqueIdentifier;
        public boolean allowNull;

        //Maximum Value
        public String maximumValue;
        public boolean valueMustBeLessThanMaximum;
        public String defaultDesData;
        public boolean defaultIsNull;

        //Value Is A Multiple Of
        public String valueIsAMultipleOf;
        public String faker;
        public List<String> enumValue;
    }
}
