package io.jiali.jfinder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class JFinder {

    public static File currentPath;

    void cd(File f) {
        if (f.exists() && f.isDirectory())
        {
            currentPath = f;
            System.out.println("目前路径为："+f.getName());
        }
        else
        {
            System.out.println("目录不存在！");
        }
    }

    void list() {
        File f = currentPath;
        if (f.exists() && f.isDirectory())
        {
            File[] files = f.listFiles();
            int count = 0;
            for (File file : files) {
                System.out.print(file.getName());
                count++;
                if (count < 4) {
                    System.out.print("    ");
                }
                else
                {
                    System.out.println();
                    count = 0;
                }
            }
        }
        else
        {
            System.out.println("目录或文件不存在！");
        }
    }

    void makeDir(File f) {
        if (!f.exists())
        {
            f.mkdir();
            System.out.println("名为"+f.getName()+"的新目录成功创建");

        }
        else
        {
            System.out.println("已存在同名目录!");
        }
    }

    void copy(File src, File dest) {
        if (src.isDirectory())
        {
            if (!dest.exists())
            {
                dest.mkdir();

                String fs[] = src.list();
                for (String f : fs) {
                    copy(new File(src, f), new File(dest, f));
                }
            }
        }
        else
        {
            try(FileInputStream inStream = new FileInputStream(src);
                FileOutputStream outStream = new FileOutputStream(dest);)
            {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inStream.read(buffer))>0)
                {
                    outStream.write(buffer,0,length);
                }
                System.out.println("目录复制成功！");
                inStream.close();
                outStream.close();
            }
            catch (IOException e)
            {
                System.out.println(e);
            }
        }
    }

    void removeDir(File f) {
        File[]files = f.listFiles();
        if (files != null)
        {
            for (File file : files)
            {
                if (file.isDirectory())
                {
                    removeDir(f);
                }
                else
                {
                    file.delete();
                }
            }
        }
        if (f.exists())
        {
            f.delete();
            System.out.println("成功删除目录!");
        }
        else
        {
            System.out.println("操作失败，"+f.getName()+"不存在");
        }
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
