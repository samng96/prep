import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

/**
 * Created by samng on 2/2/18.
 */
public class Trie {
    private class TrieNode {
        private TrieNode[] children;
        private boolean isTerm;

        public TrieNode() {
            children = new TrieNode[26];
        }

        public void PrintNode(int level, char c) {
            out.print("L:" + level + ", C:" + c + "\n");
            for (int i = 0; i < 26; i++) {
                if (children[i] != null) {
                    children[i].PrintNode(level + 1, (char)('a' + i));
                }
            }
        }
    }

    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void AddWord(String word) {
        TrieNode n = this.root;
        word = word.toLowerCase();

        for (char i : word.toCharArray()) {
            if (n.children[i - 'a'] == null) {
                n.children[i - 'a'] = new TrieNode();
            }
            n = n.children[i - 'a'];
        }
        n.isTerm = true;
    }

    public List<String> FindWords(String prefix) {
        TrieNode n = this.root;
        prefix = prefix.toLowerCase();

        for (char i : prefix.toCharArray()) {
            if (n.children[i - 'a'] == null) {
                return null;
            }
            n = n.children[i - 'a'];
        }

        // We have the node, now recurse through it.
        ArrayList<String> result = new ArrayList<String>();
        findWordsAtNode(n, prefix, result);
        return result;
    }

    private void findWordsAtNode(TrieNode n, String prefix, ArrayList<String> result) {
        if (n.isTerm) {
            result.add(prefix);
        }
        for (int i = 0; i < 26; i++) {
            if (n.children[i] != null) {
                findWordsAtNode(n.children[i], prefix + (char)('a' + i), result);
            }
        }
    }

    protected void PrintTrie() {
        root.PrintNode(0, '0');
    }
}
