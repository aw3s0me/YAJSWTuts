package de.example;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by alex on 04.03.17.
 */
public class Main {
    int secondsPassed = 0;
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            // Your code
            secondsPassed++;
            System.out.println("Seconds passed: " + secondsPassed);
        }
    };

    public void start() {
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    public static void main(String[] args) {
        System.out.println("Start running timer: ");
        Main main = new Main();
        main.start();
    }
}
