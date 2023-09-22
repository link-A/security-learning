package org.luo.security.controller;

import org.luo.security.entity.Users;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @GetMapping("/test/hello")
    public String hello(){
        return "hello world";
    }
    @RequestMapping("/test/index")
    public String indexTest(){
        return "hello admin";
    }
    @RequestMapping("/testSecured")
    @Secured({"ROLE_sale","ROLE_admin"})
    public  String  helloUser()  {
        return  "hello,user";
    }

    @RequestMapping("/testPreAuth")
    @PreAuthorize("hasAnyAuthority('admins')")
    public String preAuth(){
        return "hello PreAuthorize";
    }
    @RequestMapping("/testPostAuth")
    @PostAuthorize("hasAnyAuthority('admin')")
    public String postAuth(){
        System.out.println("PostAuthorize已执行");
        return "hello PostAuthorize";
    }
    @RequestMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('admins')")
    @PostFilter("filterObject.username  ==  'admin1'")
    @ResponseBody
    public List<Users> getAllUser(){
        ArrayList<Users>  list  =  new ArrayList<>();
        list.add(new Users(1l,"admin1","6666"));
        list.add(new Users(2l,"admin2","888"));
        System.out.println(list);
        return list;
    }
    @RequestMapping("/getTestPreFilter")
//    @PreAuthorize("hasRole('ROLE_sale')")
    @PostAuthorize("hasAnyAuthority('admins')")
    @PreFilter(value  =  "filterObject.id%2==0")
    @ResponseBody
    public List<Users> getTestPreFilter(@RequestBody List<Users> list){
        list.forEach((s)->  System.out.println("ID:"+s.getId()+"UserName:"+s.getUsername()));
        return list;
    }
}
