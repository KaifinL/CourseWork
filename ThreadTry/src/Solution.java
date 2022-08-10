import java.util.*;


class Solution {


    // return -1 if there is no such match and the next item otherwise.
    public static int next_index(List<String> curr_code, List<String> shoppingCart, int index) {
        int satisfied_counter = 0;
        while (index < shoppingCart.size() && satisfied_counter < curr_code.size()) {
            if (shoppingCart.get(index).equals(curr_code.get(satisfied_counter))) {
                satisfied_counter++;
            } else {
                satisfied_counter = 0;
            }
            index++;
        }
        if (satisfied_counter == curr_code.size()) {
            return index;
        } else {
            return -1;
        }
    }

    // return 1 if the items in shopping cart satisfy the order form codeList without
    // overlap. Gaps are allowed.
    public static int satisfy(List<List<String>> codeList, List<String> shoppingCart) {
        int next_index = 0;
        for (List<String> code : codeList) {
            next_index = next_index(code, shoppingCart, next_index);
            if (next_index == -1) {
                return 0;
            }
        }
        return 1;
    }

    public static void main(String[] args) {

    }
}