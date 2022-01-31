/**
 * Wilson Tjoeng (tjoeng@wisc.edu)
 * 2/2/21
 * CS577 - Introduction to Algorithms
 * Assignment 1
 * 
 * @brief Simple hello program that takes in inputs, and prints hello to each line up to n times
 * 
 * 
 */

#include <iostream>
#include <vector>

using std::cin;
using std::cout;
using std::endl;
using std::getline;
using std::string;
using std::vector;

/**
 * @brief Driver program
 * 
 * @return int always returns 1
 */
int main() {
    string s; 
    int n;
    vector<string> strings;

    getline(cin, s); // Get the integer as a string
    n = stoi(s); // Convert string to integer

    // Store string inputs
    while (n > 0) {
        getline(cin, s);
        strings.push_back(s);
        n--;
    }

    // Print strings
    for (string i : strings) {
        cout << "Hello, " + i + "!" + '\n';
    }

    return 1;
}
