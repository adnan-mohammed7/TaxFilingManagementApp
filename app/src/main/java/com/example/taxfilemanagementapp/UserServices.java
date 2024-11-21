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

    public void insertAllCustomers(Customer[] customers, OperationCallback callback){
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
                //
            }
        }).start();
    }

    public void getCustomerById(int id ,Consumer<Customer> onResult){
        new Thread(()->{
            try{
                Customer result = userDao.getCustomerById(id);
                if(onResult != null){
                    onResult.accept(result);
                }
            }catch(Exception e){
                //
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
                //
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

    public void insertAdmin(Admin admin, OperationCallback callback){
        new Thread(()->{
            try{
                adminDao.insert(admin);
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

    public void getAllAdmins(Consumer<List<Admin>> onResult){
        new Thread(()->{
            try{
                List<Admin> result = adminDao.getAllAdmins();
                if(onResult != null){
                    onResult.accept(result);
                }
            }catch(Exception e){
                //
            }
        }).start();
    }

    public void getAdminById(int id, Consumer<Admin> onResult){
        new Thread(()->{
            try{
                Admin result = adminDao.getAdminById(id);
                if(onResult != null){
                    onResult.accept(result);
                }
            }catch(Exception e){
                //
            }
        }).start();
    }

    public void getAdminByUsername(String name, Consumer<Admin> onResult){
        new Thread(()->{
            try{
                Admin result = adminDao.getAdminByUsername(name);
                if(onResult != null){
                    onResult.accept(result);
                }
            }catch(Exception e){
                //
            }
        }).start();
    }

    public void updateAdmin(Admin admin, OperationCallback callback){
        new Thread(()->{
            try{
                adminDao.update(admin);
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

    public void deleteAdmin(Admin admin, OperationCallback callback){
        new Thread(()->{
            try{
                adminDao.delete(admin);
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
