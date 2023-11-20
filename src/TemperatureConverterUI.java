import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TemperatureConverterUI extends JFrame {
    private JTextField temperatureField;
    private JComboBox<ConversionType> conversionType;
    private JButton convertButton;
    private JLabel resultLabel;

    public TemperatureConverterUI() {
        super("Temperature Converter");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        JLabel temperatureLabel = new JLabel("Temperature:");
        resultLabel = new JLabel("Result:");
        resultLabel.setForeground(Color.BLUE);

        temperatureField = new JTextField(10);
        conversionType = new JComboBox<>(ConversionType.values());
        convertButton = new JButton("Convert");

        // Set layout manager (FlowLayout in this case)
        setLayout(new java.awt.FlowLayout());

        // Add components to the JFrame
        add(temperatureLabel);
        add(temperatureField);
        add(conversionType);
        add(convertButton);
        add(resultLabel);

        // Add action listener to the convertButton
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertTemperature();
            }
        });

        // Add animation to the resultLabel
        Timer timer = new Timer(200, new ActionListener() {
            private boolean colorSwitch = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (colorSwitch) {
                    resultLabel.setForeground(Color.BLUE);
                } else {
                    resultLabel.setForeground(Color.RED);
                }
                colorSwitch = !colorSwitch;
            }
        });
        timer.setRepeats(true);
        timer.start();
    }

    private void convertTemperature() {
        try {
            double temperature = Double.parseDouble(temperatureField.getText());
            ConversionType selectedConversion = (ConversionType) conversionType.getSelectedItem();
            double result;

            switch (selectedConversion) {
                case FAHRENHEIT_TO_CELSIUS:
                    result = fahrenheitToCelsius(temperature);
                    break;
                case CELSIUS_TO_FAHRENHEIT:
                    result = celsiusToFahrenheit(temperature);
                    break;
                default:
                    result = 0.0; // Handle unexpected conversion type
            }

            resultLabel.setText("Result: " + temperature + " " +
                    selectedConversion.getSourceUnit() + " is equal to " + result + " " +
                    selectedConversion.getTargetUnit());
            resultLabel.setForeground(Color.GREEN); // Change color for successful conversion
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid input. Please enter a valid number.");
            resultLabel.setForeground(Color.RED); // Change color for error
        }
    }

    private double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    private double celsiusToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TemperatureConverterUI().setVisible(true));
    }

    // Enum for Conversion Types
    private enum ConversionType {
        FAHRENHEIT_TO_CELSIUS("Fahrenheit", "Celsius"),
        CELSIUS_TO_FAHRENHEIT("Celsius", "Fahrenheit");

        private final String sourceUnit;
        private final String targetUnit;

        ConversionType(String sourceUnit, String targetUnit) {
            this.sourceUnit = sourceUnit;
            this.targetUnit = targetUnit;
        }

        public String getSourceUnit() {
            return sourceUnit;
        }

        public String getTargetUnit() {
            return targetUnit;
        }

        @Override
        public String toString() {
            return sourceUnit + " to " + targetUnit;
        }
    }
}
