package rga.business.entities;

import java.util.List;

public class PropertyEntity {
    public String propertyName;
    public String type;
    public boolean pii;
    public boolean sensitive;
    public String description;
    public StringType stringType;
    public IntegerType integerType;
    public InformationType informationType;
    public Enum anEnum;
    public String defaultValue;
    public String faker;
    public DescribeStringData describeStringData;
    public DescribeIntegerData describeIntegerData;


    public class StringType {
        public String sample;
        public String propertyType;
    }

    public class IntegerType {
        public int sample;
        public String propertyType;
    }

    public class InformationType {
        public boolean mustBeUnique;
        public boolean allowNull;
        public boolean defaultIsNull;
    }

    public class Enum {
        public List<String> enumValue;
    }

    public class DescribeStringData{
        public String format;
        public int stringMinimumLength;
        public int stringMaximumLength;
        public String patternRegularExpression;
        public String description;
    }
    public class DescribeIntegerData{
        public String format;
        public int minimumValue;
        public int MaximumValue;
        public int valueIsAMultipleOf;
    }
}
