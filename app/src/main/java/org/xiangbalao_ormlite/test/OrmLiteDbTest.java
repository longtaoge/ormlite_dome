package org.xiangbalao_ormlite.test;

import java.sql.SQLException;
import java.util.List;

import org.xiangbalao_ormlite.bean.Article;
import org.xiangbalao_ormlite.bean.Student;
import org.xiangbalao_ormlite.bean.User;
import org.xiangbalao_ormlite.db.ArticleDao;
import org.xiangbalao_ormlite.db.DatabaseHelper;
import org.xiangbalao_ormlite.db.UserDao;
import org.xiangbalao_ormlite.utils.L;

import android.test.AndroidTestCase;

import com.j256.ormlite.dao.Dao;


public class OrmLiteDbTest extends AndroidTestCase
{
	public void testAddArticle()
	{
		User u = new User();
		u.setName("张鸿洋");
		new UserDao(getContext()).add(u);
		Article article = new Article();
		article.setTitle("ORMLite的使用");
		article.setUser(u);
		new ArticleDao(getContext()).add(article);

	}

	public void testGetArticleById()
	{
		Article article = new ArticleDao(getContext()).get(1);
		L.e(article.getUser() + " , " + article.getTitle());
	}

	public void testGetArticleWithUser()
	{

		Article article = new ArticleDao(getContext()).getArticleWithUser(1);
		L.e(article.getUser() + " , " + article.getTitle());
	}

	public void testListArticlesByUserId()
	{

		List<Article> articles = new ArticleDao(getContext()).listByUserId(1);
		L.e(articles.toString());
	}

	public void testGetUserById()
	{
		User user = new UserDao(getContext()).get(1);
		L.e(user.getName());
		if (user.getArticles() != null)
			for (Article article : user.getArticles())
			{
				L.e(article.toString());
			}
	}

	public void testAddStudent() throws SQLException
	{
		Dao dao = DatabaseHelper.getHelper(getContext()).getDao(Student.class);
		Student student = new Student();
		student.setDao(dao);
		student.setName("张鸿洋");
		student.create();
	}



}
