package com.lmz.service;

import com.lmz.vo.HdfsFile;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class HdfsService {
    private String hdfsPath = "hdfs://192.168.44.189:9000/es";
    private String username = "lmz";
    private String userPath = hdfsPath + username;
//    public static void main(String[] args) throws IOException {
//        HdfsService hd = new HdfsService();
//        hd.copyFile("C:\\Users\\LMZ\\IdeaProjects\\myHdfs\\src\\main\\java\\com\\lmz\\hdfs\\TestHdfs.java");
//        hd.deleteFromHdfs(hd.hdfsPath+"TestHdfs.java");
//
//        hd.createdir("testfiles.txt");

//		InputStream ism = hd.getFileInputStreamForPath("hdfs://192.168.36.173:9000/input/jdk-8u181-linux-x64.tar.gz");
//		int len = 0;
//		byte[] buf = new byte[1024];
//		FileOutputStream fos = new FileOutputStream("d:/jdk.tar.gz");
//
//		while((len=ism.read(buf))!=-1){
//			fos.write(buf,0,len);
//		}
//		fos.close();
//    }

    /**
     * 上传文件
     * @param local 本地路径
     * @throws IOException
     */
    public void copyFile(String local,String currentPath) throws IOException {
        //指定当前的Hadoop的用户
        System.setProperty("HADOOP_USER_NAME", "root");
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(currentPath),conf);
        //remote---/用户/用户下的文件或文件夹
        fs.copyFromLocalFile(new Path(local), new Path(currentPath));
        System.out.println("copy from: " + local + " to " + currentPath);
        fs.close();
    }

    /**
     * 删除hdfs中的文件
     * @param deletePath  hdfs文件的绝对路径
     */
    public  void deleteFromHdfs(String deletePath)  {
        //指定当前的Hadoop的用户
        System.setProperty("HADOOP_USER_NAME", "root");
        Configuration conf = new Configuration();
        try{

            FileSystem fs = FileSystem.get(URI.create(deletePath), conf);
            fs.deleteOnExit(new Path(deletePath));
            fs.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }


    /**
     * 创建新目录
     * @param dirpath  要创建的文件夹的名称
     */
    public void createdir(String dirpath){
        try {
            //指定当前的Hadoop的用户
            System.setProperty("HADOOP_USER_NAME", "root");

            String dirname= dirpath;

            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(URI.create(dirname), conf);
            Path f=new Path(dirname);
            if (!fs.exists(new Path(dirname))) {
                //创建文件夹
                fs.mkdirs(f);
                System.out.println("创建成功！");
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 创建新文件
     * @param filePath  要创建的文件名称
     */
    public void createFile(String filePath){
        try {
            //指定当前的Hadoop的用户
            System.setProperty("HADOOP_USER_NAME", "root");

            String dirname= hdfsPath+filePath;

            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(URI.create(dirname), conf);
            Path f=new Path(dirname);
            if (!fs.exists(new Path(dirname))) {
                //创建文件
                fs.create(f);
                System.out.println("创建成功！");
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 获取文件输入流
     * @param strpath 文件的hdfs绝对路径
     * @return
     * @throws IOException
     */
    public InputStream getFileInputStreamForPath(String strpath) throws IOException{
        //指定当前的Hadoop的用户
        System.setProperty("HADOOP_USER_NAME", "root");
        Configuration conf = new Configuration();
        conf.set("fs.default.name", strpath);
        FileSystem fs = FileSystem.get(conf);
        return fs.open(new Path(strpath));
    }



    /**遍历HDFS上的文件和目录*/
    public List<HdfsFile> getDirectoryFromHdfs(String path)  {
        //指定当前的Hadoop的用户
        System.setProperty("HADOOP_USER_NAME", "root");
        Configuration conf = new Configuration();
        List<HdfsFile> list=new ArrayList<>();
        try{
            FileSystem fs = FileSystem.get(URI.create(path), conf);
            FileStatus[] fileStatuses = fs.listStatus(new Path(path));
            if(fileStatuses != null)
                for (FileStatus f : fileStatuses) {
                    System.out.printf("name: %s, file: %s, size: %d, Path: %s, ModificaionTime: %d\n", f.getPath(), f.isFile(), f.getLen(),f.getPath(),f.getModificationTime());
                    HdfsFile hf = new HdfsFile(f.getPath().getName(), f.isFile(), f.getLen(),f.getPath(),f.getModificationTime());
                    list.add(hf);
                }
            fs.close();
        }catch(Exception ex){

        }
        return  list;
    }

//    /**遍历HDFS上的文件和目录*/
//    public  FileStatus[] getDirectoryFromHdfs(String path) throws FileNotFoundException,IOException {
//
//        //指定当前的Hadoop的用户
//        System.setProperty("HADOOP_USER_NAME", "root");
//        FileStatus[] list=null;
//        try{
//            Configuration conf = new Configuration();
//            String dst = hdfsPath;
//            if(path.length()>0){
//                dst=path;
//            }
//            FileSystem fs = FileSystem.get(URI.create(dst), conf);
//            list = fs.listStatus(new Path(dst));
//            if(list != null)
//                for (FileStatus f : list) {
//                    System.out.printf("name: %s, folder: %s, size: %d\n", f.getPath().getName(), f.isDir(), f.getLen());
//                }
//            fs.close();
//
//        }catch(Exception ex){
//
//        }
//        return  list;
//    }
    public void rename(String oldname,String newname) throws IOException {
        System.setProperty("HADOOP_USER_NAME", "root");
//        oldname= hdfsPath+oldname;
//        newname= hdfsPath+newname;
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(oldname), conf);
        Path from = new Path(oldname);
        Path to = new Path(newname);
        fs.rename(from,to);
        fs.close();

    }

}
