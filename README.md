# Recruitment Task: Finding Pairs Summing to 12

## Overview

This project is a recruitment task aimed at implementing an application that finds all unique pairs of natural numbers whose sum equals **12**. Once a number is used in a pair, it cannot be used in any other pair.

## Task Description

### Input Data:
- A collection (array/list) of natural numbers with values ranging from **0 to 12**.
- **Example:** `[1, 2, 3, 11, 1]`

### Output Data:
- All pairs of numbers from the input that add up to **12**.
- Each pair is represented with the smaller number first.
- **Example:** `[1, 11], [1, 2]`

### Requirements:
- The application must be implemented using one of the following technologies: Scala, Java, Python, or Kotlin. (Bonus points if implemented in Scala.)
- The code should follow *clean code* principles.
- The solution should be available in an online repository (e.g., GitHub) with a proper README file.
- The handling of data and the algorithm should be placed in one location.
- The project must include automation for testing using GitHub Actions.

## Approach

Two common approaches can be used to solve this problem:

### Sorting with Two-Pointer Technique:
- **Sort** the list of numbers.
- Use two pointers (one at the beginning and one at the end) to find pairs whose sum is **12**.
    - If the sum of the numbers at both pointers equals **12**, record the pair and move both pointers.
    - If the sum is less than **12**, move the left pointer to the right.
    - If the sum is greater than **12**, move the right pointer to the left.

### Frequency Table (Hash Map/Array) Approach:
- **Count** the frequency of each number in the list.
- For every number, check if the complementary number (`12 - number`) exists.
- **Form pairs** by decreasing the counts appropriately to ensure that each number is used only once.

## Automation with GitHub Actions

GitHub Actions can be used to automate tasks such as:
- Building the project (compiling code, installing dependencies).
- Running unit and integration tests automatically on each push or pull request.
- Deploying the application if necessary.

## Getting Started

### 1. Clone the Repository:
```bash
git clone <repository-url>
