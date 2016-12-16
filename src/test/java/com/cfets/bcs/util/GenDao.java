package com.cfets.bcs.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.List;

import com.cfets.bcs.util.FileUtil;
import com.cfets.bcs.util.StringUtil;

public class GenDao {
  public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
    String[] entityWhiteLists = { "Device" };
    String projectDir = System.getProperty("user.dir");
    String javaFileDir = projectDir + "/src/main/java/";
    List<File> files = FileUtil.getAllFilesWithDir(javaFileDir);
    File entityDirFile = null;
    for (File file : files) {
      if (file.isDirectory() && file.getName().equals("entity")) {
        entityDirFile = file;
      }
    }

    assert (entityDirFile != null);

    String baseDir = entityDirFile.getParentFile().getPath();
    String daoDir = baseDir + "/dao/";
    String daoImplDir = baseDir + "/daoImpl/";
    String serviceDir = baseDir + "/service/";
    String serviceImplDir = baseDir + "/serviceImpl/";

    File[] entityFiles = entityDirFile.listFiles();

    String packageBaseString = baseDir.substring(javaFileDir.length() - 1).replace("\\", ".");
    if (packageBaseString.startsWith(".")) {
      packageBaseString = packageBaseString.substring(1);
    }

    for (File file : entityFiles) {
      String entityName = file.getName();
      String entityJavaName = entityName.split("\\.")[0];
      String entityPackageString = packageBaseString + ".entity." + entityJavaName;

      boolean isInWhiteList = false;
      for (String whiteName : entityWhiteLists) {
        if (entityJavaName.equals(whiteName)) {
          isInWhiteList = true;
        }
      }

      if (isInWhiteList) {
        continue;
      }

      // 产生dao/daoImpl/service/serviceImpl
      // dao
      String daoPackageString = packageBaseString + ".dao";
      File daoFile = new File(daoDir + entityJavaName + "Dao.java");
      PrintWriter printWriter = new PrintWriter(daoFile);
      printWriter.println("package " + daoPackageString + ";");
      printWriter.println();
      printWriter.println("import " + entityPackageString + ";");
      printWriter.println();
      printWriter.println("public interface " + entityJavaName + "Dao extends BaseDao<" + entityJavaName + "> {");
      printWriter.println();
      printWriter.println("}");
      printWriter.close();

      // daoImpl
      String daoImplPackageString = packageBaseString + ".daoImpl";
      File daoImplFile = new File(daoImplDir + entityJavaName + "DaoImpl.java");
      printWriter = new PrintWriter(daoImplFile);
      printWriter.println("package " + daoImplPackageString + ";");
      printWriter.println();
      printWriter.println("import org.springframework.stereotype.Repository;");
      printWriter.println();
      printWriter.println("import " + daoPackageString + "." + entityJavaName + "Dao;");
      printWriter.println("import " + entityPackageString + ";");
      printWriter.println();
      printWriter.println("@Repository(\"" + entityJavaName + "Dao\")");
      printWriter.println("public class " + entityJavaName + "DaoImpl extends BaseDaoImpl<" + entityJavaName
          + "> implements " + entityJavaName + "Dao  {");
      printWriter.println();
      printWriter.println("}");
      printWriter.close();

      // service
      String servicePackageString = packageBaseString + ".service";
      File serviceFile = new File(serviceDir + entityJavaName + "Service.java");
      printWriter = new PrintWriter(serviceFile);
      printWriter.println("package " + servicePackageString + ";");
      printWriter.println();
      printWriter.println("import " + entityPackageString + ";");
      printWriter.println();
      printWriter.println("public interface " + entityJavaName + "Service {");
      printWriter.println();
      printWriter.println("}");
      printWriter.close();

      // serviceImpl
      String serviceImplPackageString = packageBaseString + ".serviceImpl";
      File serviceImplFile = new File(serviceImplDir + entityJavaName + "ServiceImpl.java");
      printWriter = new PrintWriter(serviceImplFile);
      printWriter.println("package " + serviceImplPackageString + ";");
      printWriter.println();
      printWriter.println("import org.springframework.stereotype.Service;");
      printWriter.println();
      printWriter.println("import " + entityPackageString + ";");
      printWriter.println("import " + servicePackageString + "." + entityJavaName + "Service;");
      printWriter.println();
      printWriter.println("@Service(\"" + StringUtil.lowerFirstChar(entityJavaName) + "Service\")");
      printWriter.println("public class " + entityJavaName + "ServiceImpl implements " + entityJavaName + "Service {");
      printWriter.println();
      printWriter.println("}");
      printWriter.close();

    }
  }

}
