package pack2;

public class Account {
    

    private static int accnogen;

    private static int accnogenfunc() {
        accnogen += 1;
        return accnogen;
    }

    int accno;
    int balance;
    static String ifsccode;
    String passwd;


    Account(int accno, int balance, String passwd) {
        this.accno = accno;
        this.balance = balance;
        this.passwd = passwd;
    }

    Account(int balance, String passwd) {
        this.accno = accnogenfunc();
        this.balance = balance;
        this.passwd = passwd;
    }
}