package com.prodapt.license_tracker.report.util;

import java.io.PrintWriter;
import java.util.List;

public class CsvGenerator {

    public static void writeLine(PrintWriter writer, List<String> values) {
        writer.println(String.join(",", values));
    }
    
    public static <T> void writeCsv(
            PrintWriter writer,
            List<String> headers,
            List<List<String>> rows) {

        writer.println(String.join(",", headers));

        for (List<String> row : rows) {
            writer.println(String.join(",", row));
        }
    }
    
    
}
