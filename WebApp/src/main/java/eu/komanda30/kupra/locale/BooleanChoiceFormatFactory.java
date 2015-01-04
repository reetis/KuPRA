package eu.komanda30.kupra.locale;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Locale;

import org.apache.commons.lang.text.FormatFactory;

public class BooleanChoiceFormatFactory implements FormatFactory {
    private static class BooleanChoiceFormat extends Format {
        private final String trueMessage;
        private final String falseMessage;

        private BooleanChoiceFormat(String trueMessage, String falseMessage) {
            this.trueMessage = trueMessage;
            this.falseMessage = falseMessage;
        }

        @Override
        public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
            if (Boolean.TRUE.equals(obj)) {
                toAppendTo.append(trueMessage);
                return toAppendTo;
            } else if (Boolean.FALSE.equals(obj)) {
                toAppendTo.append(falseMessage);
                return toAppendTo;
            } else {
                throw new IllegalArgumentException("BooleanChoice argument is not Boolean!");
            }
        }

        @Override
        public Object parseObject(String source, ParsePosition pos) {
            throw new UnsupportedOperationException("parseObject not supported!");
        }
    }

    @Override
    public Format getFormat(String name, String arguments, Locale locale) {
        //true message|false message
        final String[] parts = arguments.split("\\|");
        final String trueMessage = parts.length >= 1 ? unwrapQuotes(parts[0]) : "";
        final String falseMessage = parts.length >= 2 ? unwrapQuotes(parts[1]) : "";
        return new BooleanChoiceFormat(trueMessage, falseMessage);
    }

    private static String unwrapQuotes(String msg) {
        final String trimmed = msg.trim();
        if (trimmed.length() >= 2 && (
                trimmed.startsWith("\"") && trimmed.endsWith("\"")) ) {
            return trimmed.substring(1, trimmed.length()-1);
        } else {
            return trimmed;
        }
    }
}
