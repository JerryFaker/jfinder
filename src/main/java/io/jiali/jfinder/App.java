package io.jiali.jfinder;
import java.io.File;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "你好，欢迎使用JFinder" );
        System.out.println("温馨提示：随时输入help，获取使用帮助");
        System.out.println("祝您玩得开心，过得愉快");
        System.out.println("输入quit，结束程序");

        JFinder jFinder = new JFinder();
        Scanner scanner = new Scanner(System.in);

        boolean isRun = true;
        while(isRun)
        {
        String input = scanner.nextLine();

        switch (input) {
            case "help": {
                jFinder.help();
                break;
            }

            case "quit": {
                isRun = false;
                break;
            }

            case "cd": {
                System.out.println("输入路径名：");
                File f = new File(scanner.nextLine());
                jFinder.cd(f);
                break;
            }

            case "ls": {
                jFinder.list();
                break;
            }

            case "mkdir": {
                System.out.println("输入新建文件夹名称，默认为\"新建文件夹\"：");
                String s = scanner.nextLine();
                jFinder.makeDir(new File(jFinder.currentPath.getName()+"/"+(s.isEmpty()? "新建文件夹" : s)));
                /*if (s.isEmpty())
                {
                    jFinder.makeDir(new File(jFinder.currentPath.getName()+"/新建文件夹"));
                }
                else
                {
                    jFinder.makeDir(new File(jFinder.currentPath.getName()+"/"+s));
                }
                */
            }
        }
    }
    }

}
