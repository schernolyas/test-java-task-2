package ru.schernolyas.testtask.service;

import org.springframework.stereotype.Service;
import ru.schernolyas.testtask.dto.StatementInfo;
import ru.schernolyas.testtask.dto.StatementRow;
import ru.schernolyas.testtask.dto.StatementSummary;
import ru.schernolyas.testtask.util.BigDecimalUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StatementParser {
    static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    static final DecimalFormat DECIMAL_FORMAT = prepareDecimalFormat();
    static final Pattern DIGIT_PATTERN = Pattern.compile("(\\d+\\s{0,1}\\d*.{0,1}\\d{0,2})");

    private static DecimalFormat prepareDecimalFormat() {
        DecimalFormat decimalFormat = new DecimalFormat("###,###.##");
        DecimalFormatSymbols s = decimalFormat.getDecimalFormatSymbols();
        s.setGroupingSeparator(' ');
        decimalFormat.setDecimalFormatSymbols(s);
        return decimalFormat;
    }

    public StatementInfo parse(InputStream ioStream) throws UnsupportedEncodingException {
        return parse(new BufferedReader(new InputStreamReader(ioStream, "UTF-8")));
    }

    public StatementInfo parse(BufferedReader bufferedReader) {
        StatementInfo statementInfo = new StatementInfo();
        statementInfo.setStatementRows(new ArrayList<>());
        Iterator<String> it = bufferedReader.lines().iterator();
        while (it.hasNext()) {
            String currentLine = it.next();
            System.out.println("----");
            System.out.println(currentLine);
            if (currentLine.trim().length() == 0 ||
                    currentLine.startsWith("----------")
            ) {
                continue;
            }

            if (statementInfo.getInitialBalance() == null) {
                boolean isInitialBalance = currentLine.startsWith("Остаток на начало периода");
                if (isInitialBalance) {
                    BigDecimal number = extractNumbers(currentLine).get(0);
                    System.out.println(number);
                    statementInfo.setInitialBalance(number);
                }
            }
            if (statementInfo.getFinalBalance() == null) {
                boolean isFinalBalance = currentLine.startsWith("Остаток на конец периода");
                if (isFinalBalance) {
                    BigDecimal number = extractNumbers(currentLine).get(0);
                    System.out.println(number);
                    statementInfo.setFinalBalance(number);
                }
            }
            if (statementInfo.getStatementSummary() == null) {
                boolean isSummary = currentLine.startsWith("ИТОГО");
                System.out.println(isSummary);
                if (isSummary) {
                    List<BigDecimal> numbers = extractNumbers(currentLine);
                    statementInfo.setStatementSummary(new StatementSummary(numbers.get(0), numbers.get(1)));
                }
            }
            if (startsWithDate(currentLine)) {
                String datePart = currentLine.substring(0, 11).trim();
                String correspondentAccountPart = currentLine.substring(12, 35).trim();
                String debitPart = currentLine.substring(36, 47).trim();
                String creditPart = currentLine.substring(48, 61).trim();
                String commentPart = currentLine.substring(61).trim();
                LocalDate date = parseDate(datePart);
                BigDecimal debit = debitPart.isBlank() ? BigDecimal.ZERO : extractNumbers(debitPart).get(0);
                BigDecimal credit = creditPart.isBlank() ? BigDecimal.ZERO : extractNumbers(creditPart).get(0);
                StatementRow statementRow = new StatementRow(
                        date,
                        correspondentAccountPart,
                        debit,
                        credit,
                        commentPart
                );
                statementInfo.getStatementRows().add(statementRow);
            }

        }

        return statementInfo;
    }

    private List<BigDecimal> extractNumbers(String str) {
        List<BigDecimal> numbers = new ArrayList<>();
        String digitStr = null;
        Matcher matcher = DIGIT_PATTERN.matcher(str);
        while (matcher.find()) {
            digitStr = matcher.group(1);
            if (digitStr != null) {
                Number number = null;
                try {
                    number = DECIMAL_FORMAT.parse(digitStr);
                    System.out.println("!!!!" + number);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
//            String d = digitStr.replace(',', '.').replace(" ", "");
                numbers.add(BigDecimalUtil.build(number.doubleValue()));
            }
        }

        return numbers;
    }

    private boolean startsWithDate(String str) {
        LocalDate date = null;
        try {
            String first10Chars = str.substring(0, 10);
            date = LocalDate.parse(first10Chars, StatementParser.DATE_FORMATTER);
        } catch (Exception e) {

        }
        return date != null;
    }

    private LocalDate parseDate(String str) {
        LocalDate date = null;
        try {
            String first10Chars = str.substring(0, 10);
            date = LocalDate.parse(first10Chars, StatementParser.DATE_FORMATTER);
        } catch (Exception e) {

        }
        return date;
    }
}
