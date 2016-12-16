package com.cfets.bcs.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cfets.bcs.util.FileUtil;
import com.cfets.bcs.util.StringUtil;

public class GenConfig {
  private static final Logger LOGGER = LoggerFactory.getLogger(GenConfig.class);

  public static void main(String[] args) throws URISyntaxException, IOException {
    String projectDir = System.getProperty("user.dir");
    String javaFileDir = projectDir + "/src/main/java/";
    List<File> files = FileUtil.getAllFilesWithDir(javaFileDir);
    File configDirFile = null;
    for (File file : files) {
      if (file.isDirectory() && file.getName().equals("config")) {
        configDirFile = file;
      }
    }

    assert (configDirFile != null);

    String configDir = configDirFile.getPath();
    String packageString = configDir.substring(javaFileDir.length()).replace("\\", ".");
    File daoFile = new File(configDir + "/AppConfig.java");
    PrintWriter printWriter = new PrintWriter(new FileWriter(daoFile));
    printWriter.println("package " + packageString + ";");
    printWriter.println("");
    printWriter.println("import org.springframework.beans.factory.annotation.Value;");
    printWriter.println("import org.springframework.context.annotation.Bean;");
    printWriter.println("import org.springframework.context.annotation.Configuration;");
    printWriter.println("import org.springframework.context.annotation.PropertySource;");
    printWriter.println("import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;");
    printWriter.println("");

    printWriter.println("@Configuration");
    printWriter.println("// http://jackyrong.iteye.com/blog/2191818?utm_source=tuicool");
    printWriter.println("@PropertySource(value = \"classpath:app.properties\")");
    printWriter.println("public class AppConfig {");

    // 获取配置文件的内容
    String appConfigPath = GenConfig.class.getClassLoader().getResource("app.properties").getPath();
    BufferedReader bufferedReader = new BufferedReader(new FileReader(appConfigPath));
    String configLine;
    List<String> configFields = new ArrayList<>();
    String propField;
    String propFieldValue;
    while ((configLine = bufferedReader.readLine()) != null) {
      if (StringUtil.isBlank(configLine)) {
        continue;
      } else if (configLine.trim().startsWith("#")) {
        printWriter.println("  // " + configLine.trim()); // 输出注释信息
      } else {
        propField = configLine.trim().split("=")[0].trim();
        propFieldValue = configLine.trim().split("=")[1].trim();
        printWriter.println("  @Value(\"${" + propField + "}\")");
        String configField = "";
        if (propField.indexOf(".") != -1) {
          String[] tempSplittedStrings = propField.split("\\.");
          for (String string : tempSplittedStrings) {
            configField += StringUtil.upperFirstChar(string);
          }
        }

        configField = StringUtil.lowerFirstChar(configField);

        printWriter.println("  private String " + configField + "; // " + propFieldValue);

        configFields.add(configField);
      }
    }

    printWriter.println("");
    printWriter.println("  public AppConfig() {");
    printWriter.println("  }");
    printWriter.println("");
    printWriter.println("  // properties文件的占位符替换功能");
    printWriter.println("  @Bean");
    printWriter.println("  public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {");
    printWriter.println("    return new PropertySourcesPlaceholderConfigurer();");
    printWriter.println("  }");

    printWriter.println("");
    printWriter.println("// getters and setters");
    for (String field : configFields) {
      printWriter.println("  public String get" + StringUtil.upperFirstChar(field) + "() {");
      printWriter.println("    return " + field + ";");
      printWriter.println("  }");
      printWriter.println("");
      printWriter.println("  public void set" + StringUtil.upperFirstChar(field) + "(String " + field + ") {");
      printWriter.println("    this." + field + " = " + field + ";");
      printWriter.println("  }");
      printWriter.println("");
    }

    printWriter.println("");
    printWriter.println("}");
    printWriter.close();
    bufferedReader.close();

    LOGGER.info("生成成功");
  }

}
