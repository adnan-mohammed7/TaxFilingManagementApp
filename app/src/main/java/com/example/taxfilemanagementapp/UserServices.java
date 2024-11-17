package com.example.taxfilemanagementapp;

import android.content.Context;

import androidx.room.Room;

import java.util.List;
import java.util.function.Consumer;

public class UserServices {
    private static UserServices instance;
    private final UserDao userDao;
    private final AdminDao adminDao;

    private UserServices(Context context){
        AppDatabase db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "taxdb").build();
        userDao = db.userDao();
        adminDao = db.adminDao();
    }

    public static synchronized UserServices getInstance(Context context){
        if(instance == null){
            instance = new UserServices(context);
        }
        return instance;
    }

    public interface OperationCallback{
        void onOperationCompleted();
        void onError(Exception e);
    }

    public void insertCustomer(Customer customer, OperationCallback callback){
        new Thread(()->{
            try{
                userDao.insert(customer);
                if(callback != null){
                    callback.onOperationCompleted();
                }
            }catch(Exception e){
                if(callback != null){
                    callback.onError(e);
                }
            }
        }).start();
    }

    public void inserAllCustomers(Customer[] customers, OperationCallback callback){
        new Thread(()->{
            try{
                userDao.insertAll(customers);
                if(callback != null){
                    callback.onOperationCompleted();
                }
            }catch(Exception e){
                if(callback != null){
                    callback.onError(e);
                }
            }
        }).start();
    }

    public void getAllCustomers(Consumer<List<Customer>> onResult){
        new Thread(()->{
            try{
                List<Customer> list = userDao.getAllCustomers();
                if(onResult != null){
                    onResult.accept(list);
                }
            }catch(Exception e){

            }
        }).start();
    }

    public void getCustomerById(int id ,Consumer<Customer> onResult, OperationCallback callback){
        new Thread(()->{
            try{
                Customer result = userDao.getCustomerById(id);
                if(onResult != null){
                    onResult.accept(result);
                }
            }catch(Exception e){
                if(callback != null){
                    callback.onError(e);
                }
            }
        }).start();
    }

    public void getCustomerByUsername(String username ,Consumer<Customer> onResult){
        new Thread(()->{
            try{
                Customer result = userDao.getCustomerByUsername(username);
                if(onResult != null){
                    onResult.accept(result);
                }
            }catch(Exception e){

            }
        }).start();
    }

    public void updateCustomer(Customer customer, OperationCallback callback){
        new Thread(()->{
            try{
                userDao.update(customer);
                if(callback != null){
                    callback.onOperationCompleted();
                }
            }catch(Exception e){
                if(callback != null){
                    callback.onError(e);
                }
            }
        }).start();
    }

    public void deleteCustomer(Customer customer, OperationCallback callback){
        new Thread(()->{
            try{
                userDao.delete(customer);
                if(callback != null){
                    callback.onOperationCompleted();
                }
            }catch(Exception e){
                if(callback != null){
                    callback.onError(e);
                }
            }
        }).start();
    }
}
