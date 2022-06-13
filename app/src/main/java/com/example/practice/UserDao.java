package com.example.practice;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void registerUser(User user);

    @Query("SELECT * FROM users WHERE username=:username AND password=:password")
    User login(String username, String password);

    @Query("SELECT * FROM users WHERE id=:id LIMIT 1")
    User getUserId(long id);

    @Query("SELECT EXISTS (SELECT 1 FROM users WHERE username = :username)")
    int checkIfExists(String username);

    @Query("SELECT * FROM users")
    List<User> selectAll();

    @Update
    void updateUser(User user);

    @Insert
    void add(User user);

    @Delete
    void delete(User user);
}
