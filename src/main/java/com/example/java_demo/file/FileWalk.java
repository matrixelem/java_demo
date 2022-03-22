package com.example.java_demo.file;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;


public class FileWalk {
    public static void main(String[] args) throws Exception{
        String path = "D:\\test\\112\\logcat";
        String grep = "featureCount featureSechedule";

        getAllFiles(path).forEach(file -> {
            try {
                Files.readAllLines(file).forEach(line -> {
                    if (line.contains(grep)){
                        System.out.println(line);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 遍历某个文件夹下所有文件(包括子目录)
     * @param srcDir
     * @return
     * @throws IOException
     */
    public static List<Path> getAllFiles(String srcDir) throws IOException {
        Path srcPath = Paths.get(srcDir);
        List<Path> list = new ArrayList<>();
        Files.walkFileTree(srcPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                list.add(file);
                return super.visitFile(file, attrs);
            }

        });
        return list;
    }
}
