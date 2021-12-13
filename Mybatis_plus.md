## 简介 

是什么？ MyBatis 本来就是简化 JDBC 操作的！ 

官网：https://mp.baomidou.com/ MyBatis Plus，简化 MyBatis ！

## 特性

- **无侵入**：只做增强不做改变，引入它不会对现有工程产生影响，如丝般顺滑 
- **损耗小**：启动即会自动注入基本 CURD，性能基本无损耗，直接面向对象操作， BaseMapper
- **强大的 CRUD 操作**：内置通用 Mapper、通用 Service，仅仅通过少量配置即可实现单表大部分 CRUD 操作，更有强大的条件构造器，满足各类使用需求, 以后简单的CRUD操作，它不用自己编写 了！ 
- **支持 Lambda 形式调用**：通过 Lambda 表达式，方便的编写各类查询条件，无需再担心字段写错 
- **支持主键自动生成**：支持多达 4 种主键策略（内含分布式唯一 ID 生成器 - Sequence），可自由配 置，完美解决主键问题
-  **支持 ActiveRecord 模式**：支持 ActiveRecord 形式调用，实体类只需继承 Model 类即可进行强大 的 CRUD 操作 
- **支持自定义全局通用操作**：支持全局通用方法注入（ Write once, use anywhere ） 
- **内置代码生成器**：采用代码或者 Maven 插件可快速生成 Mapper 、 Model 、 Service 、 Controller 层代码，支持模板引擎，更有超多自定义配置等您来使用（自动帮你生成代码） 
- **内置分页插件**：基于 MyBatis 物理分页，开发者无需关心具体操作，配置好插件之后，写分页等同 于普通 List 查询 
- **分页插件支持多种数据库**：支持 MySQL、MariaDB、Oracle、DB2、H2、HSQL、SQLite、 Postgre、SQLServer 等多种数据库
- **内置性能分析插件**：可输出 Sql 语句以及其执行时间，建议开发测试时启用该功能，能快速揪出慢 查询 
- **内置全局拦截插件**：提供全表 delete 、 update 操作智能分析阻断，也可自定义拦截规则，预防误 操作

## 快速入门

地址：https://mp.baomidou.com/guide/quick-start.html#

初始化工程 使用第三方组件：

1、导入对应的依赖 

2、研究依赖如何配置 

3、代码如何编写

4、提高扩展技术能力！

#### 1、创建数据库 mybatis_plus

#### 2、创建user表

```sql
DROP TABLE IF EXISTS user;
CREATE TABLE user
(
id BIGINT(20) NOT NULL COMMENT '主键ID',
name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
age INT(11) NULL DEFAULT NULL COMMENT '年龄',
email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
PRIMARY KEY (id)
);
INSERT INTO user (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');
-- 真实开发中，version（乐观锁）、deleted（逻辑删除）、gmt_create、gmt_modified

```

#### 3、编写项目，初始化项目！使用SpringBoot初始化！ 

#### 4、导入依赖

```xml
<!-- 数据库驱动 -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
<!-- lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
<!-- mybatis-plus -->
<!-- mybatis-plus 是自己开发，并非官方的！ -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.0.5</version>
</dependency>
```

说明：我们使用 mybatis-plus 可以节省我们大量的代码，尽量不要同时导入 mybatis 和 mybatisplus！版本的差异！

#### 5、连接数据库！

这一步和 mybatis 相同！

```properties
# mysql 5 驱动不同 com.mysql.jdbc.Driver
# mysql 8 驱动不同com.mysql.cj.jdbc.Driver、需要增加时区的配置
serverTimezone=GMT%2B8
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.url=jdbc:mysql://localhost:3306/mybatis_plus?
useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

6、传统方式pojo-dao（连接mybatis，配置mapper.xml文件）-service-controller

#### 6、使用了mybatis-plus 

pojo

```
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
```

mapper接口

```java
// 在对应的Mapper上面继承基本的类 BaseMapper
@Repository // 代表持久层
public interface UserMapper extends BaseMapper<User> {
// 所有的CRUD操作都已经编写完成了
// 你不需要像以前的配置一大堆文件了！
}
```

注意点，我们需要在主启动类上去扫描我们的mapper包下的所有接口

 @MapperScan("com.kuang.mapper")

```java
@SpringBootTest
class MybatisPlusApplicationTests {
	// 继承了BaseMapper，所有的方法都来自己父类
    // 我们也可以编写自己的扩展方法！

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
    // 参数是一个 Wrapper ，条件构造器，这里我们先不用 null
// 查询全部用户
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

}
```

## 配置日志

```properties
mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
```

默认

## CRUD

#### Insert 插入

```java
@Test
public void testInsert(){
    User user = new User();
    user.setName("狂神说Java");
    user.setAge(3);
    user.setEmail("24736743@qq.com");
    int result = userMapper.insert(user); // 帮我们自动生成id
    System.out.println(result); // 受影响的行数
    System.out.println(user); // 发现，id会自动回填
}
```

数据库插入的id的默认值为：全局的唯一id

#### 主键生成策略

**默认 ID_WORKER 全局唯一id**

分布式系统唯一id生成：https://www.cnblogs.com/haoxinyue/p/5208136.html

雪花算法：

 snowflake是Twitter开源的分布式ID生成算法，结果是一个long型的ID。其核心思想是：使用41bit作为 毫秒数，10bit作为机器的ID（5个bit是数据中心，5个bit的机器ID），12bit作为毫秒内的流水号（意味 着每个节点在每毫秒可以产生 4096 个 ID），最后还有一个符号位，永远是0。可以保证几乎全球唯 一！

#### 主键自增

实体类字段上 @TableId(type = IdType.AUTO)

数据库字段一定要是自增！

```java
public enum IdType {
    AUTO(0), // 数据库id自增
    NONE(1), // 未设置主键
    INPUT(2), // 手动输入
    ID_WORKER(3), // 默认的全局唯一id
    UUID(4), // 全局唯一id uuid
    ID_WORKER_STR(5); //ID_WORKER 字符串表示法
}
```

#### 更新操作

```java
// 测试更新
@Test
public void testUpdate(){
    User user = new User();
    // 通过条件自动拼接动态sql
    user.setId(5L);
    user.setName("qolll");
    user.setAge(18);
    // 注意：updateById 但是参数是一个 对象！
    int i = userMapper.updateById(user);
    System.out.println(i);
}
```

所有的sql都是自动帮你动态配置的！

#### 自动填充 

创建时间、修改时间！这些个操作一遍都是自动化完成的，我们不希望手动更新！ 阿里巴巴开发手册：所有的数据库表：gmt_create、gmt_modified几乎所有的表都要配置上！而且需 要自动化！

##### 方式一：数据库级别（工作中不允许你修改数据库）

![image-20211213130712979](C:\Users\86178\AppData\Roaming\Typora\typora-user-images\image-20211213130712979.png)

在表中新增字段 create_time, update_time

再次测试插入方法，我们需要先把实体类同步！

```java
private Date createTime;
private Date updateTime;
```

##### 方式二：代码级别

1、删除数据库的默认值、更新操作！

![image-20211213130658051](C:\Users\86178\AppData\Roaming\Typora\typora-user-images\image-20211213130658051.png)

2、实体类字段属性上需要增加注解

```java
    @TableField(fill = FieldFill.INSERT)
    private Data createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Data updateTime;
```

3、编写处理器来处理这个注解即可！

```java
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    //插入時填充策略
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);

    }

    //更新時填充策略
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
```

#### 乐观锁

乐观锁 : 故名思意十分乐观，它总是认为不会出现问题，无论干什么不去上锁！如果出现了问题， 再次更新值测试 

悲观锁：故名思意十分悲观，它总是认为总是出现问题，无论干什么都会上锁！再去操作！

乐观锁实现方式： 

- 取出记录时，获取当前 version 
- 更新时，带上这个version 
- 执行更新时， set version = newVersion where version = oldVersion 
- 如果version不对，就更新失败

```sql
乐观锁：1、先查询，获得版本号 version = 1
-- A
update user set name = "kuangshen", version = version + 1
where id = 2 and version = 1
-- B 线程抢先完成，这个时候 version = 2，会导致 A 修改失败！
update user set name = "kuangshen", version = version + 1
where id = 2 and version = 1
```

**MP的乐观锁插件**

1、给数据库中增加version字段！

![image-20211213141728499](C:\Users\86178\AppData\Roaming\Typora\typora-user-images\image-20211213141728499.png)

2、我们实体类加对应的字段

```java
@Version //乐观锁Version注解
private Integer version;
```

3、注册组件

```java
@Configuration
@EnableTransactionManagement
@MapperScan("com.coderm.mapper")
public class mybatisPlusConfig {

    //注册乐观锁插件
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}
```

4、测试

```
// 测试乐观锁成功！
@Test
public void testOptimisticLocker(){
    // 1、查询用户信息
    User user = userMapper.selectById(1L);
    // 2、修改用户信息
    user.setName("coderm02222");
    user.setEmail("24736743@qq.com");
    // 3、执行更新操作
    userMapper.updateById(user);
}

// 测试乐观锁失败！多线程下
@Test
public void testOptimisticLocker2(){
    // 线程 1
    User user = userMapper.selectById(1L);
    user.setName("coderdddsa111");
    user.setEmail("24736743@qq.com");
    // 模拟另外一个线程执行了插队操作
    User user2 = userMapper.selectById(1L);
    user2.setName("dasdas222");
    user2.setEmail("24736743@qq.com");
    userMapper.updateById(user2);
    // 自旋锁来多次尝试提交！
    userMapper.updateById(user); // 如果没有乐观锁就会覆盖插队线程的值！
}
```

#### 查询操作

```
// 测试查询
@Test
public void testSelectById(){
    User user = userMapper.selectById(1L);
    System.out.println(user);
}
// 测试批量查询！
@Test
public void testSelectByBatchId(){
    List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
    users.forEach(System.out::println);
}
// 按条件查询之一使用map操作
@Test
public void testSelectByBatchIds(){
    HashMap<String, Object> map = new HashMap<>();
    // 自定义要查询
    map.put("name","狂神说Java");
    map.put("age",3);
    List<User> users = userMapper.selectByMap(map);
    users.forEach(System.out::println);
}
```

#### 分页查询

1、原始的 limit 进行分页 

2、pageHelper 第三方插件 

3、MP 其实也内置了分页插件！

##### 1、配置拦截器组件即可

```java
// 分页插件
@Bean
public PaginationInterceptor paginationInterceptor() {
    return new PaginationInterceptor();
}
```

##### 2、直接使用Page对象即可！

```java
// 测试分页查询
@Test
public void testPage(){
    // 参数一：当前页
    // 参数二：页面大小
    // 使用了分页插件之后，所有的分页操作也变得简单的！
    Page<User> page = new Page<>(2,5);
    userMapper.selectPage(page,null);
    page.getRecords().forEach(System.out::println);
    System.out.println(page.getTotal());
}
```

#### 删除操作

1、根据 id 删除记录

```java
// 测试删除
@Test
public void testDeleteById(){
    userMapper.deleteById(1240620674645544965L);
}
// 通过id批量删除
@Test
public void testDeleteBatchId(){
    userMapper.deleteBatchIds(Arrays.asList(1240620674645544961L,1240620674645544962L));
}
// 通过map删除
@Test
public void testDeleteMap() {
    HashMap<String, Object> map = new HashMap<>();
    map.put("name", "coderm");
    userMapper.deleteByMap(map);
}
```

#### 逻辑删除

物理删除 ：从数据库中直接移除  

逻辑删除 ：再数据库中没有被移除，而是通过一个变量来让他失效！ deleted = 0 => deleted = 1

1、在数据表中增加一个 deleted 字段

```sql
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `age` int DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `deleted` int DEFAULT '0',
  `version` int DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1470253911214878724 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

2、实体类中增加属性

```java
@TableLogic //逻辑删除
private Integer deleted;
```

3、配置！

```java
// 逻辑删除组件！
@Bean
public ISqlInjector sqlInjector() {
    return new LogicSqlInjector();
}
```

```properties
# 配置逻辑删除
mybatis-plus.global-config.db-config.logic-delete-value=1
mybatis-plus.global-config.db-config.logic-not-delete-value=0
```

4、测试！

```java
// 测试删除
@Test
public void testDeleteById(){
    userMapper.deleteById(1240620674645544965L);
}
```

## 性能分析插件

我们在平时的开发中，会遇到一些慢sql。测试！ druid,,,,, 作用：性能分析拦截器，用于输出每条 SQL 语句及其执行时间 MP也提供性能分析插件，如果超过这个时间就停止运行！

1、导入插件

```java
/**
 * SQL执行效率插件
 */
@Bean
@Profile({"dev","test"})// 设置 dev test 环境开启，保证我们的效率
public PerformanceInterceptor performanceInterceptor() {
    PerformanceInterceptor performanceInterceptor = new
            PerformanceInterceptor();
    performanceInterceptor.setMaxTime(100); // ms设置sql执行的最大时间，如果超过了则不执行
    performanceInterceptor.setFormat(true); // 是否格式化代码
    return performanceInterceptor;
}
```

记住，要在SpringBoot中配置环境为dev或者 test 环境！

```properties
#设置开发环境
spring.profiles.active=dev
```

2、测试使用！

```java
@Test
void contextLoads() {
    List<User> users = userMapper.selectList(null);
    users.forEach(System.out::println);
}
```

## 条件构造器

## 条件构造器

1、测试一，记住查看输出的SQL进行分析

```java
    void contextLoadsw() {
// 查询name不为空的用户，并且邮箱不为空的用户，年龄大于等于12
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .isNotNull("name")
                .isNotNull("email")
                .ge("age",12);
        userMapper.selectList(wrapper).forEach(System.out::println); // 和我们刚才学习的map对比一下
    }
```

2、测试二，记住查看输出的SQL进行分析

```java
@Test
void test2(){
    // 查询名字狂神说
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    wrapper.eq("name","狂神说");
    User user = userMapper.selectOne(wrapper); // 查询一个数据，出现多个结果使用List或者 Map
    System.out.println(user);
}
```

3、测试三，记住查看输出的SQL进行分析

```java
    @Test
    void test3(){
// 查询年龄在 20 ~ 30 岁之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age",20,30); // 区间
        Integer count = userMapper.selectCount(wrapper);// 查询结果数
        System.out.println(count);
    }
```

4、测试四，记住查看输出的SQL进行分析

```java
    // 模糊查询
    @Test
    void test4(){
// 查询年龄在 20 ~ 30 岁之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
// 左和右 t%
        wrapper
                .notLike("name","e")
                .likeRight("email","t");
        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }
```

5、测试五（）

```java
    // 模糊查询
    @Test
    void test5(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
// id 在子查询中查出来
        wrapper.inSql("id","select id from user where id<3");
        List<Object> objects = userMapper.selectObjs(wrapper);
        objects.forEach(System.out::println);
    }
```



6、测试六

```java
//测试六
    @Test
    void test6(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
// 通过id进行排序
        wrapper.orderByAsc("id");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }
```

说明:

- 以下出现的第一个入参`boolean condition`表示该条件**是否**加入最后生成的sql中，例如：query.like(StringUtils.isNotBlank(name), Entity::getName, name) .eq(age!=null && age >= 0, Entity::getAge, age)
- 以下代码块内的多个方法均为从上往下补全个别`boolean`类型的入参,默认为`true`
- 以下出现的泛型`Param`均为`Wrapper`的子类实例(均具有`AbstractWrapper`的所有方法)
- 以下方法在入参中出现的`R`为泛型,在普通wrapper中是`String`,在LambdaWrapper中是**函数**(例:`Entity::getId`,`Entity`为实体类,`getId`为字段`id`的**getMethod**)
- 以下方法入参中的`R column`均表示数据库字段,当`R`具体类型为`String`时则为数据库字段名(**字段名是数据库关键字的自己用转义符包裹!**)!而不是实体类数据字段名!!!,另当`R`具体类型为`SFunction`时项目runtime不支持eclipse自家的编译器!!!
- 以下举例均为使用普通wrapper,入参为`Map`和`List`的均以`json`形式表现!
- 使用中如果入参的`Map`或者`List`为**空**,则不会加入最后生成的sql中!!!
- 有任何疑问就点开源码看,看不懂**函数**的[点击我学习新知识](https://www.jianshu.com/p/613a6118e2e0)

[ ](https://www.jianshu.com/p/613a6118e2e0)

- [ (opens new window)](https://www.jianshu.com/p/613a6118e2e0)

警告:

不支持以及不赞成在 RPC 调用中把 Wrapper 进行传输

1. wrapper 很重
2. 传输 wrapper 可以类比为你的 controller 用 map 接收值(开发一时爽,维护火葬场)
3. 正确的 RPC 调用姿势是写一个 DTO 进行传输,被调用方再根据 DTO 执行相应的操作
4. 我们拒绝接受任何关于 RPC 传输 Wrapper 报错相关的 issue 甚至 pr

### AbstractWrapper

说明:

QueryWrapper(LambdaQueryWrapper) 和 UpdateWrapper(LambdaUpdateWrapper) 的父类
 用于生成 sql 的 where 条件, entity 属性也用于生成 sql 的 where 条件
 注意: entity 生成的 where 条件与 使用各个 api 生成的 where 条件**没有任何关联行为**

### [#](https://mp.baomidou.com/guide/wrapper.html#alleq) allEq

```java
allEq(Map<R, V> params)
allEq(Map<R, V> params, boolean null2IsNull)
allEq(boolean condition, Map<R, V> params, boolean null2IsNull)
```

- 全部

  eq

  (或个别

  isNull

  )

  个别参数说明:

  `params` : `key`为数据库字段名,`value`为字段值
   `null2IsNull` : 为`true`则在`map`的`value`为`null`时调用 [isNull](https://mp.baomidou.com/guide/wrapper.html#isnull) 方法,为`false`时则忽略`value`为`null`的

- 例1: `allEq({id:1,name:"老王",age:null})`--->`id = 1 and name = '老王' and age is null`

- 例2: `allEq({id:1,name:"老王",age:null}, false)`--->`id = 1 and name = '老王'`

```java
allEq(BiPredicate<R, V> filter, Map<R, V> params)
allEq(BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull)
allEq(boolean condition, BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull) 
```

个别参数说明:

`filter` : 过滤函数,是否允许字段传入比对条件中
 `params` 与 `null2IsNull` : 同上

- 例1: `allEq((k,v) -> k.indexOf("a") >= 0, {id:1,name:"老王",age:null})`--->`name = '老王' and age is null`
- 例2: `allEq((k,v) -> k.indexOf("a") >= 0, {id:1,name:"老王",age:null}, false)`--->`name = '老王'`

### eq

```java
eq(R column, Object val)
eq(boolean condition, R column, Object val)
```

- 等于 =
- 例: `eq("name", "老王")`--->`name = '老王'`

### ne

```java
ne(R column, Object val)
ne(boolean condition, R column, Object val)
```

- 不等于 <>
- 例: `ne("name", "老王")`--->`name <> '老王'`

### gt

```java
gt(R column, Object val)
gt(boolean condition, R column, Object val)
```

- 大于 >
- 例: `gt("age", 18)`--->`age > 18`

### ge

```java
ge(R column, Object val)
ge(boolean condition, R column, Object val)
```

- 大于等于 >=
- 例: `ge("age", 18)`--->`age >= 18`

### lt

```java
lt(R column, Object val)
lt(boolean condition, R column, Object val)
```

- 小于 <
- 例: `lt("age", 18)`--->`age < 18`

### le

```java
le(R column, Object val)
le(boolean condition, R column, Object val)
```

- 小于等于 <=
- 例: `le("age", 18)`--->`age <= 18`

### between

```java
between(R column, Object val1, Object val2)
between(boolean condition, R column, Object val1, Object val2)
```

- BETWEEN 值1 AND 值2
- 例: `between("age", 18, 30)`--->`age between 18 and 30`

### notBetween

```java
notBetween(R column, Object val1, Object val2)
notBetween(boolean condition, R column, Object val1, Object val2)
```

- NOT BETWEEN 值1 AND 值2
- 例: `notBetween("age", 18, 30)`--->`age not between 18 and 30`

### like

```java
like(R column, Object val)
like(boolean condition, R column, Object val)
```

- LIKE '%值%'
- 例: `like("name", "王")`--->`name like '%王%'`

### notLike

```java
notLike(R column, Object val)
notLike(boolean condition, R column, Object val)
```

- NOT LIKE '%值%'
- 例: `notLike("name", "王")`--->`name not like '%王%'`

### likeLeft

```java
likeLeft(R column, Object val)
likeLeft(boolean condition, R column, Object val)
```

- LIKE '%值'
- 例: `likeLeft("name", "王")`--->`name like '%王'`

### likeRight

```java
likeRight(R column, Object val)
likeRight(boolean condition, R column, Object val)
```

- LIKE '值%'
- 例: `likeRight("name", "王")`--->`name like '王%'`

### isNull

```java
isNull(R column)
isNull(boolean condition, R column)
```

- 字段 IS NULL
- 例: `isNull("name")`--->`name is null`

### isNotNull

```java
isNotNull(R column)
isNotNull(boolean condition, R column)
```

- 字段 IS NOT NULL
- 例: `isNotNull("name")`--->`name is not null`

### in

```java
in(R column, Collection<?> value)
in(boolean condition, R column, Collection<?> value)
```

- 字段 IN (value.get(0), value.get(1), ...)
- 例: `in("age",{1,2,3})`--->`age in (1,2,3)`

```java
in(R column, Object... values)
in(boolean condition, R column, Object... values)
```

- 字段 IN (v0, v1, ...)
- 例: `in("age", 1, 2, 3)`--->`age in (1,2,3)`

### notIn

```java
notIn(R column, Collection<?> value)
notIn(boolean condition, R column, Collection<?> value)
```

- 字段 NOT IN (value.get(0), value.get(1), ...)
- 例: `notIn("age",{1,2,3})`--->`age not in (1,2,3)`

```java
notIn(R column, Object... values)
notIn(boolean condition, R column, Object... values)
```

- 字段 NOT IN (v0, v1, ...)
- 例: `notIn("age", 1, 2, 3)`--->`age not in (1,2,3)`

### inSql

```java
inSql(R column, String inValue)
inSql(boolean condition, R column, String inValue)
```

- 字段 IN ( sql语句 )
- 例: `inSql("age", "1,2,3,4,5,6")`--->`age in (1,2,3,4,5,6)`
- 例: `inSql("id", "select id from table where id < 3")`--->`id in (select id from table where id < 3)`

### notInSql

```java
notInSql(R column, String inValue)
notInSql(boolean condition, R column, String inValue)
```

- 字段 NOT IN ( sql语句 )
- 例: `notInSql("age", "1,2,3,4,5,6")`--->`age not in (1,2,3,4,5,6)`
- 例: `notInSql("id", "select id from table where id < 3")`--->`id not in (select id from table where id < 3)`

### groupBy

```java
groupBy(R... columns)
groupBy(boolean condition, R... columns)
```

- 分组：GROUP BY 字段, ...
- 例: `groupBy("id", "name")`--->`group by id,name`

### orderByAsc

```java
orderByAsc(R... columns)
orderByAsc(boolean condition, R... columns)
```

- 排序：ORDER BY 字段, ... ASC
- 例: `orderByAsc("id", "name")`--->`order by id ASC,name ASC`

### orderByDesc

```java
orderByDesc(R... columns)
orderByDesc(boolean condition, R... columns)
```

- 排序：ORDER BY 字段, ... DESC
- 例: `orderByDesc("id", "name")`--->`order by id DESC,name DESC`

### orderBy

```java
orderBy(boolean condition, boolean isAsc, R... columns)
```

- 排序：ORDER BY 字段, ...
- 例: `orderBy(true, true, "id", "name")`--->`order by id ASC,name ASC`

### having

```java
having(String sqlHaving, Object... params)
having(boolean condition, String sqlHaving, Object... params)
```

- HAVING ( sql语句 )
- 例: `having("sum(age) > 10")`--->`having sum(age) > 10`
- 例: `having("sum(age) > {0}", 11)`--->`having sum(age) > 11`

### func

```java
func(Consumer<Children> consumer)
func(boolean condition, Consumer<Children> consumer)
```

- func 方法(主要方便在出现if...else下调用不同方法能不断链)
- 例: `func(i -> if(true) {i.eq("id", 1)} else {i.ne("id", 1)})`

### or

```java
or()
or(boolean condition)
```

- 拼接 OR

  注意事项:

  主动调用`or`表示紧接着下一个**方法**不是用`and`连接!(不调用`or`则默认为使用`and`连接)

- 例: `eq("id",1).or().eq("name","老王")`--->`id = 1 or name = '老王'`

```java
or(Consumer<Param> consumer)
or(boolean condition, Consumer<Param> consumer)
```

- OR 嵌套
- 例: `or(i -> i.eq("name", "李白").ne("status", "活着"))`--->`or (name = '李白' and status <> '活着')`

### and

```java
and(Consumer<Param> consumer)
and(boolean condition, Consumer<Param> consumer)
```

- AND 嵌套
- 例: `and(i -> i.eq("name", "李白").ne("status", "活着"))`--->`and (name = '李白' and status <> '活着')`

### nested

```java
nested(Consumer<Param> consumer)
nested(boolean condition, Consumer<Param> consumer)
```

- 正常嵌套 不带 AND 或者 OR
- 例: `nested(i -> i.eq("name", "李白").ne("status", "活着"))`--->`(name = '李白' and status <> '活着')`

### [#](https://mp.baomidou.com/guide/wrapper.html#apply) apply

```java
apply(String applySql, Object... params)
apply(boolean condition, String applySql, Object... params)
```

- 拼接 sql

  注意事项:

  该方法可用于数据库**函数** 动态入参的`params`对应前面`applySql`内部的`{index}`部分.这样是不会有sql注入风险的,反之会有!

- 例: `apply("id = 1")`--->`id = 1`

- 例: `apply("date_format(dateColumn,'%Y-%m-%d') = '2008-08-08'")`--->`date_format(dateColumn,'%Y-%m-%d') = '2008-08-08'")`

- 例: `apply("date_format(dateColumn,'%Y-%m-%d') = {0}", "2008-08-08")`--->`date_format(dateColumn,'%Y-%m-%d') = '2008-08-08'")`

### [#](https://mp.baomidou.com/guide/wrapper.html#last) last

```java
last(String lastSql)
last(boolean condition, String lastSql)
```

- 无视优化规则直接拼接到 sql 的最后

  注意事项:

  只能调用一次,多次调用以最后一次为准 有sql注入的风险,请谨慎使用

- 例: `last("limit 1")`

### [#](https://mp.baomidou.com/guide/wrapper.html#exists) exists

```java
exists(String existsSql)
exists(boolean condition, String existsSql)
```

- 拼接 EXISTS ( sql语句 )
- 例: `exists("select id from table where age = 1")`--->`exists (select id from table where age = 1)`

### [#](https://mp.baomidou.com/guide/wrapper.html#notexists) notExists

```java
notExists(String notExistsSql)
notExists(boolean condition, String notExistsSql)
```

- 拼接 NOT EXISTS ( sql语句 )
- 例: `notExists("select id from table where age = 1")`--->`not exists (select id from table where age = 1)`

### QueryWrapper

说明:

继承自 AbstractWrapper ,自身的内部属性 entity 也用于生成 where 条件
 及 LambdaQueryWrapper, 可以通过 new QueryWrapper().lambda() 方法获取

### [#](https://mp.baomidou.com/guide/wrapper.html#select) select

```java
select(String... sqlSelect)
select(Predicate<TableFieldInfo> predicate)
select(Class<T> entityClass, Predicate<TableFieldInfo> predicate)
```

- 设置查询字段

  说明:

  以上方法分为两类.
   第二类方法为:过滤查询字段(主键除外),入参不包含 class 的调用前需要`wrapper`内的`entity`属性有值! 这两类方法重复调用以最后一次为准

- 例: `select("id", "name", "age")`

- 例: `select(i -> i.getProperty().startsWith("test"))`

### UpdateWrapper

说明:

继承自 `AbstractWrapper` ,自身的内部属性 `entity` 也用于生成 where 条件
 及 `LambdaUpdateWrapper`, 可以通过 `new UpdateWrapper().lambda()` 方法获取!

### [#](https://mp.baomidou.com/guide/wrapper.html#set) set

```java
set(String column, Object val)
set(boolean condition, String column, Object val)
```

- SQL SET 字段
- 例: `set("name", "老李头")`
- 例: `set("name", "")`--->数据库字段值变为**空字符串**
- 例: `set("name", null)`--->数据库字段值变为`null`

### [#](https://mp.baomidou.com/guide/wrapper.html#setsql) setSql

```java
setSql(String sql)
```

- 设置 SET 部分 SQL
- 例: `setSql("name = '老李头'")`

### [#](https://mp.baomidou.com/guide/wrapper.html#lambda) lambda

- 获取 `LambdaWrapper`
   在`QueryWrapper`中是获取`LambdaQueryWrapper`
   在`UpdateWrapper`中是获取`LambdaUpdateWrapper`

### 使用 Wrapper 自定义SQL

注意事项:

需要`mybatis-plus`版本 >= `3.0.7` param 参数名要么叫`ew`,要么加上注解`@Param(Constants.WRAPPER)` 使用`${ew.customSqlSegment}` 不支持 `Wrapper` 内的entity生成where语句

### [#](https://mp.baomidou.com/guide/wrapper.html#kotlin持久化对象定义最佳实践) kotlin持久化对象定义最佳实践

由于`kotlin`相比于`java`多了数据对象（`data class`)，在未说明情况下可能会混用。建议按照以下形式定义持久化对象

```kotlin
@TableName("sys_user")
class User {
		@TableId(type = IdType.AUTO)
    var id: Int? = null

    @TableField("username")
    var name: String? = null

    var roleId: Int? = null
}
```

**注意**：这里的`TableId`及`TableField`并非必要，只是为了展示`Mybatis-Plus`中的`annotation`使用

这里所有成员都需要定义为可空类型（`?`），并赋予`null`的初始值，方便我们在以下场景中使用（类似java中的`updateSelective`）

```kotlin
val wrapper = KtUpdateWrapper(User::class.java).eq(User::id, 2)
val newRecord = User()
newRecord.name = "newName"
userMapper!!.update(newRecord, wrapper)
```

不建议使用`data class`及全参数构造方法，这样我们会写很多不必要的`null`来构造一个空对象

### [#](https://mp.baomidou.com/guide/wrapper.html#用注解) 用注解

```java
@Select("select * from mysql_data ${ew.customSqlSegment}")
List<MysqlData> getAll(@Param(Constants.WRAPPER) Wrapper wrapper);
```

### [#](https://mp.baomidou.com/guide/wrapper.html#用xml) 用XML

```java
List<MysqlData> getAll(Wrapper ew);
<select id="getAll" resultType="MysqlData">
	SELECT * FROM mysql_data ${ew.customSqlSegment}
</select>
```

### [#](https://mp.baomidou.com/guide/wrapper.html#kotlin使用wrapper) kotlin使用wrapper

> kotlin 可以使用 `QueryWrapper` 和 `UpdateWrapper` 但无法使用 `LambdaQueryWrapper` 和 `LambdaUpdateWrapper`
>  如果想使用 lambda 方式的 wrapper 请使用 `KtQueryWrapper` 和 `KtUpdateWrapper`

请参考[实例](https://github.com/baomidou/mybatis-plus/blob/master/mybatis-plus-extension/src/test/kotlin/com/baomidou/mybatisplus/extension/kotlin/WrapperTest.kt)

[ ](https://github.com/baomidou/mybatis-plus/blob/master/mybatis-plus-extension/src/test/kotlin/com/baomidou/mybatisplus/extension/kotlin/WrapperTest.kt)

[ (opens new window)](https://github.com/baomidou/mybatis-plus/blob/master/mybatis-plus-extension/src/test/kotlin/com/baomidou/mybatisplus/extension/kotlin/WrapperTest.kt)

```kotlin
val queryWrapper = KtQueryWrapper(User()).eq(User::name, "sss").eq(User::roleId, "sss2")
userMapper!!.selectList(queryWrapper)

val updateConditionWrapper = KtUpdateWrapper(User()).eq(User::name, "sss").eq(User::roleId, "sss2")
val updateRecord = User()
updateRecord.name = "newName"
userMapper!!.update(updateRecord, updateConditionWrapper)

val updateRecord = User()
updateRecord.id = 2
updateRecord.name = "haha"
userMapper.updateById(updateRecord)
```

### [#](https://mp.baomidou.com/guide/wrapper.html#链式调用-lambda-式) 链式调用 lambda 式

```java
// 区分:
// 链式调用 普通
UpdateChainWrapper<T> update();
// 链式调用 lambda 式。注意：不支持 Kotlin 
LambdaUpdateChainWrapper<T> lambdaUpdate();

// 等价示例：
query().eq("id", value).one();
lambdaQuery().eq(Entity::getId, value).one();

// 等价示例：
update().eq("id", value).remove();
lambdaUpdate().eq(Entity::getId, value).remove();
```

## 代码生成器

```
public class CodeGenerator {

    @Test
    public void run() {

        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("testjava");
        gc.setOpen(false); //生成后是否打开资源管理器
        gc.setFileOverride(false); //重新生成时文件是否覆盖
        gc.setServiceName("%sService");	//去掉Service接口的首字母I
        gc.setIdType(IdType.ID_WORKER); //主键策略
        gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型
        gc.setSwagger2(true);//开启Swagger2模式

        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/guli");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 4、包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("edu"); //模块名
        pc.setParent("com.example.demo");
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("edu_teacher");
        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        strategy.setTablePrefix(pc.getModuleName() + "_"); //生成实体时去掉表前缀

        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作

        strategy.setRestControllerStyle(true); //restful api风格控制器
        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符

        mpg.setStrategy(strategy);


        // 6、执行
        mpg.execute();
    }
}
```

