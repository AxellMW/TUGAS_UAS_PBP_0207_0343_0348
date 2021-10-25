package com.example.ugdpbp.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.ugdpbp.Dao.OrderDao;
import com.example.ugdpbp.Dao.UserDao;
import com.example.ugdpbp.model.Order;
import com.example.ugdpbp.model.User;

@Database(entities = {Order.class, User.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{
    public abstract OrderDao orderDao();
    public abstract UserDao userDao();
}
