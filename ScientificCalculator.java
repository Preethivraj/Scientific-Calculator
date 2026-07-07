package com.preethivraj.scientificcalculator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ScientificCalculator extends Application {

    private final TextField display = new TextField();

    @Override
    public void start(Stage stage) {

        display.setEditable(false);
        display.setFont(Font.font("Arial", 24));
        display.setPrefHeight(60);
        display.setAlignment(Pos.CENTER_RIGHT);

        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(8);
        grid.setPadding(new Insets(15));

        String[][] buttons = {
                {"sin", "cos", "tan", "√"},
                {"log", "ln", "x²", "π"},
                {"7", "8", "9", "/"},
                {"4", "5", "6", "*"},
                {"1", "2", "3", "-"},
                {"0", ".", "=", "+"},
                {"C", "←", "(", ")"}
        };

        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {

                String text = buttons[row][col];

                Button button = new Button(text);
                button.setPrefSize(90, 60);
                button.setFont(Font.font(18));

                button.setStyle("""
                        -fx-background-color: #2D2D2D;
                        -fx-text-fill: white;
                        -fx-background-radius: 10;
                        """);

                button.setOnAction(e -> handleButton(text));

                grid.add(button, col, row);
            }
        }

        BorderPane root = new BorderPane();
        root.setTop(display);
        root.setCenter(grid);

        root.setStyle("-fx-background-color: #1E1E1E;");
        BorderPane.setMargin(display, new Insets(15));

        Scene scene = new Scene(root, 420, 580);

        stage.setTitle("Scientific Calculator");
        stage.setScene(scene);
        stage.show();
    }

    private void handleButton(String value) {

        switch (value) {

            case "C":
                display.clear();
                break;

            case "←":
                if (!display.getText().isEmpty()) {
                    display.setText(
                            display.getText()
                                    .substring(0,
                                            display.getText().length() - 1));
                }
                break;

            case "π":
                display.appendText(String.valueOf(Math.PI));
                break;

            case "√":
                try {
                    double num = Double.parseDouble(display.getText());
                    display.setText(String.valueOf(Math.sqrt(num)));
                } catch (Exception ex) {
                    display.setText("Error");
                }
                break;

            case "x²":
                try {
                    double num = Double.parseDouble(display.getText());
                    display.setText(String.valueOf(num * num));
                } catch (Exception ex) {
                    display.setText("Error");
                }
                break;

            case "sin":
                calculateTrig("sin");
                break;

            case "cos":
                calculateTrig("cos");
                break;

            case "tan":
                calculateTrig("tan");
                break;

            case "log":
                try {
                    double num = Double.parseDouble(display.getText());
                    display.setText(String.valueOf(Math.log10(num)));
                } catch (Exception ex) {
                    display.setText("Error");
                }
                break;

            case "ln":
                try {
                    double num = Double.parseDouble(display.getText());
                    display.setText(String.valueOf(Math.log(num)));
                } catch (Exception ex) {
                    display.setText("Error");
                }
                break;

            case "=":
                evaluateExpression();
                break;

            default:
                display.appendText(value);
        }
    }

    private void calculateTrig(String function) {
        try {

            double num =
                    Double.parseDouble(display.getText());

            double radians = Math.toRadians(num);

            switch (function) {
                case "sin":
                    display.setText(
                            String.valueOf(Math.sin(radians)));
                    break;

                case "cos":
                    display.setText(
                            String.valueOf(Math.cos(radians)));
                    break;

                case "tan":
                    display.setText(
                            String.valueOf(Math.tan(radians)));
                    break;
            }

        } catch (Exception e) {
            display.setText("Error");
        }
    }

    private void evaluateExpression() {

        try {

            String expression = display.getText();

            expression = expression.replace("*", " * ");
            expression = expression.replace("/", " / ");
            expression = expression.replace("+", " + ");
            expression = expression.replace("-", " - ");

            String[] tokens = expression.trim().split("\\s+");

            double result = Double.parseDouble(tokens[0]);

            for (int i = 1; i < tokens.length; i += 2) {

                String operator = tokens[i];
                double next =
                        Double.parseDouble(tokens[i + 1]);

                switch (operator) {
                    case "+":
                        result += next;
                        break;
                    case "-":
                        result -= next;
                        break;
                    case "*":
                        result *= next;
                        break;
                    case "/":
                        result /= next;
                        break;
                }
            }

            display.setText(String.valueOf(result));

        } catch (Exception e) {
            display.setText("Error");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}