# Java Synchronization and Performance Analysis

This repository contains the implementation and analysis for my **Master's Thesis**, which explores various Java synchronization methods, the Java Memory Model (JMM), and the impact of the Just-In-Time (JIT) compiler on performance. The project uses **Java Microbenchmark Harness (JMH)** to measure synchronization performance on a range of problems.

---

## 📖 Overview

The thesis investigates:
- **Java Synchronization Methods**: Performance and behavior of different synchronization techniques in Java.
- **Java Memory Model (JMM)**: How the JMM affects thread communication and consistency guarantees.
- **JIT Compiler**: The influence of Just-In-Time compilation on synchronization and overall application performance.

### Problems Analyzed:
1. **Increment Operation**: A simple counter increment with various synchronization mechanisms.
2. **Producer-Consumer Problem**: Implementation and performance testing of a classic multi-threading problem.
3. **Modified Matrix Multiplication**: A computational problem to assess synchronization performance in more complex scenarios.

---

## 🚀 Tools and Technologies

- **Java Microbenchmark Harness (JMH)**: For benchmarking and measuring performance.
- **Java**: Focus on multi-threading and concurrency features.
- **JIT Compiler**: Analyzed for runtime optimizations.
- **Maven/Gradle**: For build and dependency management.

---

## 📊 Key Findings

The experiments provide insights into:
- How different synchronization methods compare in terms of performance.
- The role of the JMM in thread synchronization and memory visibility.
- How JIT optimizations influence the execution of synchronized code.

For detailed results and analysis, refer to the full Master's Thesis linked below.

---

## 📜 Master's Thesis

You can read the complete thesis here:  
[**Master's Thesis - Java Synchronization and Performance Analysis**](https://drive.google.com/file/d/1Vtoc3AMKm56kv8rEzsw89rCXrBapuhI0/view?usp=share_link)
