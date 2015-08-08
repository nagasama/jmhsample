package jmhsample;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 *
 * samples
 * 
 * @author 
 */
@Fork(value = 1)
@Warmup(iterations = 5)
@Measurement(iterations = 10)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class JMH_RegxSample {

    private static final Pattern MAIL_REGEX = Pattern.compile("[-_.0-9A-Za-z]+@[-_0-9A-Za-z]+[-_.0-9A-Za-z]+");
    private static final Pattern MAIL_REGEX2 = Pattern.compile("[-_.0-9A-Za-z]{1,64}@[-_0-9A-Za-z]+[-_.0-9A-Za-z]+");
    private static final com.google.re2j.Pattern MAIL_REGEX3 = com.google.re2j.Pattern.compile("[-_.0-9A-Za-z]+@[-_0-9A-Za-z]+[-_.0-9A-Za-z]+");

    @Param({"64", "128", "256"})
    public int runs;
    
    public String text;

    public String repeat(String st, int c) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < c; ++i) {
            buf.append(st);
        }
        return buf.toString();
    }

    @Setup
    public void setUp() {
        text = repeat("a", runs);
    }

    @TearDown
    public void tearDown() {
    }

    @Benchmark
    public String mail_regex() {
        return MAIL_REGEX.matcher(text).replaceAll(" ");
    }
    @Benchmark
    public String mail_regex2() {
        return MAIL_REGEX2.matcher(text).replaceAll(" ");
    }

    @Benchmark
    public String mail_regex3() {
        return MAIL_REGEX3.matcher(text).replaceAll(" ");
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JMH_RegxSample.class.getSimpleName())
                //    .forks(2)
                .build();
        new Runner(opt).run();
    }

}
