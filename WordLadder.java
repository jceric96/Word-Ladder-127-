import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordLadder {

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // aviod duplicate word
        Set<String> dictionary = new HashSet<String>(wordList);
        if (!dictionary.contains(endWord))
            return 0;

        // 3. Use set instead of queue during bfs
        // avoid duplicate word and they are not in any order
        Set<String> forwardSet = new HashSet<String>();
        Set<String> backwardSet = new HashSet<String>();

        forwardSet.add(beginWord);
        backwardSet.add(endWord);
        dictionary.remove(endWord);
        dictionary.remove(beginWord);

        // 1. Search from entry and exit at the same time
        return transform(forwardSet, backwardSet, dictionary);
    }

    public int transform(Set<String> forwardSet, Set<String> backwardSet, Set<String> dictionary) {
        Set<String> tempSet = new HashSet<String>();
        for (String fs : forwardSet) {
            char word[] = fs.toCharArray();
            for (int i = 0; i < word.length; i++) {
                for (int c = 'a'; c <= 'z'; c++) {
                    char origin = word[i];
                    word[i] = (char) c;
                    String target = String.valueOf(word);
                    if (backwardSet.contains(target)) {
                        // stop bfs when entry and exits meet
                        return 2;
                    } else if (dictionary.contains(target) && !forwardSet.contains(target)) {
                        // 4. Remove visited word from disctionary to decrease the search time
                        dictionary.remove(target);
                        tempSet.add(target);
                    }
                    word[i] = origin;
                }
            }
        }
        if (tempSet.size() == 0)
            return 0;
        forwardSet = tempSet;
        // 2. Pick the Set with less elements to bfs
        int result = forwardSet.size() > backwardSet.size() ? transform(backwardSet, forwardSet, dictionary)
                : transform(forwardSet, backwardSet, dictionary);
        return result == 0 ? 0 : result + 1;
    }

    public static void main(String[] args) {
        WordLadder wl = new WordLadder();
        String beginWord = "hit";
        String endWord = "cog";
        List<String> wordList = new ArrayList<String>();
        String[] ss = { "hie", "mie", "mee", "cee", "coe", "cog" };
        for (String s : ss) {
            wordList.add(s);
        }
        System.out.println(wl.ladderLength(beginWord, endWord, wordList));
    }
}
