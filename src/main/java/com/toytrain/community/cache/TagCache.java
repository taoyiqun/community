package com.toytrain.community.cache;

import com.toytrain.community.dto.TagDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagCache {

    public static List<TagDTO> get() {
        List<TagDTO> tagDtos = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("java", "c/c++", "python", "golang", "c#", "php", "javascript", "scala", "html", "html5", "css", "node·js", "objective-c", "typescript", "shell", "swift", "sass", "ruby", "bash", "less", "asp·net", "lua", "coffeescript", "actionscript", "rust", "erlang", "perl"));
        tagDtos.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("spring", "springmvc", "springboot", "springcloud", "mybatis", "bootstrap", "express", "django", "flask", "yii", "ruby-on-rails", "tornado", "koa", "struts", "laravel"));
        tagDtos.add(framework);


        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux", "nginx", "docker", "apache", "ubuntu", "centos", "缓存", "tomcat", "负载均衡", "unix", "hadoop", "windows-server"));
        tagDtos.add(server);

        TagDTO db = new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql", "redis", "mongodb", "sql", "oracle", "nosql", "memcached", "elasticsearch", "sqlserver", "postgresql", "sqlite"));
        tagDtos.add(db);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("intellij-idea", "git", "github", "vscode", "vim", "sublime", "eclipse", "xcode", "maven", "ide", "svn", "android-studio", "atom", "emacs", "textmate", "hg"));
        tagDtos.add(tool);

        TagDTO other = new TagDTO();
        other.setCategoryName("其它");
        other.setTags(Arrays.asList("找bug", "测试", "冒泡", "交友", "生活", "电影", "音乐", "读书", "美食", "游戏", "科技", "数码", "理财"));
        tagDtos.add(other);
        return tagDtos;
    }
    public boolean isValid(String tags){
        
    }

}