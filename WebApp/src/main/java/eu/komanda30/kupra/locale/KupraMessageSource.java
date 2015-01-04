package eu.komanda30.kupra.locale;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.text.ExtendedMessageFormat;
import org.apache.commons.lang.text.FormatFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class KupraMessageSource extends ReloadableResourceBundleMessageSource {
    private final Map<String, FormatFactory> registry;

    public KupraMessageSource(Map<String, FormatFactory> registry) {
        this.registry = registry;
    }


    @Override
    protected MessageFormat createMessageFormat(String msg, Locale locale) {
        return new ExtendedMessageFormat(msg, locale, registry);
    }
}
