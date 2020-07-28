import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MyString message = new MyString(scanner.nextLine());
        String instruction;
        do {
            instruction = scanner.nextLine().trim();
        } while (!runInstruction(instruction, message));
    }
    static boolean runInstruction(String instruction , MyString message){
        Matcher validInstruction = Instructions.valid.matcher(instruction);
        String invalidCommand = "THE COMMAND IS INVALID";
        if (!validInstruction.find()) {
            System.out.println(invalidCommand);
            return false;
        }
        if (validInstruction.group(1).equals("mul")) {
            if (Instructions.mul.matcher(instruction).find())
                message.runMul();
            else
                System.out.println(invalidCommand);
            return  false;
        }
        if (validInstruction.group(1).equals("add")) {
            if(Instructions.add.matcher(instruction).find())
                message.runAdd();
            else
                System.out.println(invalidCommand);
            return false;
        }
        if (validInstruction.group(1).equals("sub")) {
            if(Instructions.sub.matcher(instruction).find())
                message.runSub();
            else
                System.out.println(invalidCommand);
            return false;
        }
        if (validInstruction.group(1).equals("sum")) {
            if(Instructions.sum.matcher(instruction).find())
                message.runSum(Integer.parseInt(validInstruction.group(2)) , validInstruction.group(3).charAt(1));
            else
                System.out.println(invalidCommand);
            return false;
        }
        if (validInstruction.group(1).equals("gcd")) {
            if(Instructions.gcd.matcher(instruction).find())
                message.runGcd(Integer.parseInt(validInstruction.group(2)) , validInstruction.group(3).charAt(1));
            else
                System.out.println(invalidCommand);
            return false;
        }
        if (validInstruction.group(1).equals("replace")) {
            if(Instructions.replace.matcher(instruction).find())
                message.runReplace(validInstruction.group(2) , validInstruction.group(3) , Integer.parseInt(validInstruction.group(4)));
            else
                System.out.println(invalidCommand);
            return false;
        }
        if (validInstruction.group(1).equals("count_entail")) {
            if(Instructions.count_entail.matcher(instruction).find())
                message.runCount(validInstruction.group(2));
            else
                System.out.println(invalidCommand);
            return false;
        }
        if (validInstruction.group(1).equals("insert")) {
            if(Instructions.insert.matcher(instruction).find()) {
                if (validInstruction.group(3) != null)
                    message.runInsert(validInstruction.group(2), Integer.parseInt(validInstruction.group(3)));
                else
                    message.runInsert(validInstruction.group(2), -1);
            }
            else
                System.out.println(invalidCommand);
            return false;
        }
        if (validInstruction.group(1).equals("delete")) {
            if(Instructions.delete.matcher(instruction).find()) {
                if (validInstruction.group(3) != null)
                    message.runDelete(validInstruction.group(2), validInstruction.group(3).charAt(1));
                else
                    message.runDelete(validInstruction.group(2), 'b');
            }
            else
                System.out.println(invalidCommand);
            return false;
        }
        if (validInstruction.group(1).equals("print")) {
            if(Instructions.print.matcher(instruction).find())
                message.runPrint();
            else
                System.out.println(invalidCommand);
            return false;
        }
        if (validInstruction.group(1).equals("end")) {
            if (Instructions.end.matcher(instruction).find()) {
                System.out.println("END OF PROGRAM");
                return true;
            }
            else
                System.out.println(invalidCommand);
            return false;
        }
        System.out.println(invalidCommand);
        return false;
    }
}

class MyString{
    StringBuilder text;
    public MyString(String text) {
        this.text = new StringBuilder(text);
    }
    public void runMul(){
        Matcher matcher = Instructions.num.matcher(this.text);
        if (!matcher.find()) {
            System.out.println("CANNOT PERFORM THE COMMAND SUCCESSFULLY");
            return;
        }
        int startIndex = matcher.start();
        int num1 = Integer.parseInt(matcher.group(1));
        if (!matcher.find()) {
            System.out.println("CANNOT PERFORM THE COMMAND SUCCESSFULLY");
            return;
        }
        int endIndex = matcher.end();
        int num2 = Integer.parseInt(matcher.group(1));
        this.text.replace(startIndex , endIndex , Integer.toString(num1*num2));
        System.out.println(this.text);
    }
    public void runAdd(){
        Matcher matcher = Instructions.num.matcher(this.text);
        if (!matcher.find()) {
            System.out.println("CANNOT PERFORM THE COMMAND SUCCESSFULLY");
            return;
        }
        int startIndex = matcher.start();
        int num1 = Integer.parseInt(matcher.group(1));
        if (!matcher.find()) {
            System.out.println("CANNOT PERFORM THE COMMAND SUCCESSFULLY");
            return;
        }
        int endIndex = matcher.end();
        int num2 = Integer.parseInt(matcher.group(1));
        this.text.replace(startIndex , endIndex , Integer.toString(num1+num2));
        System.out.println(this.text);
    }
    public void runSub(){
        Matcher matcher = Instructions.num.matcher(this.text);
        if (!matcher.find()) {
            System.out.println("CANNOT PERFORM THE COMMAND SUCCESSFULLY");
            return;
        }
        int startIndex = matcher.start();
        int num1 = Integer.parseInt(matcher.group(1));
        if (!matcher.find()) {
            System.out.println("CANNOT PERFORM THE COMMAND SUCCESSFULLY");
            return;
        }
        int endIndex = matcher.end();
        int num2 = Integer.parseInt(matcher.group(1));
        this.text.replace(startIndex , endIndex , Integer.toString(num1-num2));
        System.out.println(this.text);
    }
    public void runSum(int count , char type){
        Matcher matcher = Instructions.num.matcher(this.text);
        int sum = 0;
        int numCount = -1;
        for (int i = 0; matcher.find() ; i++) {
            if (i<count)
                sum+=Integer.parseInt(matcher.group());
            numCount = i;
        }
        if (numCount<count-1){
            System.out.println("CANNOT PERFORM THE COMMAND SUCCESSFULLY");
            return;
        }
        if (type=='f'){
            sum = 0;
            matcher.reset();
            for (int i = 0; matcher.find() ; i++) {
                if (i>numCount-count)
                    sum+=Integer.parseInt(matcher.group());
            }
        }
        this.text.append("S" + Integer.toString(sum) + "S");
        System.out.println(this.text);
    }
    public void runGcd(int count , char type){
        Matcher matcher = Instructions.positiveNum.matcher(this.text);
        int gcd=0;
        int numCount = -1;
        for (int i = 0; matcher.find() ; i++) {
            if (i==0) {
                gcd = Integer.parseInt(matcher.group());
            }
            else{
                if (i<count)
                    gcd=MyMath.Gcd( Integer.parseInt(matcher.group()) , gcd);
            }

            numCount = i;
        }
        if (numCount<count-1){
            System.out.println("CANNOT PERFORM THE COMMAND SUCCESSFULLY");
            return;
        }
        if (type=='f'){
            matcher.reset();
            for (int i = 0; matcher.find() ; i++) {
                if (i==numCount-count+1)
                    gcd = Integer.parseInt(matcher.group());
                if (i>numCount-count)
                    gcd=MyMath.Gcd(Integer.parseInt(matcher.group()) , gcd);
            }
        }

        this.text.append("G" + Integer.toString(gcd) + "G");
        System.out.println(this.text);
    }
    public void runReplace(String oldWord , String newWord , int count){
        for (int i = 0; i < count; i++) {
            int start = this.text.indexOf(oldWord);
            if (start==-1){
                break;
            }
            this.text.replace(start, start+oldWord.length() , newWord);

        }
        System.out.println(this.text);
    }
    public void runCount(String key){
        int ans = 0;
        int index = 0;
        while (true){
            index = this.text.indexOf(key , index);
            if (index == -1)
                break;
            ans+=1;
            index+=1;
        }
        if (ans==0)
            System.out.println("CANNOT PERFORM THE COMMAND SUCCESSFULLY");
        else{
            this.text.append("C"+Integer.toString(ans)+"C");
            System.out.println(this.text);
        }
    }
    public void runInsert(String word , int count){
        if (count>this.text.length())
            System.out.println("CANNOT PERFORM THE COMMAND SUCCESSFULLY");

        else {
            if (count==-1)
                this.text.append(word);
            else {
                this.text.insert(count, word);
            }
            System.out.println(this.text);
        }
    }
    public void runDelete(String word , char type){
        int index = this.text.indexOf(word);
        if (type == 'f')
            index = this.text.lastIndexOf(word);
        if (index==-1)
            System.out.println("CANNOT PERFORM THE COMMAND SUCCESSFULLY");
        else {
            this.text.delete(index , index + word.length());
            System.out.println(this.text);
        }
    }
    public void runPrint(){
        System.out.println(this.text);
    }
}

class Instructions{
    public static Pattern valid = Pattern.compile("(\\S+) ?(\\S+)? ?(\\S+)? ?(\\S+)?");
    public static Pattern mul = Pattern.compile("^mul$");
    public static Pattern add = Pattern.compile("^add$");
    public static Pattern sub = Pattern.compile("^sub$");
    public static Pattern sum = Pattern.compile("^sum (\\d+) -([bf])$");
    public static Pattern gcd = Pattern.compile("^gcd (\\d+) -([bf])$");
    public static Pattern replace = Pattern.compile("^replace (\\S+) (\\S+) (\\d+)$");
    public static Pattern count_entail = Pattern.compile("^count_entail (\\S+)$");
    public static Pattern insert = Pattern.compile("^insert (\\S+) ?(\\d+)?$");
    public static Pattern delete = Pattern.compile("^delete (\\S+) ?(-f)?$");
    public static Pattern print = Pattern.compile("^print$");
    public static Pattern end = Pattern.compile("^end$");
    public static Pattern num = Pattern.compile("(-?\\d+)");
    public static Pattern positiveNum = Pattern.compile("(\\d+)");

}
class MyMath{
    public static int Gcd(int num1 ,int num2)
    {
        if (num1*num2==0)
            return 0;
        if (num1<num2){
            int temp = num1;
            num1 = num2;
            num2 = temp;
        }
        if (num1%num2==0)
            return num2;
        return Gcd(num2 , num1%num2);
    }
}
