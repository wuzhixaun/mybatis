package com.wuzx.construct;

public class Test {


    public static void main(String[] args) {
        ComputerBuilder computerBuilder = new ComputerBuilder();
        computerBuilder.installDisplayer("显万器");
        computerBuilder.installMainUnit("主机");
        computerBuilder.installKeyboard("键盘");
        computerBuilder.installMouse("鼠标");
        Computer computer = computerBuilder.Builder();
        System.out.println(computer);
    }
}
