package com.wuzx.construct;

public class ComputerBuilder {

    Computer computer = new Computer();


    public void installDisplayer(String displayer) {
        computer.setDisplayer(displayer);
    }


    public void installMainUnit(String mainUnit) {
        computer.setMainUnit(mainUnit);
    }

    public void installMouse(String mouse) {
        computer.setMouse(mouse);
    }

    public void installKeyboard(String keyboard) {
        computer.setKeyboard(keyboard);
    }


    public Computer Builder() {
        return computer;
    }
}
