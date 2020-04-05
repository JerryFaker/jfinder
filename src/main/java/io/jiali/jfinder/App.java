package io.jiali.jfinder;

import java.io.File;

public class App {

  public static void main(String[] args) {

    JFinder jFinder = new JFinder();
    Controller controller = new Controller();

    jFinder.hello();

    while (jFinder.isRun()) {
      String[] input = controller.inputFormat();

      switch (input[0]) {
        case "help": {
          jFinder.help();
          break;
        }

        case "quit": {
          jFinder.setRun();
          break;
        }

        case "cd": {
          jFinder.cd(input[1]);
          break;
        }

        case "ls": {
          jFinder.list();
          break;
        }

        case "mkdir": {
          String name = input[1];
          if (input[1].isEmpty()) {
            name = controller.input("新建目录的名称");
          }
          jFinder.makeDir(new File(jFinder.getCurrentPath().getAbsolutePath(), name));
          break;
        }

        case "rmdir": {
          jFinder.removeDir(jFinder.directoryCheck(input[1],"删除",1));
          break;
        }

        case "cp": {
          jFinder.copy(jFinder.check(input[1], "复制"), new File(controller.input("目标目录名")));
          break;
        }

        case "encrypt": {
          jFinder.encryptionCore(jFinder.directoryCheck(input[1], "加密", 0), true);
          break;
        }

        case "decrypt": {
          jFinder.encryptionCore(jFinder.directoryCheck(input[1], "解密", 0), false);
          break;
        }

        default: {
          System.out.println("无法识别指令!请重新输入");
          break;
        }
      }
    }
  }
}
