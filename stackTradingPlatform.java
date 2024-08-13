import java.sql.DriverManager;
import java.sql.Statement;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;
public class stackTradingPlatform{
    private static String url="jdbc:mysql://127.0.0.1:3306/STACK_TRADING_db";
    private static String user="root";
    private static String pass="Pakistan 123";
    static Scanner input=new Scanner(System.in);
    static String username;
    static String password;
    static Connection connection;
    static Statement statement;
    static ResultSet resultSet;
    static String sql;
    static{
        try{
            connection=DriverManager.getConnection(url,user,pass);
            statement=connection.createStatement();
        }
        catch(Exception e){
        }
    }
    public static void main(String []Args){
        System.out.println("\t\t\t\t\t\t\t__________________________________");
        System.out.println("\t\t\t\t\t\t\tWelcome to Stack Trading Platform");
        System.out.println("\t\t\t\t\t\t\t__________________________________");
        while(true){
            System.out.println("\n--------------------------");
            System.out.println("1.sign up.\n2.loggin.");
            System.out.println("--------------------------");
            System.out.print("Enter your choice here: ");
            String choice=input.nextLine();
            if(choice.equals("1")){
                toSignup();
                while(true){
                    try{
                        System.out.print("\nEnter your initial balance USD:");
                        Double initialBalance=input.nextDouble();
                        input.nextLine();
                        sql="INSERT INTO USERS_DATA "+
                            "(name,password,balance) "+
                            "VALUES "+
                            "('"+username+"','"+password+"',"+initialBalance+") ";
                        statement.executeUpdate(sql);    
                        break;
                    }
                    catch(Exception e){
                        System.out.println("Enter integer or float value in balance:");
                    }
                }
                break;
            }
            else if(choice.equals("2")){
                if(toLoggin())
                    break;   
            }
            else
                System.out.println("Invalid choice plz select again");
        }
        while(true){
            System.out.println("_________________");
            System.out.println("1)View Market.\n2)Buy Stock.\n3)Sell Stock.\n4)View Portfolio.\n5)Add USD\n6)Exit");
            System.out.print("Enter your choice here:");
            String choice=input.nextLine();
            if(choice.equals("1"))
                viewMarket();
            else if(choice.equals("2"))
                buyStock();
            else if(choice.equals("3"))
                sellStock();
            else if(choice.equals("4"))
                viewPortfolio();
            else if(choice.equals("5")){
                addUSD();
            }
            else if(choice.equals("6")){
                exit();
                break;
            }
            else
                System.out.println("You entered a invalid choice plz enter again:");
        }
    }

    public static void toSignup(){
        System.out.print("Enter username:");
        username=input.nextLine();
        // password="";
        String choice;
        while(true){
            System.out.print("1.Produce autopassword\n2.Make password\nEnter your choice here:");
            choice=input.nextLine();
            if(choice.equals("2")){
                while(true){
                    System.out.println("The minimum length of password should be 8 and it must contain a lowercase Character,An upercase character,a special character and a Number.");
                    System.out.print("Enter password here:");
                    password=input.nextLine();
                    if(passwordStrength(password)){
                        System.out.printf("Your account is generated.\nYour username is %s and password is %s",username,password);
                        break;
                    }
                    else
                        System.out.print("You enter a very weak password.Plz Enter Again.");
                }
                break;
            }
            else if(choice.equals("1")){
                password=generateAutoPassword();
                System.out.printf("Your account is generated.\nYour username is %s and password is %s\n",username,password);
                break;
            }
            else{
                System.out.print("Your choice is invalid plz select again");
            }
        }
        sql="INSERT INTO USERS_LOGGINS "+
            "VALUES "+
            "('"+username+"','"+password+"')";
        try{
            statement.executeUpdate(sql);
        }
        catch(Exception e ){
            System.out.println("User name or password already taken.Plz try again.");
        };
    }

    public static boolean passwordStrength(String password) {
        int passLength = password.length();
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialCharacter = false;
        for (int i = 0; i < passLength; i++) {
            char ch = password.charAt(i);
            if (Character.isUpperCase(ch))
                hasUpperCase = true;
            else if (Character.isLowerCase(ch))
                hasLowerCase = true;
            else if (Character.isDigit(ch))
                hasDigit = true;
            else
                hasSpecialCharacter = true;
        }
        return passLength >= 8 && hasUpperCase && hasLowerCase && hasDigit && hasSpecialCharacter;
    }

    public static String generateAutoPassword() {
        SecureRandom random = new SecureRandom();
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String upperCase = lowerCase.toUpperCase();
        String digit = "123456789";
        String specialCharacter = "!@#$%^&*()-_=+[]{}|;:,.<>?";
        String allCharacters = lowerCase + upperCase + digit + specialCharacter;
        int length = random.nextInt(5) + 8;
        StringBuilder password = new StringBuilder(length);
        password.append(lowerCase.charAt(random.nextInt(lowerCase.length())));
        password.append(upperCase.charAt(random.nextInt(upperCase.length())));
        password.append(digit.charAt(random.nextInt(digit.length())));
        password.append(specialCharacter.charAt(random.nextInt(specialCharacter.length())));
        for (int i = 0; i < length - 4; i++) {
            password.append(allCharacters.charAt(random.nextInt(allCharacters.length())));
        }
        for (int i = 0; i < length; i++) {
            char ch = password.charAt(i);
            int rand = random.nextInt(length);
            password.setCharAt(i, password.charAt(rand));
            password.setCharAt(rand, ch);
        }
        return password.toString();
    }

    public static Boolean toLoggin(){
        while(true){
            System.out.print("Enter your username:");
            username=input.nextLine();
            System.out.print("Enter your password:");
            password=input.nextLine();
            sql="SELECT Count(username) FROM USERS_LOGGINS "+
                "WHERE username='"+username+"' AND password='"+password+"'";
            try{    
                resultSet=statement.executeQuery(sql);
                resultSet.next(); 
                if(resultSet.getInt("Count(username)")==0){
                    System.out.println("Username or password incorrect enter your choice again or go back:");
                    System.out.println("Do you want to go back:\n1)Yes.\n2)No");
                    System.out.print("Enter your choice(1 or 2) here:");
                    String choice=input.nextLine();
                    if(choice.equals("1")){
                        return false;
                    }  
                }
                else{
                    System.out.println("Loggin succesfull");
                    return true;
                }
            }   
            catch(Exception e ){
                System.out.println(e);
            };
        }
    }

    public static void viewMarket(){
        sql="SELECT * FROM MARKET_DATA";
        try {
            resultSet=statement.executeQuery(sql);
            System.out.println("_________________________________________");
            System.out.println("Stocks_name      Price      Inc_Dec_By");
            System.out.println("_________________________________________");
            while(resultSet.next()){
                String stocks_name=resultSet.getString("stocks_name");
                Double price=resultSet.getDouble("price");
                Double inc_dec_by=resultSet.getDouble("inc_dec_rate");
                System.out.printf("%-17s%-11.2f%.2f\n",stocks_name,price,inc_dec_by);
            }
        }
        catch(Exception e){};
    }

    public static void buyStock(){
        while(true){
            try{
                Double price;
                System.out.println("_______________________________________");
                System.out.print("Enter the name of stock u want to buy:");
                String stockNameInput=input.nextLine();
                stockNameInput=stockNameInput.toLowerCase();
                sql="select price from MARKET_DATA "+
                    "WHERE stocks_name='"+stockNameInput+"'";
                resultSet=statement.executeQuery(sql);
                if(resultSet.isBeforeFirst()){
                    resultSet.next();
                    price=resultSet.getDouble("price");
                }
                else{
                    System.out.print("This stock is not available please enter input again :");   
                    continue;
                }
                System.out.print("Enter Quantity:");
                int quantity=input.nextInt();
                input.nextLine();
                sql="SELECT balance FROM USERS_DATA "+
                    "WHERE name='"+username+"' AND password='"+password+"'";
                resultSet=statement.executeQuery(sql);
                resultSet.next();
                double balance=resultSet.getDouble("balance");
                double bill=quantity*price;
                if(bill>balance){
                    System.out.printf("You have not enough money to buy this stock,Your current balance is %.3f and the totsl bill is %.3f",balance,bill);
                    continue;
                }
                else{
                    balance=balance-bill;
                    sql="UPDATE USERS_DATA "+
                        "SET balance="+balance+
                        " WHERE name='"+username+"' AND password='"+password+"'";
                    statement.executeUpdate(sql);
                    sql = "UPDATE USERS_DATA " +
                        "SET " + stockNameInput + " = " + stockNameInput + " + " + quantity +
                        " WHERE name='" + username + "' AND password='" + password + "'";
                    statement.executeUpdate(sql);
                    sql="UPDATE MARKET_DATA " +
                        "SET inc_dec_rate = inc_dec_rate + (5 * " + quantity + ") " +
                        "WHERE stocks_name = '" + stockNameInput + "'";
                    statement.executeUpdate(sql);
                    sql="UPDATE MARKET_DATA " +
                        "SET price=price + (5 * " + quantity + ") " +
                        "WHERE stocks_name = '" + stockNameInput + "'";
                    statement.executeUpdate(sql);       
                }
                System.out.println("Stock purchased succesfully.");
                break;
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static void sellStock(){
        while(true){
            try{
                Double price;
                System.out.println("_______________________________________");
                System.out.print("Enter the name of stock u want to sell:");
                String stockNameInput=input.nextLine();
                stockNameInput=stockNameInput.toLowerCase();
                sql="select price from MARKET_DATA "+
                    "WHERE stocks_name='"+stockNameInput+"'";
                resultSet=statement.executeQuery(sql);
                if(resultSet.isBeforeFirst()){
                    resultSet.next();
                    price=resultSet.getDouble("price");
                }
                else{
                    System.out.println("This stock is not available please enter input again :");   
                    continue;
                }
                System.out.print("Enter Quantity:");
                int quantity=input.nextInt();
                input.nextLine();
                sql = "SELECT " + stockNameInput + " FROM USERS_DATA " +
                      "WHERE name='" + username + "' AND password='" + password + "'";
                resultSet=statement.executeQuery(sql);
                resultSet.next();
                int AvailableQuantity=resultSet.getInt(stockNameInput);
                if(AvailableQuantity>=quantity){
                    sql="UPDATE USERS_DATA "+
                        "SET "+stockNameInput+"="+(AvailableQuantity-quantity);
                    statement.executeUpdate(sql);    
                }    
                else{ 
                    System.out.println("The "+stockNameInput+" availble in your account are "+AvailableQuantity+" so enter input again:");
                    continue;
                }
                sql="SELECT balance FROM USERS_DATA "+
                    "WHERE name='"+username+"' AND password='"+password+"'";
                resultSet=statement.executeQuery(sql);
                resultSet.next();
                double balance=resultSet.getDouble("balance");
                double bill=quantity*price;
                balance=balance+bill;
                sql="UPDATE USERS_DATA "+
                    "SET balance="+balance+
                    " WHERE name='"+username+"' AND password='"+password+"'";
                statement.executeUpdate(sql);
                sql="UPDATE MARKET_DATA " +
                    "SET inc_dec_rate = inc_dec_rate - (5 * " + quantity + ") " +
                    "WHERE stocks_name = '" + stockNameInput + "'";
                statement.executeUpdate(sql); 
                sql="UPDATE MARKET_DATA " +
                    "SET price=price - (5 * " + quantity + ") " +
                     "WHERE stocks_name = '" + stockNameInput + "'";
                statement.executeUpdate(sql);    
                System.out.println("Stock sell succesfully.");       
                break;
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
    }

    public static void viewPortfolio(){
        System.out.println("___________");
        System.out.println("Portfolio");
        sql = "SELECT * FROM USERS_DATA WHERE name='" + username + "' AND password='" + password + "'";
        try {
            System.out.println("___________________________________________________________________________________");
            System.out.println("Name            Balance   microsoft   apple   cocacola   samsung   gold   amazon");
            System.out.println("___________________________________________________________________________________");
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int balance = resultSet.getInt("balance");
                int microsoft = resultSet.getInt("microsoft");
                int apple = resultSet.getInt("apple");
                int cocacola = resultSet.getInt("cocacola");
                int samsung = resultSet.getInt("samsung");
                int gold = resultSet.getInt("gold");
                int amazon = resultSet.getInt("amazon");
                System.out.printf("%-16s%-10d%-12d%-8d%-11d%-10d%-7d%-6d\n", name, balance, microsoft, apple, cocacola, samsung, gold, amazon);
                System.out.println("___________________________________________________________________________________");
            }
        } catch (Exception e) {}
    }
    
    public static void addUSD(){
        System.out.println("_________________________________________");
        System.out.print("Enter the amount of USD you want to add:");
        Double USD=input.nextDouble();
        input.nextLine();
        sql="UPDATE USERS_DATA "+
            "SET balance=balance + "+USD+
            " WHERE name='"+username+"' AND password='"+password+"'";
        try {
            statement.executeUpdate(sql);
        } catch (Exception e) {} 
    }

    public static void exit(){
        System.out.print("Exiting");
        for(int i=0;i<=4;i++){
            try {
                Thread.sleep(1000);
                System.out.print(" .");
            }catch(Exception e){}  
        }
    }

}