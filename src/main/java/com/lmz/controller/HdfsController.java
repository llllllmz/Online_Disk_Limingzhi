package com.lmz.controller;

import com.lmz.service.HdfsService;
import com.lmz.vo.HdfsFile;
import com.lmz.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class HdfsController {
    private String hdfsPath = "hdfs://192.168.44.189:9000/";
    private String username;
    private String userPath;

    @Autowired
    private HdfsService hdfsService;

    @RequestMapping("/index")
    public String index(ModelMap map,HttpSession session){
        User user = (User) session.getAttribute("user_login");
        map.put("user",user);
        hdfsService.createdir(hdfsPath+user.getUsername());
        return "layout/index";
    }

    @RequestMapping("/lists")
    public String lists(ModelMap map, @RequestParam(value="path", required=false) String path,HttpSession session) throws IOException {
        User user = (User)session.getAttribute("user_login");
        username=user.getUsername();
        userPath=hdfsPath+username;
        List<HdfsFile> lists;
        if(path == null)
        {lists = hdfsService.getDirectoryFromHdfs(userPath);
          map.put("currentPath",userPath);
            map.put("relativePath","/");
        }
        else
        {lists = hdfsService.getDirectoryFromHdfs(path);
            map.put("currentPath",path);

            String str=path.replace(userPath,"");
            System.out.println(str);
                if(!str.isEmpty())
                    map.put("relativePath",str);
                else
                    map.put("relativePath","/");


        }
        map.put("lists",lists);

        return "lists";
    }

    @RequestMapping("/last")
    public String last(ModelMap map, @RequestParam(value="path", required=false) String path,HttpSession session) throws IOException {
        User user = (User)session.getAttribute("user_login");
        username=user.getUsername();
        userPath=hdfsPath+username;
        System.out.println(path);
        String str=path.replace(userPath,"");
        System.out.println(str);
        if(str.lastIndexOf("/")!=-1){
            str=str.substring(0,str.lastIndexOf("/"));
//            map.put("relativePath",str);
        }
        System.out.println(str);
        return "redirect:lists?path="+userPath+str;
    }

    @RequestMapping("/del")
    public String del(String name){
        hdfsService.deleteFromHdfs(name);
        return "redirect:lists";

    }
    @RequestMapping("/addForm")
    public String addForm(){

        return "add";

    }
    @RequestMapping("/mkdirForm")
    public String mkdirForm(){
        return "mkdir";
    }

    @RequestMapping("/mkdir")
    public String mkdir(String dirname,String currentPath){
        hdfsService.createdir(currentPath+"/"+dirname);
        return "redirect:lists?path="+currentPath;
    }

    @RequestMapping(value = "/addFile",method = RequestMethod.POST)
    public String addFile(MultipartFile upfile,String currentPath) throws IOException {

        String originalFilename = upfile.getOriginalFilename();
        File file = new File("d:/upfile/" + originalFilename);
        upfile.transferTo(file);
        System.out.println(currentPath);
        hdfsService.copyFile(file.getAbsolutePath(),currentPath);
        return "redirect:lists?path="+currentPath;

    }
    @RequestMapping("/download")
    public void download(String path, HttpSession session, HttpServletResponse response) throws IOException {

//        filePath="hdfs://192.168.44.189:9000/es/"+filePath;
        //?????????????????????
        File files = new File(path);

        //??????????????????
        response.setContentType("image/jpeg;charset=UTF-8");
        /**
         * ???????????????
         * Content-Disposition????????????????????????
         * inline ??? attachment
         * inline ???????????????????????????????????????
         * attachment???????????????????????????????????????
         */
        response.setHeader("Content-Disposition","attachment; filename="+files.getName()+"");

        //??????????????????
        InputStream fis = hdfsService.getFileInputStreamForPath(path);
        int len=0;
        byte[] buf = new byte[512];

        //??????response????????????
        ServletOutputStream outputStream = response.getOutputStream();

        //??????????????????response???????????? ??????????????????
        while((len=fis.read(buf))!=-1){
            outputStream.write(buf,0,len);
        }
        //????????????
        fis.close();

        //??????????????????

    }

    @RequestMapping("/renameForm")
    public String renameForm(String name,ModelMap map){
        map.put("oldname",name);
        return "renameForm";
    }


    @RequestMapping("/rename")
    public String rename(String oldname,String newname,String currentPath) throws IOException {
        hdfsService.rename(currentPath+'/'+oldname,currentPath+'/'+newname);
        return "redirect:lists";
    }


}
