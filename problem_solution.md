问题汇总
========



## 1. 修改初始页"/"实现

### **问题**： 

JHipster默认的初始页是index.html，被框架维护，每次生成新的Entity都会自动重写。

我要把"index.html"对应的路径移动到"/admin"。
把"/"留给自己的首页。

### **解决方案**：

#### 方案1：
自己写一个Controller，将路由"/"映射到自己的实现方法，一定要设置`@Order(Ordered.HIGHEST_PRECEDENCE)`来覆盖SpringBoot的默认路由。

创建路由"/admin",指向原来的"index.html"。

```java
@Controller
public class IndexController {
    @RequestMapping(value = "/")
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public String index() {
        return "redirect:/articles/0";
    }
    
    @RequestMapping(value = "/admin")
    public String management() {
        return "forward:/index.html";
    }
}

```

#### 方案2：

在Config中配置路由, 同样需要设置优先级覆盖SpringBoot默认路由。

```java
@Configuration
public class IndexWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/articles/0");
        registry.setOrder( Ordered.HIGHEST_PRECEDENCE );
        super.addViewControllers(registry);
    }
}
```

## 2. Thymeleaf模板修改后更新问题

### 问题
Thymeleaf模板修改不能更新，需要重启应用才可以，导致开发调试时效率低下。

### 解决方案

TODO


## 3. Layout模板里的数据获取问题

### 问题

比如菜单、侧边栏的数据，不应该在业务逻辑中获取，但是页面是后台渲染方式，所以也应该是后台统一设置，而不是前端用ajax获取。

工作项目上的前后端分离方式，和这个项目的方式有些不同。


### 解决方案

TODO











