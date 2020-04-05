package io.jiali.jfinder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

class JFinder {

  private boolean isRun = true;
  private File currentPath = new File("");

  boolean isRun() {
    return isRun;
  }

  void setRun() {
    isRun = false;
  }

  File getCurrentPath() {
    return currentPath;
  }

  void cd(String s) {
    currentPath = directoryCheck(s, "浏览", 1);
    System.out.println("当前路径为：" + currentPath.getAbsolutePath());
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
    if (f.mkdir()) {
      System.out.println("名为" + f.getName() + "的新目录成功创建");
    } else {
      System.out.println("已存在同名目录，或者目录名无效");
    }
  }

  void removeDir(File f) {
    File[] files = f.listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) {
          removeDir(file);
        } else {
          if (file.delete()) {
            System.out.println("成功删除 " + file.toPath());
          }
        }
      }
    }
    if (f.delete()) {
      System.out.println("成功删除 " + f.toPath());
    } else {
      System.out.println("操作失败，" + f.toPath() + "不存在");
    }
  }

  void copy(File src, File dest) {
    Controller controller = new Controller();
    if (!dest.isDirectory()) {
      System.out.println("错误！输入的粘贴目录格式不正确");
      copy(src, new File(controller.input()));
    } else {
      if (dest.mkdirs()) {
        System.out.println("目标目录不存在，已自动新建！");
      }
      if (src.isDirectory()) {
        String[] fs = src.list();
        dest = new File(dest.getAbsolutePath(), src.getName());
        if (!dest.mkdirs()) {
          System.out.println("目标目录存在同名文件夹，将复制到其中");
        }
        assert fs != null;
        for (String f : fs) {
          copy(new File(src, f), dest);
        }
        System.out.println("文件夹复制成功! " + dest.toPath());
      } else {
        dest = new File(dest.getAbsolutePath(), src.getName());
        try (FileInputStream inStream = new FileInputStream(src);
            FileOutputStream outStream = new FileOutputStream(dest);) {
          byte[] buffer = new byte[1024];
          int length;
          while ((length = inStream.read(buffer)) > 0) {
            outStream.write(buffer, 0, length);
          }
          System.out.println("文件复制成功! " + dest.toPath());
          inStream.close();
          outStream.close();
        } catch (IOException e) {
          System.out.println("出现错误，请检查文件：" + dest.toPath());
        }
      }
    }
  }

  File directoryCheck(String s, String command, int require) {
    //require: 0-only file/ 1-only dir/ 2-dir or file
    File f = check(s, command);
    switch (require) {
      case 0: {
        if (f.isDirectory()) {
          System.out.println("输入不能为目录！请重新输入");
          f = directoryCheck("", command, 0);
        }
        break;
      }
      case 1: {
        if (!f.isDirectory()) {
          System.out.println("输入必须是目录！请重新输入");
          f = directoryCheck("", command, 1);
        }
        break;
      }
    }
    return f;
  }

  File check(String s, String command) {
    Controller controller = new Controller();
    File src = new File(s);
    if (!s.isEmpty()) {
      if (!src.exists()) {
        File f = new File(getCurrentPath().getAbsolutePath(), s);
        if (f.exists()) {
          src = f;
        } else {
          System.out.println("目录或文件不存在，请重新输入！");
          src = check(controller.input("要" + command + "的文件名或目录名"), command);
        }
      }
    } else {
      src = check(controller.input("要" + command + "的文件名或目录名"), command);
    }
    return src;
  }

  void encryptionCore(File src, boolean isEncrypt) {
    String s = src.getAbsolutePath();
    File dest = new File((isEncrypt ? s + "encrypt" : s.substring(0, s.length() - 7)));
    try {
      if (!dest.createNewFile()) {
        System.out.println("目录下已有同名文件！输出文件将覆盖同名文件");
      }
      InputStream in = new FileInputStream(src);
      OutputStream out = new FileOutputStream(dest);
      byte[] buffer = new byte[in.available()];
      int length;
      while ((length = in.read(buffer)) > 0) {
        for (int i = buffer.length - 1; i >= 0; i--) {
          out.write(buffer[i]);
        }
      }
      in.close();
      out.close();
      System.out.println("创建" + (isEncrypt ? "加" : "解") + "密文件成功！");
    } catch (IOException e) {
      System.out.println("在创建" + (isEncrypt ? "加" : "解") + "密文件中出现错误");
    }
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
