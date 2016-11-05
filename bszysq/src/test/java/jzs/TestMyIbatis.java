package jzs;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.bszy.admin.mapper.CategoryMapper;
import com.bszy.admin.mapper.UserMapper;
import com.bszy.admin.pojo.Category;
import com.bszy.admin.pojo.User;

public class TestMyIbatis {
	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;

	static {
		try {
			reader = Resources.getResourceAsReader("Configuration.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SqlSessionFactory getSession() {
		return sqlSessionFactory;
	}
	
	public static void test_simple(){
		SqlSession session = sqlSessionFactory.openSession();
		try {
			User user = (User) session.selectOne("com.bszy.admin.mapper.UserMapper.get", 1001L);
			System.out.println(user);
		} finally {
			session.close();
		}
	}
	
	public static void test_face() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserMapper userMpr = session.getMapper(UserMapper.class);
            User user = userMpr.get(1002L);
            System.out.println(user);
            
            CategoryMapper cateMapper = session.getMapper(CategoryMapper.class);
            Category cate = cateMapper.get(1L);
            System.out.println(cate);
        } finally {
            session.close();
        }
    }

	public static void main(String[] args) {
//		test_simple();
		test_face();
	}
}
