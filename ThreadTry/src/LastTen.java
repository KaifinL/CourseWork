public class LastTen {
    public int lastTen[];
    public int index = 0;

    public LastTen() {
        this.lastTen = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    public void add(int newValue) {
        this.lastTen[index] = newValue;
        this.index += 1;
    }

    public int[] getLastTen() {
        return this.lastTen;
    }
}
