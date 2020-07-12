
import java.text.DecimalFormat;
import java.util.Scanner;

public class LongInt {

    private final int[] array;
    private final int sign;

    public LongInt(int sign, int[] array) {

        this.array = array;
        this.sign = sign;
    }

    public void print() {

        String number = String.valueOf(array[array.length - 1]);
        DecimalFormat decimalFormat = new DecimalFormat("00000000");
        for (int i = array.length - 2; i >= 0; i--) {
            if (String.valueOf(array[i]).length() < 8) {
                number = number + decimalFormat.format(array[i]);
            } else {
                number = number + String.valueOf(array[i]);
            }

        }
        if (sign == -1) {
            System.out.print("-");
        }
        System.out.println(number);
    }

    public boolean equalTo(LongInt other) {
        boolean equal = true;
        if (this.sign != other.sign) {
            return false;
        }
        if (this.array.length != other.array.length) {
            return false;
        }
        for (int i = 0; i < array.length; i++) {
            if (this.array[i] != other.array[i]) {
                return false;
            }
        }
        return equal;
    }

    public LongInt add(LongInt other) {
        int[] temp;
        int sign = 1;

        if (this.array.length > other.array.length) {
            temp = new int[this.array.length];
        } else if (this.array.length < other.array.length) {
            temp = new int[other.array.length];
        } else {
            temp = new int[other.array.length + 1];
        }
        int sum = 0, carry = 0;
        for (int i = 0; i < temp.length; i++) {
            sum = 0;
            if (i < this.array.length && i < other.array.length) {
                sum = this.array[i] + other.array[i];
            } else if (i < this.array.length) {
                sum = this.array[i];
            } else if (i < other.array.length) {
                sum = other.array[i];
            }
            if (carry == 1) {
                sum = sum + 1;
            }
            if (sum / 100000000 == 1) {
                sum = sum % 100000000;
                carry = 1;
            } else {
                carry = 0;
            }

            temp[i] = sum;
        }
        return new LongInt(sign, temp);
    }

    public LongInt subtract(LongInt other) {
        int[] temp;
        int sign = 1;

        if (this.array.length > other.array.length) {
            temp = new int[this.array.length];
        } else if (this.array.length < other.array.length) {
            temp = new int[other.array.length];
        } else {
            temp = new int[other.array.length + 1];
        }

        int subtract = 0, carry = 0;
        for (int i = 0; i < temp.length; i++) {
            subtract = 0;
            carry = 0;

            if (i < this.array.length && i < other.array.length) {
                if (this.array[i] < other.array[i]) {
                    // take carry
                    int j;
                    for (j = i + 1; j < this.array.length; j++) {
                        if (this.array[j] != 0) {
                            this.array[j] = this.array[j] - 1;
                            carry = 1;
                            break;
                        }
                    }
                    if (carry == 1) {
                        for (int k = j - 1; k > i; k--) {
                            this.array[j] = 9;
                        }
                        subtract = 100000000 + array[i];
                        subtract = subtract - other.array[i];
                    } else {
                        subtract = other.array[i] - this.array[i];
                        sign = -1;
                        //System.out.println("WRONG CALCULATIONS\n");
                    }
                } else {
                    subtract = this.array[i] - other.array[i];
                }

            } else if (i < this.array.length) {
                subtract = this.array[i];
            } else if (i < other.array.length) {
                subtract = other.array[i];
            }
            temp[i] = subtract;
        }

        return new LongInt(sign, temp);

    }

    public boolean greaterThan(LongInt other) {
        boolean flag = false;

        if (this.sign == 1 && other.sign == -1) {
            return true;
        } else if (this.sign == 1 && other.sign == -1) {
            return false;
        } else if (this.sign == 1 && other.sign == 1) {
            if (this.subtract(other).sign == 1) {
                return true;
            } else {
                return false;
            }
        } else if (this.sign == -1 && other.sign == -1) {
            if (this.subtract(other).sign == -1) {
                return true;
            } else {
                return false;
            }
        }
        return flag;
    }

    public boolean lessThan(LongInt other) {
        boolean flag = false;

        if (this.sign == 1 && other.sign == -1) {
            return false;
        } else if (this.sign == 1 && other.sign == -1) {
            return true;
        } else if (this.sign == 1 && other.sign == 1) {
            if (this.subtract(other).sign == 1) {
                return false;
            } else {
                return true;
            }
        } else if (this.sign == -1 && other.sign == -1) {
            if (this.subtract(other).sign == -1) {
                return false;
            } else {
                return true;
            }
        }
        return flag;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        Scanner input = new Scanner(System.in);

        System.out.println("Enter First the number:");
        String number = input.nextLine();
        LongInt FirstNum = input(number);
        System.out.println("Enter Second the number:");
        number = input.nextLine();
        LongInt SecondNum = input(number);

        
        System.out.print("Addition : ");
        FirstNum.add(SecondNum).print();
        
        System.out.print("Subtraction :");
        FirstNum.subtract(SecondNum).print();
        
        System.out.println("");
        System.out.println("");
        
        if (FirstNum.greaterThan(SecondNum)) {
            System.out.print("Greater : ");
            FirstNum.print();
        } else {
            System.out.print("Greater : ");
            SecondNum.print();
        }
        
        
        if (FirstNum.lessThan(SecondNum)) {
            System.out.print("Lesser : ");
            FirstNum.print();
        } else {
            System.out.print("Lesser : ");
            SecondNum.print();
        }
    }

    public static LongInt input(String number) {
        int sign = 1;
        if (number.charAt(0) == '-') {
            number = number.substring(1, number.length());
            sign = -1;
        }
        int size = number.length() / 8;
        if (number.length() % 8 != 0) {
            size++;
        }
        System.out.println("Size : " + size);
        int[] temp = new int[size];
        int startingIndex = number.length() % 8;
        int t = size - 1; // last index
        if (startingIndex != 0) {
            //System.out.println("Number1:"+Integer.parseInt(number.substring(0, startingIndex)));
            temp[t] = Integer.parseInt(number.substring(0, startingIndex));
            //System.out.println(temp[t]);
            t--;
        }
        while (t >= 0) {
            //System.out.println("Number2:"+Integer.parseInt(number.substring(startingIndex,startingIndex+8)));
            temp[t] = Integer.parseInt(number.substring(startingIndex, startingIndex + 8));
            startingIndex += 8;
            t--;
        }

        return new LongInt(sign, temp);
    }
}
