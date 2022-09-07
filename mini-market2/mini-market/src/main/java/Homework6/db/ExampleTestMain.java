package Homework6.db;

import Homework6.db.model.Categories;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class ExampleTestMain {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        Homework6.db.dao.CategoriesMapper categoriesMapper = sqlSession.getMapper(Homework6.db.dao.CategoriesMapper.class);
        Homework6.db.model.CategoriesExample categoriesExample = new Homework6.db.model.CategoriesExample();

        categoriesExample.createCriteria().andIdEqualTo(1);



        Categories selected = categoriesMapper.selectByPrimaryKey(2L);
        System.out.println("ID: " + selected.getId() + "\ntitle: " + selected.getTitle());
    }
}