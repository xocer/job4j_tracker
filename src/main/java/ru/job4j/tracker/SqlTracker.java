//package ru.job4j.tracker;
//
//import java.io.InputStream;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;
//
//public class SqlTracker implements Store {
//    private Connection cn;
//
//    public SqlTracker(Connection cn) {
//        this.cn = cn;
//    }
//
//    public void init() {
//        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("app.properties.properties")) {
//            Properties config = new Properties();
//            config.load(in);
//            Class.forName(config.getProperty("driver-class-name"));
//            cn = DriverManager.getConnection(
//                    config.getProperty("url"),
//                    config.getProperty("username"),
//                    config.getProperty("password")
//            );
//        } catch (Exception e) {
//            throw new IllegalStateException(e);
//        }
//    }
//
//    @Override
//    public void close() throws Exception {
//        if (cn != null) {
//            cn.close();
//        }
//    }
//
//    @Override
//    public Item add(Item item) {
//        try (PreparedStatement preparedStatement = cn.prepareStatement("insert into items (id, name) values (?, ?)")) {
//            preparedStatement.setInt(1, item.getId());
//            preparedStatement.setString(2, item.getName());
//            preparedStatement.execute();
//        } catch (Exception e) {
//            System.out.println("Ошибка в методе добавления");
//        }
//        return item;
//    }
//
//    @Override
//    public boolean replace(String id, Item item) {
//        boolean result = false;
//        try (PreparedStatement preparedStatement = cn.prepareStatement("update items set id = ?, name = ? where id = ?")) {
//            preparedStatement.setInt(1, item.getId());
//            preparedStatement.setString(2, item.getName());
//            preparedStatement.setInt(3, Integer.parseInt(id));
//            result = preparedStatement.executeUpdate() > 0;
//        } catch (Exception e) {
//            System.out.println("Ошибка в методе обновления");
//        }
//        return result;
//    }
//
//    @Override
//    public boolean delete(String id) {
//        boolean result = false;
//        try (PreparedStatement preparedStatement = cn.prepareStatement("delete from items where id = ?")) {
//            preparedStatement.setInt(1, Integer.parseInt(id));
//            result = preparedStatement.executeUpdate() > 0;
//        } catch (Exception e) {
//            System.out.println("Ошибка в методе удаления");
//        }
//        return result;
//    }
//
//    @Override
//    public List<Item> findAll() {
//        List<Item> list = new ArrayList<>();
//        try (PreparedStatement preparedStatement = cn.prepareStatement("select * from items")) {
//            try (ResultSet resultSet = preparedStatement.executeQuery()){
//                while (resultSet.next()) {
//                    list.add(new Item(
//                            resultSet.getInt("id"),
//                            resultSet.getString("name")));
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("Ошибка в методе findAll");
//        }
//        return list;
//    }
//
//    @Override
//    public List<Item> findByName(String key) {
//        List<Item> list = new ArrayList<>();
//        try (PreparedStatement preparedStatement = cn.prepareStatement("select * from items where name = ?")) {
//            preparedStatement.setString(1, key);
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                while (resultSet.next()) {
//                    list.add(new Item(
//                            resultSet.getInt("id"),
//                            resultSet.getString("name")
//                    ));
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("Ошибка в методе поиска по имени");
//        }
//        return list;
//    }
//
//    @Override
//    public Item findById(String id) {
//        Item item = null;
//        try (PreparedStatement preparedStatement = cn.prepareStatement("select * from items where name = ?")) {
//            preparedStatement.setInt(1, Integer.parseInt(id));
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                item = new Item(
//                        resultSet.getInt("id"),
//                        resultSet.getString("name")
//                );
//            }
//        } catch (Exception e) {
//            System.out.println("Ошибка в методе поиска по id");
//        }
//        return item;
//    }
//
////    public static void main(String[] args) {
////        Input validate = new ValidateInput(
////                new ConsoleInput()
////        );
////        try (Store tracker = new SqlTracker()) {
////            tracker.init();
////            UserAction[] actions = {
////                    new CreateAction()
////            };
////            new StartUI().init(validate, tracker, actions);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
//}