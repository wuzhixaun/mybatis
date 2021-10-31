package com.wuzx.simplefactory;

public class ComputerFactory {


    public static Computer createComPuter(String type) {
        Computer mComputer=null;
        switch (type) {
            case "lenovo":
                mComputer=new LenovoComputer();
                break;
            case "hp":
                mComputer=new HPComputer();
                break;
        }
        return mComputer;
    }
}
