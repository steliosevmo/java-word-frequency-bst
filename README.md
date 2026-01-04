# Self-Adjusting Word Frequency Counter (BST)

A Java-based implementation of a word frequency counter that utilizes a **Self-Adjusting Binary Search Tree (BST)**. This project efficiently processes text files, filters stop-words, and organizes word frequencies using advanced tree rotation techniques to optimize search performance for high-frequency terms.

## 🚀 Overview

The system reads text data, cleans punctuation using regular expressions, and stores words in a BST. To optimize performance, the tree is self-adjusting: when a word is searched and its frequency exceeds the mean frequency of all words, it is moved to the root of the tree through rotations.

## 🛠️ Key Features

* **Self-Adjusting Logic:** Implements `rotL` (left rotation) and `rotR` (right rotation) to elevate frequently accessed nodes.
* **Stop-word Management:** Includes a dynamic Linked List to store and filter out common "stop-words" that should be ignored during processing.
* **Advanced Sorting:** Uses an optimized **QuickSort** algorithm to display word lists sorted by frequency.
* **Punctuation Cleaning:** Automatically strips characters such as `()[]{}.,!?;:<>` and quotes from the input text.
* **Statistical Analysis:** Provides metrics such as total word count, distinct word count, mean frequency, and identifies the most frequent word.

---

## 📂 Project Structure

| File | Description |
| :--- | :--- |
| `WordCounter.java` | The core interface defining the operations for word management. |
| `BST.java` | The main implementation of the self-adjusting Binary Search Tree. |
| `WordFrequency.java` | Object class representing a word and its occurrence count. |
| `Main.java` | Entry point for testing the library features (Stop-words, Loading, Searching). |
| `text.txt` | Sample input file for testing word processing. |

---

## ⚙️ Technical Details

### Self-Adjustment Strategy
The tree maintains efficiency through conditional rotations. During a `search(String w)` operation:
1.  The word's frequency is compared to the **Mean Frequency** ($Total\_Occurrences / Distinct\_Words$).
2.  If the word's frequency is higher than the mean, the node is removed and re-inserted at the **Head** of the tree using recursive rotations to maintain BST properties while making future searches faster.


### Complexity
* **Search/Insert (Balanced):** $O(\log n)$
* **Search/Insert (Worst Case):** $O(n)$
* **Sorting:** $O(n \log n)$ using QuickSort.

---

## 💻 How to Use

### Prerequisites
* Java Development Kit (JDK) 8 or higher.

### Compilation
Compile all files in the project directory:
```bash
javac *.java
