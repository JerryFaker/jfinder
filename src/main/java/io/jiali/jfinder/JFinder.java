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
        }
        else
        {
            System.out.println("目录不存在！");
        }
    }

    void list(File f) {
        if (f.exists() && f.isDirectory())
        {
            File[] files = f.listFiles();
            for (File file : files) {
                System.out.println(file.getName());
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
            System.out.println("新目录："+f.getName()+"成功创建");

        }
        else
        {
            System.out.println("已存在同名目录!");
        }
    }

    void copyDir(File src, File dest) {
        if (src.isDirectory())
        {
            if (!dest.exists())
            {
                dest.mkdir();

                String fs[] = src.list();
                for (String f : fs) {
                    copyDir(new File(src, f), new File(dest, f));
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
}
