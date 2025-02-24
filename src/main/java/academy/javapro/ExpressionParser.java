package academy.javapro;

class ExpressionParser {
    private final String input;
    private int position;

    public ExpressionParser(String input) {
        this.input = input;
        this.position = 0;
    }

    // expr → expr + term
    public double parseExpression() {

        double leftmostTerm = parseTerm();
        double total = leftmostTerm;
        
       while(position < input.length() && input.charAt(position) == '+')
       {
            position++;
            double rightTerm = parseTerm();
            total += rightTerm;
       }
       
        return total;
       
    }

    // term → term * factor
    private double parseTerm() {
       
        double leftmostTerm = parseFactor();
        double total = leftmostTerm;

        while(position < input.length() && input.charAt(position) == '*')
        {
            position++;
            double rightTerm = parseFactor();
            total *= rightTerm;
        }

        return total;
    }

    // factor → ( expr )
    private double parseFactor() {
        
        if(position < input.length() && input.charAt(position) == '(')
        {
            position++;
            double expression = parseExpression();
            
            if(position < input.length() && input.charAt(position) == ')')
            {
                position++;
                return expression;
            }
            else
                throw new IllegalArgumentException("Uncompleted set of parentheses");
        }

        else
             return parseNumber();

    }

    // Parse a numeric value
    private double parseNumber() {
        
        StringBuilder digits = new StringBuilder();

        while(position < input.length() && (input.charAt(position) == '.' || Character.isDigit(input.charAt(position))))
        {
            digits.append(input.charAt(position));
            position++;
        }

        if(digits.length() == 0)
        {
            throw new IllegalArgumentException("Invalid number at position " + position);
        }

        return Double.parseDouble(digits.toString());
        
    }

    public static void main(String[] args) {
        // Test cases
        String[] testCases = {
                "2 + 3 * (4 + 5)",    // Complex expression with parentheses
                "2 + 3 * 4",          // Basic arithmetic with precedence
                "(2 + 3) * 4",        // Parentheses changing precedence
                "2 * (3 + 4) * (5 + 6)", // Multiple parentheses
                "1.5 + 2.5 * 3"       // Decimal numbers
        };

        // Process each test case
        for (String expression : testCases) {
            System.out.println("\nTest Case: " + expression);
            try {
                ExpressionParser parser = new ExpressionParser(expression.replaceAll("\\s+", "")); // Remove spaces
                double result = parser.parseExpression();
                System.out.println("Result: " + result);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}

