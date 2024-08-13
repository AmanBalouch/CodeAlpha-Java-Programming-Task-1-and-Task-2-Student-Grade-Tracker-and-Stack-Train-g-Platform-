import java.util.*;
class StudentsGradeTracker{
    static Scanner input=new Scanner(System.in);
    static String topperName,lowestGradeStudentName;
    public static void main(String []Args){
        try{
            System.out.println("\t\t\t\t\t\t\t\t________________________");
            System.out.println("\t\t\t\t\t\t\t\tWelcome to Grade Tracker");
            System.out.println("\t\t\t\t\t\t\t\t________________________\n\n");
            while(true){
                System.out.print("1)You wnat to enter only grades without the staudent name.\n2)You want to enter Student name along with his grades.\nEnter your choice here:");
                String choice =input.nextLine();
                if(!choice.equals("1") && !choice.equals("2")){
                    System.out.println("Your choice is not valid please Enter choice again.");
                    continue;
                }
                System.out.print("Enter the number of students:");
                int numberOfStudents=input.nextInt();
                input.nextLine();
                double []gradesArray=new double[numberOfStudents];
                if(choice.equals("1")){
                    inputOfGrades(gradesArray);
                    System.out.println("The average of grades is "+gradeAverage(gradesArray));
                    System.out.println("The hishest grade is "+highestGrade(gradesArray));
                    System.out.println("The lowest grade is "+lowestGrade(gradesArray));
                    break;
                }
                else {
                    String []studentsArray=new String[numberOfStudents];
                    inputOfGradesWithStudentsName(gradesArray,studentsArray);
                    double highestGrade=highestGrade(gradesArray);
                    double lowestGrade=lowestGrade(gradesArray);
                    System.out.println("The average of grades is "+gradeAverage(gradesArray));
                    System.out.println("The hishest grade is "+highestGrade+" by ("+topperNames(highestGrade,gradesArray,studentsArray)+")");
                    System.out.println("The lowest grade is "+lowestGrade+" by ("+lowestGradeStudentNames(lowestGrade,gradesArray,studentsArray)+")");
                    break;
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public static void inputOfGrades(double []gradesArray){
        System.out.println("Enter grades of students below:");
        for(int i=0;i<gradesArray.length;i++){
            System.out.print("\nStudent "+(i+1)+" :");
            gradesArray[i]=input.nextDouble();
            input.nextLine();
        }
    }
    public static void inputOfGradesWithStudentsName(double[]gradesArray,String []studentsArray){
        System.out.println("Enter names and grades ofstudents carefully:");
        for(int i=0;i<gradesArray.length;i++){
            System.out.print("\nName of student "+(i+1)+" :");
            studentsArray[i]=input.nextLine();
            System.out.print("Grades of Student "+(i+1)+" :");
            gradesArray[i]=input.nextDouble();
            input.nextLine();
        }
    }
    public static double gradeAverage(double []gradesArray){
        double average;
        double sum=0.0;
        for(int i=0;i<gradesArray.length;i++){
            sum+=gradesArray[i];
        }
        average=sum/gradesArray.length;
        return average;
    }
    public static double highestGrade(double []gradesArray){
        double highestGrade=0;
        for(int i=0;i<gradesArray.length;i++){
            if(gradesArray[i]>highestGrade)
                highestGrade=gradesArray[i];
        }
        return highestGrade;
    }
    public static double lowestGrade(double []gradesArray){
        double lowestGrade=gradesArray[0];
        for(int i=0;i<gradesArray.length;i++){
            if(gradesArray[i]<lowestGrade)
                lowestGrade=gradesArray[i];
        }
        return lowestGrade;
    }
    public static String topperNames(double highestGrade,double []gradesArray,String []studentsArray){
        String toppersName="";
        for(int i=0;i<gradesArray.length;i++){
            if(gradesArray[i]==highestGrade){
                toppersName+=(studentsArray[i]+",");
            }
        }
        return toppersName;
    }
    public static String lowestGradeStudentNames(double lowestGrade,double []gradesArray,String []studentsArray){
        String lowestGradeStudentsName="";
        for(int i=0;i<gradesArray.length;i++){
            if(gradesArray[i]==lowestGrade){
                lowestGradeStudentsName+=(studentsArray[i]+",");
            }
        }
        return lowestGradeStudentsName;
    }
}