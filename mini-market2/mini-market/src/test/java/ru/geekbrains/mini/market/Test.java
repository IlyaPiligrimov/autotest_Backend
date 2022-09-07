package ru.geekbrains.mini.market;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class Test {
    static SqlSession session;
    @BeforeAll
    static void beforeAll() throws IOException {
        session = null;
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        session = sqlSessionFactory.openSession();
    }
    @org.junit.jupiter.api.Test
    void ChangeCategory() {
        Homework6.db.dao.CategoriesMapper categoriesMapper = session.getMapper(Homework6.db.dao.CategoriesMapper.class);
        Homework6.db.model.Categories selected = categoriesMapper.selectByPrimaryKey(1L);

        assertThat(selected.getId(),is(1L));
        assertThat(selected.getTitle(),is ("Food"));
    }
    @org.junit.jupiter.api.Test
    void ChangeProduct() {
        Homework6.db.dao.ProductsMapper productsMapper = session.getMapper(Homework6.db.dao.ProductsMapper.class);
        Homework6.db.model.Products selected = productsMapper.selectByPrimaryKey(1L);

        assertThat(selected.getId(),is(1L));
        assertThat(selected.getTitle(),is("Milk"));
    }



    @org.junit.jupiter.api.Test
    void CreateProduct() {
        Homework6.db.dao.ProductsMapper productsMapper = session.getMapper(Homework6.db.dao.ProductsMapper.class);
        Homework6.db.model.Products products = new Homework6.db.model.Products();
        products.setTitle("Fresh Meat");
        productsMapper.insert(products);
        assertThat(products.getTitle(),is("Fresh Meat"));
    }
}