package io.jiali.jfinder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

class JFinder {

  private File currentPath = new File("");

  File getCurrentPath() {
    return currentPath;
  }

  String getInput(String s) {
    System.out.println("请输入" + s);
    Scanner scanner = new Scanner(System.in);
    return scanner.nextLine();
  }

  String[] input(String input) {
    String[] output = new String[2];
    if (input.contains(" ")) {
      output = input.split(" ", 2);
    } else {
      output[0] = input;
      output[1] = "";
    }
    return output;
  }

  void cd(File f) {
    if (f.exists() && f.isDirectory()) {
      currentPath = f;
      System.out.println("当前路径为：" + f.getAbsolutePath());
    } else {
      System.out.println("目录不存在！");
    }
  }

  void list() {
    File f = currentPath;
    if (f.exists() && f.isDirectory()) {
      File[] files = f.listFiles();
      int count = 0;
      assert files != null;
      for (File file : files) {
        System.out.print(file.getName());
        count++;
        if (count < 4) {
          System.out.print("    ");
        } else {
          System.out.println();
          count = 0;
        }
      }
      System.out.println("");
    } else {
      System.out.println("目录或文件不存在！");
    }
  }

  void makeDir(File f) {
    if (!f.exists()) {
      f.mkdir();
      System.out.println("名为" + f.getName() + "的新目录成功创建");

    } else {
      System.out.println("已存在同名目录!");
    }
  }

  void removeDir(File f) {
    File[] files = f.listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) {
          removeDir(file);
        } else {
          file.delete();
          System.out.println("成功删除文件 "+file.getName());
        }
      }
    }
    if (f.exists()) {
      f.delete();
      System.out.println("成功删除目录 "+f.getName());
    } else {
      System.out.println("操作失败，" + f.getName() + "不存在");
    }
  }

  void copy(File src, File dest) {
    if (!dest.exists()) {
      dest.mkdirs();
    }
    if (src.isDirectory()) {
      String[] fs = src.list();
      dest = new File(dest.getAbsolutePath(), src.getName());
      dest.mkdirs();
      assert fs != null;
      for (String f : fs) {
        copy(new File(src, f), dest);
      }
    } else {
      dest = new File(dest.getAbsolutePath(), src.getName());
      try (FileInputStream inStream = new FileInputStream(src);
          FileOutputStream outStream = new FileOutputStream(dest);) {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inStream.read(buffer)) > 0) {
          outStream.write(buffer, 0, length);
        }
        System.out.println("文件复制成功! "+dest.getAbsolutePath()+File.separator+dest.getName());
        inStream.close();
        outStream.close();
      } catch (IOException e) {
        System.out.println("出现错误，请检查文件："+dest.getAbsolutePath()+File.separator+dest.getName());
      }
    }

  }

  File copyCheck(String s) {
    File src = new File("");
    if (!s.isEmpty()) {
      if (new File(s).exists()) {
        src = new File(s);
      } else {
        File f = new File(getCurrentPath().getAbsolutePath(), s);
        if (f.exists()) {
          src = f;
        } else {
          System.out.println("目录或文件不存在！");
          src = copyCheck(getInput("要复制的文件或目录名"));
        }
      }
    } else {
      src = copyCheck(getInput("要复制的文件或目录名"));
    }
    return src;
  }

  void hello() {
    System.out.println("你好，欢迎使用JFinder");
    System.out.println("温馨提示：随时输入help，获取使用帮助");
    System.out.println("祝您玩得开心，过得愉快");
    System.out.println("输入quit，结束程序");
  }

  void help() {
    System.out.println("cd ~/Documents -- 转到此目录下 ");
    System.out.println("ls -- 罗列当前目录下内容");
    System.out.println("mkdir -- 在当前目录下新建文件夹");
    System.out.println("rmdir -- 删除当前目录以及目录下所有文件");
    System.out.println("cp -- 拷贝文件或目录到其他目录下");
    System.out.println("encrypt -- 加密文件");
    System.out.println("decrypt -- 解密文件");
    System.out.println("quit -- 退出程序");
  }
}
