# Cache Locality Benchmark

The matrix contains 2000 rows and 2000 columns.

Both traversal methods sum the same 4,000,000 elements.

Three warm-up executions per traversal precede ten measured
executions per traversal. Measurement order alternates to
reduce systematic ordering bias.

A typical 64-byte cache line can hold eight 8-byte doubles.
Row-major traversal accesses adjacent values within each row,
which tends to improve spatial locality and prefetching.

Column-major traversal switches between row arrays during
its inner loop.

Java double[][] is an array of row arrays, rather than one
guaranteed contiguous rectangular allocation.

This program measures elapsed execution time, not actual
hardware cache-miss counts.

Results depend on the processor, JVM and background activity.

Speedup is:
average column-major time / average row-major time.