package com.main;

import com.main.data.model.Person;
import com.main.data.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.swing.*;
import java.awt.*;

public class Main extends JPanel {

    public Main() {


    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("HTMLFrame");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new Main(), BorderLayout.CENTER);
        frame.setSize(300, 75);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
