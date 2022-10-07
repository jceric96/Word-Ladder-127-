import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class WordLadder {
    // BFS
    // method one: one direction
    public int ladderLengthI(String beginWord, String endWord, List<String> wordList) {
        // remove duplicate string
        Set<String> wordSet = new HashSet<>(wordList);
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        Set<String> visited = new HashSet<>();
        visited.add(beginWord);
        int distance = 1;
        while (!queue.isEmpty()) {
            Queue<String> queue2 = new LinkedList<>();
            distance++;
            while (!queue.isEmpty()) {
                String current = queue.poll();
                List<String> wordsWithinDistance = getWordsWithinDistance(wordSet, current);
                for (String word : wordsWithinDistance) {
                    if (word.equals(endWord)) {
                        return distance;
                    }
                    if (!visited.contains(word)) {
                        queue2.add(word);
                        visited.add(word);
                    }
                }
            }
            queue = queue2;
        }
        return 0;
    }

    private ArrayList<String> getWordsWithinDistance(Set<String> wordSet, String word) {
        ArrayList<String> results = new ArrayList<>();
        char[] wordCharArr = word.toCharArray();
        for (int i = 0; i < word.length(); i++) {
            char originChar = wordCharArr[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c == originChar) {
                    continue;
                }
                wordCharArr[i] = c;
                String newStr = new String(wordCharArr);
                if (wordSet.contains(newStr)) {
                    results.add(newStr);
                    // remove visited word to reduce time
                    wordSet.remove(newStr);
                }
            }
            wordCharArr[i] = originChar;
        }
        return results;
    }

    // BFS
    // method two: two-direction
    public int ladderLengthII(String beginWord, String endWord, List<String> wordList) {
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
        String[] ss = { "hie", "mie", "mee", "mee", "cee", "coe", "cog" };
        for (String s : ss) {
            wordList.add(s);
        }
        System.out.println(wl.ladderLengthI(beginWord, endWord, wordList));
    }
}
