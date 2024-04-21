import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class Lox {
    //> Evaluating Expressions interpreter-instance
    private static final Interpreter interpreter = new Interpreter();
    //< Evaluating Expressions interpreter-instance
//> had-error
    static boolean hadError = false;
    //< had-error
//> Evaluating Expressions had-runtime-error-field
    static boolean hadRuntimeError = false;

    //< Evaluating Expressions had-runtime-error-field
    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            System.exit(64); // [64]
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }



    //> run-file
    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
//> exit-code

        // Indicate an error in the exit code.
        if (hadError) System.exit(65);
//< exit-code
//> Evaluating Expressions check-runtime-error
        if (hadRuntimeError) System.exit(70);
//< Evaluating Expressions check-runtime-error
    }



    static void runtimeError(RuntimeError error) {
        System.err.println(error.getMessage() +
                "\n[line " + error.token.line + "]");
        hadRuntimeError = true;
    }



    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);
        for (;;) {
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) break;
            run(line);
        }
