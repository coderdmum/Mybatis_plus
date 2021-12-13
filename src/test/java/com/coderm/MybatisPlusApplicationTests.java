package com.coderm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coderm.mapper.UserMapper;
import com.coderm.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MybatisPlusApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    // 测试插入
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

    @Test
    void contextLoadsw() {
// 查询name不为空的用户，并且邮箱不为空的用户，年龄大于等于12
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .isNotNull("name")
                .isNotNull("email")
                .ge("age",12);
        userMapper.selectList(wrapper).forEach(System.out::println); // 和我们刚才学习的map对比一下
    }

    @Test
    void test2(){
        // 查询名字狂神说
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name","狂神说");
        User user = userMapper.selectOne(wrapper); // 查询一个数据，出现多个结果使用List或者 Map
        System.out.println(user);
    }

    @Test
    void test3(){
// 查询年龄在 20 ~ 30 岁之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age",20,30); // 区间
        Integer count = userMapper.selectCount(wrapper);// 查询结果数
        System.out.println(count);
    }

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

    // 模糊查询
    @Test
    void test5(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
// id 在子查询中查出来
        wrapper.inSql("id","select id from user where id<3");
        List<Object> objects = userMapper.selectObjs(wrapper);
        objects.forEach(System.out::println);
    }

    //测试六
    @Test
    void test6(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
// 通过id进行排序
        wrapper.orderByAsc("id");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }











}
