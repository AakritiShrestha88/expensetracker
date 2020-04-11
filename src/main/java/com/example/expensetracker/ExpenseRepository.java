package com.example.expensetracker;

//import javassist.bytecode.stackmap.BasicBlock;
import com.example.expensetracker.Expense;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExpenseRepository {
    // JDBC driver name and database URL
    //SETP 1 STARTING DATABASES PROPETIES AND CREDENTIALS
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL="jdbc:mysql://localhost:3306/expense_tracker_db?serverTimezone=UTC";
    //
    //  Database credentials
    static final String USER = "Expense_App";
    static final String PASS = "Nepal@123";

    private Connection conn;
    private List<Expense> expenses;
    private int count = 0;



    public ExpenseRepository() {
        this.expenses = new ArrayList<>();
        //SETP2 LOADING DRIVER
        try {
            Class.forName(JDBC_DRIVER);
            //SETP 3 ESTABLISHING DATABASES CONNECTION
            conn= DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (ClassNotFoundException e){}
        catch(SQLException e){}
    }



    public List<Expense> findAll(){
        List<Expense>expenses=new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from expenses");
            while (rs.next()) {
                Expense expense=new Expense();
                expense.setId(rs.getInt("id"));
                expense.setName(rs.getString("name"));
                expense.setAmount(rs.getFloat("amount"));
                expense.setCategory(rs.getString("category"));
                expense.setDescription(rs.getString("description"));
                expenses.add(expense);
            }
            stmt.close();
            rs.close();
        }
        catch(SQLException e){}
        return expenses;
    }




    public Expense findById(int id){
        Expense expense = null;

//        for(int i = 0; i < expenses.size(); i++){
//            if(expenses.get(i).getId() == id){
//                expense = expenses.get(i);
//            }
//        }

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from expenses where id = "+id);
            while (rs.next()) {
               expense=new Expense();
                expense.setId(rs.getInt("id"));
                expense.setName(rs.getString("name"));
                expense.setAmount(rs.getFloat("amount"));
                expense.setCategory(rs.getString("category"));
                expense.setDescription(rs.getString("description"));
//                expenses.add(expense);
            }
            stmt.close();
            rs.close();
        }
        catch(SQLException e){}
        return expense;

    }

    public Expense save(Expense expense){

        expense.setId(count);
        count++;
        this.expenses.add(expense);

        return expense;

    }

    public void deleteById(int id){
        Expense expense = this.findById(id);
        this.expenses.remove(expense);
    }

    public void delete(Expense expense){
        this.deleteById(expense.getId());
    }

}
