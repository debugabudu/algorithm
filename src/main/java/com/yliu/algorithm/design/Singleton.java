package com.yliu.algorithm.design;

/**
 * 一个类只允许创建一个实例（数据在系统中只应该保存一份，或者解决资源访问冲突的问题）
 */
public class Singleton {
    //饿汉式，线程安全
    static class Singleton1{
        private static Singleton1 singleton1 = new Singleton1();
        private Singleton1(){};
        public static Singleton1 getSingleton1(){
            return singleton1;
        }
    }

    //懒汉式，线程不安全
    static class Singleton2{
        private static Singleton2 singleton2;
        private Singleton2(){};
        public static Singleton2 getSingleton2(){
            if (singleton2 == null){
                singleton2 = new Singleton2();
            }
            return singleton2;
        }
    }

    //懒汉式，加锁，线程安全
    static class Singleton3{
        private static Singleton3 singleton3;
        private Singleton3(){};
        public synchronized static Singleton3 getSingleton3(){
            if (singleton3 == null){
                singleton3 = new Singleton3();
            }
            return singleton3;
        }
    }

    //双重校验锁，线程安全
    static class Singleton4{
        private static volatile Singleton4 singleton4 = null;
        private Singleton4(){};
        public static Singleton4 getSingleton4(){
            if (singleton4 == null){
                synchronized (Singleton4.class){
                    singleton4 = new Singleton4();
                }
            }
            return singleton4;
        }
    }

    //静态内部类，线程安全
    static class Singleton5{
        private Singleton5(){};
        private static class SingletonHolder{
            private static final Singleton5 INSTANCE = new Singleton5();
        }
        public static Singleton5 getSingleton5(){
            return SingletonHolder.INSTANCE;
        }
    }

    //枚举，线程安全
    enum  Singleton6{
        INSTANCE;
        public Singleton6 getSingleton6(){
            return INSTANCE;
        }
    }
}
