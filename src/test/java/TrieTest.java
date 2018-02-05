import org.junit.Test;

import java.util.List;

import static java.lang.System.out;

/**
 * Created by samng on 2/2/18.
 */
public class TrieTest {
    @Test
    public void basicAddTest() {
        Trie trie = new Trie();

        trie.AddWord("hello");
        trie.AddWord("hi");
        trie.PrintTrie();

        FindAndPrint(trie, "h");
    }

    @Test
    public void addMultipleTest() {
        Trie trie = new Trie();

        trie.AddWord("hello");
        trie.AddWord("hello");
        trie.PrintTrie();

        FindAndPrint(trie, "h");
    }

    private void FindAndPrint(Trie trie, String prefix) {
        List<String> res = trie.FindWords(prefix);
        for (String s : res) {
            out.println(s);
        }
    }
}
