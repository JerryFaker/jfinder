package io.jiali.jfinder;

import java.util.Scanner;

public class Controller {

  private Scanner scanner = new Scanner(System.in);

  String input() {
    return input("");
  }

  String input(String s) {
    if (!s.isEmpty()) {
      System.out.println("请输入" + s);
    }
    return scanner.nextLine();
  }

  String[] inputFormat() {
    String[] output = new String[2];
    String s = input("");
    if (s.contains(" ")) {
      output = s.split(" ", 2);
    } else {
      output[0] = s;
      output[1] = "";
    }
    return output;
  }
}
