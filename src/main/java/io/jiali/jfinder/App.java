package io.jiali.jfinder;

import java.io.File;
import java.util.Scanner;

public class App {

  public static void main(String[] args) {
    JFinder jFinder = new JFinder();
    Scanner scanner = new Scanner(System.in);

    jFinder.hello();

    boolean isRun = true;

    while (isRun) {
      String[] input = jFinder.input(scanner.nextLine());

      switch (input[0]) {
        case "help": {
          jFinder.help();
          break;
        }

        case "quit": {
          isRun = false;
          break;
        }

        case "cd": {
          jFinder.cd(new File(input[1].isEmpty() ? jFinder.getInput("目录名") : input[1]));
          break;
        }

        case "ls": {
          jFinder.list();
          break;
        }

        case "mkdir": {
          String name = input[1];
          if (input[1].isEmpty()) {
            name = jFinder.getInput("新建目录的名称");
          }
          jFinder.makeDir(new File(jFinder.getCurrentPath().getAbsolutePath(), name));
          break;
        }

        case "rmdir": {
          String name = input[1];
          if (input[1].isEmpty()) {
            name = jFinder.getInput("要删除的目录");
          }
          jFinder.removeDir(new File(name));
          break;
        }

        case "cp": {
          jFinder.copy(jFinder.copyCheck(input[1]), new File(jFinder.getInput("目标目录名")));
          break;
        }

        default: {
          System.out.println("无法识别指令!请重新输入");
          break;
        }
      }
    }
    scanner.close();
  }
}
