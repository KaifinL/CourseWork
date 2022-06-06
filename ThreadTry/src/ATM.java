class ATM {

    private int twenties;
    private int fifties;
    private int hundred;
    private int two_hundreds;
    private int five_hundreds;

    public ATM() {
        this.twenties = 0;
        this.fifties = 0;
        this.hundred = 0;
        this.two_hundreds = 0;
        this.five_hundreds = 0;
    }

    public void deposit(int[] banknotesCount) {
        this.twenties += banknotesCount[0];
        this.fifties += banknotesCount[1];
        this.hundred += banknotesCount[2];
        this.two_hundreds += banknotesCount[3];
        this.five_hundreds += banknotesCount[4];
    }

//    private int[] withdraw_helper(int amount) {
//        if (amount < 0) {
//            return new int[]{-1};
//        }
//    }
//
//    public int[] withdraw(int amount) {
//
//    }

}

/**
 * Your ATM object will be instantiated and called as such:
 * ATM obj = new ATM();
 * obj.deposit(banknotesCount);
 * int[] param_2 = obj.withdraw(amount);
 */