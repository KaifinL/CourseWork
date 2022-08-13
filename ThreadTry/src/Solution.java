
import java.util.*;

class Solution {
    private boolean valid_index(int []fs_memory, int begin_index, int word_length) {
        HashSet<Integer> validation = new HashSet();
        for (int i = 0; i < word_length; i++) {
            validation.add(fs_memory[begin_index+i*word_length]);
        }

        for (int i = 0; i < word_length; i++) {
            if (!validation.contains(i)) {
                return false;
            }
        }
        return true;
    }


    public List<Integer> findSubstring(String s, String[] words) {
        int fs_memory[] = new int[s.length()];
        int word_length = words[0].length();
        List<Integer> result = new ArrayList();
        HashMap<String, Integer> words_index = new HashMap();
        for (int i = 0; i < words.length; i++) {
            words_index.put(words[i], i);
        }
        for (int i = 0; i < s.length()-word_length+1; i++) {
            String curr = s.substring(i, i+word_length+1);
            if (words_index.containsKey(curr)) {
                fs_memory[i] = words_index.get(curr);
            } else {
                fs_memory[i] = -1;
            }
        }
        for (int i = 0; i < s.length()-word_length+1; i++) {
            if (fs_memory[i] >= 0 && valid_index(fs_memory, i, word_length)) {
                result.add(i);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Solution test = new Solution();
        test.findSubstring("barfoothefoobarman", new String[]{})
    }
}