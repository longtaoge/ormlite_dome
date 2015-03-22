package org.xiangbalao_ormlite;

import java.sql.SQLException;
import java.util.List;

import org.xiangbalao_ormlite.bean.Article;
import org.xiangbalao_ormlite.bean.Student;
import org.xiangbalao_ormlite.bean.User;
import org.xiangbalao_ormlite.db.ArticleDao;
import org.xiangbalao_ormlite.db.DatabaseHelper;
import org.xiangbalao_ormlite.db.UserDao;
import org.xiangbalao_ormlite.utils.L;

import com.j256.ormlite.dao.Dao;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener
{
    private Button button1, button2, button3, button4, button5, button6, button7;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        
        getMenuInflater().inflate(R.menu.main, menu);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        button5 = (Button)findViewById(R.id.button5);
        button6 = (Button)findViewById(R.id.button6);
        button7 = (Button)findViewById(R.id.button7);
        
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        
        return true;
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button1:
                testAddArticle();
                Toast.makeText(this, testAddArticle(), 0).show();
                break;
            
            case R.id.button2:
                testGetArticleById();
                
                Toast.makeText(this, testGetArticleById(), 0).show();
                break;
            case R.id.button3:
                
                testGetArticleWithUser();
                
                Toast.makeText(this, testGetArticleWithUser(), 0).show();
                
                break;
            case R.id.button4:
                testListArticlesByUserId();
                Toast.makeText(this, testListArticlesByUserId(), 0).show();
                
                break;
            case R.id.button5:
                testGetUserById();
                Toast.makeText(this, testGetUserById(), 0).show();
                
                break;
            case R.id.button6:
                try
                {
                    testAddStudent();
                    Toast.makeText(this, testAddStudent(), 0).show();
                    
                }
                catch (SQLException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case R.id.button7:
                
                break;
            
            default:
                break;
        }
        
    }
    
    public String testAddArticle()
    {
        User u = new User();
        u.setName("张鸿洋");
        new UserDao(this).add(u);
        Article article = new Article();
        article.setTitle("ORMLite的使用");
        article.setUser(u);
        new ArticleDao(this).add(article);
        
        return article.getTitle() + article.getUser().getName();
        
    }
    
    public String testGetArticleById()
    {
        Article article = new ArticleDao(this).get(1);
        L.e(article.getUser() + " , " + article.getTitle());
        return article.getUser() + article.getTitle();
        
    }
    
    public String testGetArticleWithUser()
    {
        
        Article article = new ArticleDao(this).getArticleWithUser(1);
        L.e(article.getUser() + " , " + article.getTitle());
        return article.getUser() + " , " + article.getTitle();
        
    }
    
    public String testListArticlesByUserId()
    {
        
        List<Article> articles = new ArticleDao(this).listByUserId(1);
        L.e(articles.toString());
        
        return articles.toString();
        
    }
    
    public String testGetUserById()
    {
        User user = new UserDao(this).get(1);
        L.e(user.getName());
        if (user.getArticles() != null)
            for (Article article : user.getArticles())
            {
                L.e(article.toString());
            }
        
        return user.getName();
    }
    
    public String testAddStudent()
        throws SQLException
    {
        Dao dao = DatabaseHelper.getHelper(this).getDao(Student.class);
        Student student = new Student();
        student.setDao(dao);
        student.setName("张鸿洋");
        student.create();
        return student.getName() + student.getId();
    }
    
}
